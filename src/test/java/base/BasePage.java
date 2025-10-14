package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class BasePage {
    protected static AndroidDriver driver;
    protected static WebDriverWait wait;

    private static boolean isCI() {
        String ci = System.getenv("CI");
        return ci != null && ci.equalsIgnoreCase("true");
    }

    public static void takeScreenshot(String name) {
        if (driver == null) return;
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get("target/screenshots"));
            File destFile = new File("target/screenshots/" + name + ".png");
            Files.copy(srcFile.toPath(), destFile.toPath());
            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }

    public static AndroidDriver getDriver() {
        return driver;
    }

    @BeforeAll
static void setup() {
    try {
        // APK path
        String apkPath = System.getenv("APK_PATH");
        if (apkPath == null || apkPath.isEmpty()) {
            apkPath = System.getProperty("user.dir") + "/app/General-Store.apk";
            if (!new File(apkPath).exists()) {
                apkPath = System.getProperty("user.dir") + "/General-Store.apk";
            }
        }
        System.out.println("📦 Using APK path: " + apkPath);

        // Wait for emulator to be fully booted
        if (isCI()) {
            System.out.println("🕓 Checking for emulator...");
            int timeout = 300; // 5 min max
            int elapsed = 0;
            while (true) {
                Process p = Runtime.getRuntime().exec("adb shell getprop sys.boot_completed");
                p.waitFor();
                String output = new String(p.getInputStream().readAllBytes()).trim();
                if ("1".equals(output)) break;
                if (elapsed >= timeout) throw new RuntimeException("❌ Timeout waiting for emulator to boot");
                System.out.println("⏳ Waiting for emulator to boot... " + elapsed + "s");
                Thread.sleep(5000);
                elapsed += 5;
            }
            System.out.println("✅ Emulator booted!");
        }

        // Setup Appium options
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("appium:app", apkPath);
        options.setCapability("appium:deviceName", "emulator-5554");
        options.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        options.setCapability("appium:appPackage", "com.androidsample.generalstore");
        options.setCapability("appium:appActivity", "com.androidsample.generalstore.SplashActivity");
        options.setCapability("appium:appWaitActivity", "com.androidsample.generalstore.*");
        options.setCapability("appium:autoGrantPermissions", true);
        options.setCapability("appium:fullReset", false);
        options.setCapability("appium:clearDeviceLogsOnStart", true);

        if (isCI()) {
            options.setCapability("appium:ignoreHiddenApiPolicyError", true);
            options.setCapability("appium:adbExecTimeout", 600_000);
            options.setCapability("appium:uiautomator2ServerInstallTimeout", 180_000);
            options.setCapability("appium:uiautomator2ServerLaunchTimeout", 180_000);
            options.setCapability("appium:newCommandTimeout", 600);
        } else {
            options.setCapability("appium:newCommandTimeout", 300);
        }

        // CI delay
        if (isCI()) {
            System.out.println("⏳ Waiting for app to stabilize (CI delay 5s)...");
            Thread.sleep(5000);
        }

        String appiumUrl = "http://127.0.0.1:4723/wd/hub";
        System.out.println("🌐 Connecting to Appium at: " + appiumUrl);

        // Retry driver initialization
        int retryCount = 0, maxRetries = 3;
        while (retryCount < maxRetries) {
            try {
                driver = new AndroidDriver(new URL(appiumUrl), options);
                System.out.println("✅ AndroidDriver initialized successfully.");
                break;
            } catch (Exception e) {
                retryCount++;
                System.out.println("⚠️ Driver init failed (attempt " + retryCount + "/" + maxRetries + "): " + e.getMessage());
                if (retryCount == maxRetries) throw e;
                Thread.sleep(10000);
            }
        }

        // WebDriverWait
        int waitSeconds = isCI() ? 60 : 20;
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

        // Check splash screen
        try {
            System.out.println("👀 Waiting for splash screen element...");
            By splashLocator = By.id("com.androidsample.generalstore:id/splash_logo");
            wait.until(ExpectedConditions.visibilityOfElementLocated(splashLocator));
            System.out.println("✅ Splash screen is visible.");
        } catch (TimeoutException e) {
            System.out.println("⚠️ Splash screen not found after timeout, continuing anyway.");
            takeScreenshot("splash_timeout");
        }

    } catch (Exception e) {
        e.printStackTrace();
        takeScreenshot("init_error");
        throw new RuntimeException("❌ Failed to initialize driver: " + e.getMessage(), e);
    }
}


    @AfterAll
    static void tearDown() {
        if (driver != null) {
            try {
                System.out.println("🧹 Cleaning up: removing app...");
                driver.removeApp("com.androidsample.generalstore");
            } catch (Exception e) {
                System.out.println("⚠️ Unable to remove app: " + e.getMessage());
            } finally {
                System.out.println("🔚 Quitting driver...");
                driver.quit();
            }
        }
    }
}
