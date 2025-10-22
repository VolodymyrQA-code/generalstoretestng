package base;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;

/**
 * Базовий клас для ініціалізації iOS-драйвера та допоміжних методів.
 */
public class IOSBasePage {

    protected static IOSDriver driver;

    /**
     * 🪵 DEBUG: Створення або перевірка існуючого iOS Driver.
     */
    public void startDriver() {
        if (driver != null) {
            System.out.println("⚠️ LOGGING: Драйвер вже існує, повторне створення пропущено.");
            return;
        }

        System.out.println("🚀 LOGGING: Ініціалізуємо iOS Driver...");

        try {
            // 🪵 DEBUG: 1️⃣ Отримуємо шлях до .app або .ipa
            String appPath = System.getenv("APP_PATH");

            if (appPath == null || appPath.isEmpty()) {
                File localApp = new File("/Users/IUAR0044/generalstore/AppIos/TheApp.app");
                if (localApp.exists()) {
                    appPath = localApp.getAbsolutePath();
                    System.out.println("📦 LOGGING: Використано локальний .app → " + appPath);
                } else {
                    System.out.println("⚠️ LOGGING: Не знайдено .app/.ipa — використовуємо bundleId");
                }
            } else {
                System.out.println("📦 LOGGING: APP_PATH з env → " + appPath);
            }

            // 🪵 DEBUG: 2️⃣ Формуємо опції драйвера
            BaseOptions options = new BaseOptions()
                    .amend("platformName", "iOS")
                    .amend("automationName", "XCUITest")
                    .amend("deviceName", "iPhone 16 Pro")
                    .amend("platformVersion", "18.4")
                    .amend("udid", System.getenv("SIM_UDID"))
                    .amend("newCommandTimeout", 300)
                    .amend("noReset", false);

            if (appPath != null && !appPath.isEmpty()) {
                options.amend("app", appPath);
            } else {
                options.amend("bundleId", "com.cloudinary.theapp");
            }

            // 🪵 DEBUG: 3️⃣ Створюємо драйвер
            System.out.println("🧠 LOGGING: Створюємо iOSDriver на порту 4725...");
            driver = new IOSDriver(new URL("http://127.0.0.1:4725/wd/hub"), options);
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
     * 🧹 Закриття драйвера.
     */
    public void stopDriver() {
        if (driver != null) {
            System.out.println("🧹 LOGGING: Закриваємо iOS Driver...");
            driver.quit();
            driver = null;
            System.out.println("✅ LOGGING: Драйвер успішно закрито.");
        } else {
            System.out.println("⚠️ LOGGING: Драйвер не запущений, закривати нічого.");
        }
    }

    /**
     * 📸 Збереження скриншоту.
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
