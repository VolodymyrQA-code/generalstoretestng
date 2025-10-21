package base;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.net.URL;

public class IOSBasePage {
    protected static IOSDriver driver;
    protected static AppiumDriverLocalService service;

    @BeforeAll
    public static void setup() throws Exception {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withArgument(() -> "--base-path", "/wd/hub")
                .build();
        service.start();

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setDeviceName("iPhone 17")
                .setPlatformVersion("26.0")
                .setApp("/Users/IUAR0044/generalstore/AppIos/TheApp.app")
                .setNoReset(true)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setAutomationName("XCUITest");

        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("✅ iOS Driver запущено");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🧹 Драйвер закрито");
        }
        if (service != null) {
            service.stop();
            System.out.println("🧩 Appium сервер зупинено");
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
