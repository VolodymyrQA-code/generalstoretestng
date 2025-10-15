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
        AndroidDriver driver = BasePage.getDriver();
        splashPage = new SplashPage(driver);
    }

   @Test
@DisplayName("SplashActivity is displayed on launch")
public void testSplashAppears() {
    boolean splashFound = splashPage.isSplashDisplayed();
    Assertions.assertTrue(splashFound, "Splash screen was not found!");
}
}
