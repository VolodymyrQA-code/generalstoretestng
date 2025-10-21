package base;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.net.URL;

public class IOSBasePage {
    protected static IOSDriver driver;

    @BeforeAll
    public static void setup() throws Exception {
        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setDeviceName(System.getenv().getOrDefault("IOS_DEVICE", "iPhone 14"))
                .setPlatformVersion("18.6")
                .setApp(System.getProperty("user.dir") + "/AppIos/TheApp.app")
                .setNoReset(true)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setAutomationName("XCUITest")
                // 🟢 додаємо додаткові таймаути для стабільності на GitHub Actions
                .setWdaLaunchTimeout(Duration.ofSeconds(120))
                .setWdaConnectionTimeout(Duration.ofSeconds(120))
                .setCommandTimeouts(Duration.ofMinutes(3));

        System.out.println("🚀 Створюємо iOS Driver...");
        driver = new IOSDriver(new URL("http://127.0.0.1:4725/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("✅ iOS Driver запущено успішно!");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🧹 Драйвер закрито");
        }
    }

    public static void takeScreenshot(String name) {
        try {
            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            Files.createDirectories(Paths.get("screenshots"));
            Files.write(Paths.get("screenshots/" + name + ".png"), screenshot);
            System.out.println("📸 Скриншот збережено: " + name + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
