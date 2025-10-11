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
            // 🔧 Отримуємо шлях до APK з environment або з локального каталогу
            String apkPath = System.getenv("APK_PATH");
            if (apkPath == null || apkPath.isEmpty()) {
                apkPath = System.getProperty("user.dir") + "/General-Store.apk";
            }

            System.out.println("📦 Using APK path: " + apkPath);

            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("emulator-5554")
                    .setApp(apkPath)  // ✅ Використовуємо динамічний шлях
                    .setAppPackage("com.androidsample.generalstore")
                    .setAppActivity("com.androidsample.generalstore.SplashActivity")
                    .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);

            if (isCI()) {
                options.setCapability("appium:ignoreHiddenApiPolicyError", true);
                options.setCapability("adbExecTimeout", 1200000);
                options.setCapability("uiautomator2ServerInstallTimeout", 120000);
                options.setCapability("uiautomator2ServerLaunchTimeout", 120000);
                options.setCapability("appWaitActivity", "com.androidsample.generalstore.MainActivity");
                options.setCapability("newCommandTimeout", 300);
                options.setCapability("fullReset", true);
                options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(180));
            } else {
                options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(20));
            }

            if (isCI()) {
                Thread.sleep(10000);
            }

            String appiumUrl = "http://127.0.0.1:4723/wd/hub";
            System.out.println("Environment: " + (isCI() ? "CI (GitHub Actions)" : "Local"));
            System.out.println("Connecting to Appium at: " + appiumUrl);

            driver = new AndroidDriver(new URL(appiumUrl), options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(isCI() ? 30 : 15));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            try {
                driver.removeApp("com.androidsample.generalstore");
            } catch (Exception e) {
                System.out.println("Не вдалося видалити додаток: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }

    // ✅ Метод для доступу до драйвера
    public static AndroidDriver getDriver() {
        return driver;
    }
}
