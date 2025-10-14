package tests;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import pages.SplashPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SplashTest {

    private AndroidDriver driver;
    private SplashPage splashPage;

    @BeforeAll
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("app", System.getProperty("user.dir") + "/app/General-Store.apk");
        caps.setCapability("automationName", "UIAutomator2");
        caps.setCapability("autoGrantPermissions", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        splashPage = new SplashPage(driver);
    }

    @Test
    @DisplayName("Splash screen appears or home screen is ready")
    public void testSplashAppears() {
        boolean splashShown = splashPage.isSplashDisplayed();
        Assertions.assertTrue(splashShown, "Splash screen should appear or home screen should be ready");
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
