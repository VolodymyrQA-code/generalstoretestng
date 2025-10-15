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
        long startTime = System.currentTimeMillis();

        boolean splashShown = splashPage.isSplashDisplayed();

        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("⏱ Splash check elapsed time: " + elapsedSeconds + " seconds");

        Assertions.assertTrue(
            splashShown,
            "Splash screen should appear or home screen should be ready"
        );
    }
}
