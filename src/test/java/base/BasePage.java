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

    @BeforeAll
    static void setup() {
        try {
            String apkPath = System.getenv("APK_PATH");
            if (apkPath == null || apkPath.isEmpty()) {
                apkPath = System.getProperty("user.dir") + "/General-Store.apk";
            }

            System.out.println("📦 Using APK path: " + apkPath);

            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("emulator-5554")
                    .setApp(apkPath)
                    .setAppPackage("com.androidsample.generalstore")
                    .setAppActivity("com.androidsample.generalstore.SplashActivity")
                    .setAppWaitActivity("com.androidsample.generalstore.*")
                    .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                    .eventTimings();
            // ✅ CI-specific tuning
            if (isCI()) {
                System.out.println("🏗️ Running in CI mode: applying stability and timeouts...");
                options.setCapability("appium:ignoreHiddenApiPolicyError", true);
                options.setCapability("adbExecTimeout", 600000);
                options.setCapability("uiautomator2ServerInstallTimeout", 180000);
                options.setCapability("uiautomator2ServerLaunchTimeout", 180000);
                options.setCapability("newCommandTimeout", 600);
                options.setCapability("fullReset", false);
                options.setCapability("autoGrantPermissions", true);
                options.setCapability("appium:clearDeviceLogsOnStart", true);
            } else {
                options.setCapability("fullReset", false);
                options.setCapability("autoGrantPermissions", true);
                options.setCapability("newCommandTimeout", 300);
            }

            // ⏳ Pause to allow emulator & app to stabilize
            if (isCI()) {
                System.out.println("⏳ Waiting for app to stabilize (CI delay 5s)...");
                Thread.sleep(5000);
            }

            String appiumUrl = "http://127.0.0.1:4723/wd/hub";
            System.out.println("🌐 Connecting to Appium at: " + appiumUrl);

            int retryCount = 0;
            int maxRetries = 3;
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

            int waitSeconds = isCI() ? 45 : 20;
            wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

            // ✅ Wait for splash/main screen
            try {
                System.out.println("👀 Waiting for splash screen to finish...");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*")));
                System.out.println("✅ App is ready for testing.");
            } catch (TimeoutException e) {
                System.out.println("⚠️ Splash screen timeout — continuing anyway.");
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

    public boolean isTextEqual(By locator, String expectedText) {
        try {
            String actualText = driver.findElement(locator).getText().trim();
            return actualText.equals(expectedText.trim());
        } catch (NoSuchElementException e) {
            return false;
        }
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
}
