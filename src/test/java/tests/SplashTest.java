package tests;

import base.BasePage;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import pages.SplashPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SplashTest extends BasePage {

    private SplashPage splashPage;

    @BeforeAll
    public void initPage() {
        // Використовуємо готовий драйвер із BasePage
        AndroidDriver driver = BasePage.getDriver();
        splashPage = new SplashPage(driver);
    }

    @Test
    @DisplayName("Splash screen appears or home screen is ready")
    public void testSplashAppears() {
        boolean splashShown = splashPage.isSplashDisplayed();
        Assertions.assertTrue(
            splashShown,
            "Splash screen should appear or home screen should be ready"
        );
    }
}
