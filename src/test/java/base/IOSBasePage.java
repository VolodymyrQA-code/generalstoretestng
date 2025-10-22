package base;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;

public class IOSBasePage {

    protected static IOSDriver driver; // один драйвер для всіх тестів

    /**
     * Створює драйвер, якщо він ще не існує
     */
    public void startDriver() {
        if (driver != null) {
            System.out.println("⚠️ LOGGING: Драйвер вже існує, повторне створення пропущено.");
            return;
        }

        System.out.println("🚀 LOGGING: Ініціалізуємо iOS Driver...");

        try {
            BaseOptions options = new BaseOptions()
                    .amend("platformName", "iOS")
                    .amend("automationName", "XCUITest")
                    .amend("deviceName", "iPhone 16 Pro")
                    .amend("platformVersion", "18.4")
                    .amend("udid", System.getenv("SIM_UDID"))
                    .amend("app", "/Users/runner/work/ios-app.ipa")
                    .amend("newCommandTimeout", 300)
                    .amend("noReset", false);

            driver = new IOSDriver(
                    new URL("http://127.0.0.1:4725/wd/hub"),
                    options
            );

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("✅ LOGGING: iOS Driver створено успішно.");

        } catch (MalformedURLException e) {
            System.err.println("❌ LOGGING: Невірний URL Appium сервера — " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ LOGGING: Помилка створення драйвера — " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Метод для збереження скриншоту
     */
    public static void takeScreenshot(String name) {
        if (driver == null) {
            System.err.println("⚠️ LOGGING: Неможливо зробити скриншот — driver == null");
            return;
        }
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(name + ".png");
            Files.copy(src.toPath(), dest.toPath());
            System.out.println("📸 LOGGING: Скриншот збережено → " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ LOGGING: Помилка при збереженні скриншоту — " + e.getMessage());
        }
    }
}
