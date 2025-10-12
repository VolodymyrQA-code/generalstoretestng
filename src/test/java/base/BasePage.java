package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;

public class BasePage {
    protected static AndroidDriver driver;
    protected static WebDriverWait wait;

    private static boolean isCI() {
        String ci = System.getenv("CI");
        return ci != null && ci.equalsIgnoreCase("true");
    }

    public boolean isTextEqual(By locator, String expectedText) {
        try {
            String actualText = driver.findElement(locator).getText().trim();
            return actualText.equals(expectedText.trim());
        } catch (NoSuchElementException e) {
            return false;
        }
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
                    .setAppActivity("com.androidsample.generalstore.MainActivity") // ✅ оновлено
                    .setAppWaitActivity("com.androidsample.generalstore.*") // ✅ дозволяє чекати будь-яку активність у пакеті
                    .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);

            if (isCI()) {
                System.out.println("🏗️ Running in CI mode, increasing timeouts and stability options...");
                options.setCapability("appium:ignoreHiddenApiPolicyError", true);
                options.setCapability("adbExecTimeout", 1200000);
                options.setCapability("uiautomator2ServerInstallTimeout", 180000);
                options.setCapability("uiautomator2ServerLaunchTimeout", 180000);
                options.setCapability("newCommandTimeout", 300);
                options.setCapability("fullReset", false); // ✅ не перевстановлюй кожного разу
                options.setCapability("appium:clearDeviceLogsOnStart", true);
                options.setCapability("autoGrantPermissions", true);
            } else {
                options.setCapability("fullReset", false);
                options.setCapability("newCommandTimeout", 300);
                options.setCapability("autoGrantPermissions", true);
            }

            // Додаткова пауза на CI для стабільного запуску після емулятора
            if (isCI()) {
                System.out.println("⏳ Waiting for app to stabilize (CI delay)...");
                Thread.sleep(20000); // 20 секунд
            }

            String appiumUrl = "http://127.0.0.1:4723/wd/hub";
            System.out.println("Environment: " + (isCI() ? "CI (GitHub Actions)" : "Local"));
            System.out.println("Connecting to Appium at: " + appiumUrl);

            driver = new AndroidDriver(new URL(appiumUrl), options);

            // Більший timeout для CI
            int waitSeconds = isCI() ? 40 : 20;
            wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

            System.out.println("✅ Driver initialized successfully with " + waitSeconds + "s wait timeout");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            try {
                System.out.println("🧹 Cleaning up: removing app...");
                driver.removeApp("com.androidsample.generalstore");
            } catch (Exception e) {
                System.out.println("⚠️ Не вдалося видалити додаток: " + e.getMessage());
            } finally {
                System.out.println("🔚 Quitting driver...");
                driver.quit();
            }
        }
    }

    public static AndroidDriver getDriver() {
        return driver;
    }
}
