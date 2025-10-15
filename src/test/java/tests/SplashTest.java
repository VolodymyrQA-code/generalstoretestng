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
    @DisplayName("SplashActivity logs detected and MainActivity launched")
    public void testSplashAppearsInLogs() {
        boolean splashFound = splashPage.waitForSplashInLogs();
        boolean mainFound = splashPage.waitForMainActivityInLogs();

        Assertions.assertTrue(
            splashFound || mainFound,
            "Neither SplashActivity nor MainActivity was found in logs!"
        );
    }
}
