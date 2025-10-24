package tests;

import base.BasePage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SplashPage;

import static org.testng.Assert.assertTrue;

public class SplashTest extends BasePage {

    private SplashPage splashPage;

    @BeforeClass
    public void initPage() {
        AndroidDriver driver = BasePage.getDriver();
        splashPage = new SplashPage(driver);
    }

    @Test(description = "SplashActivity is displayed on launch")
    public void testSplashAppears() {
        boolean splashFound = splashPage.isSplashDisplayed();
        assertTrue(splashFound, "Splash screen was not found!");
    }
}
