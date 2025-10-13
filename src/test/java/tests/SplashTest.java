package tests;

import java.sql.DriverManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.appium.java_client.android.AndroidDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;


import base.BasePage;
import pages.SplashPage;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
public class SplashTest extends BasePage {

    @Test
    public void testSplashAppears() {
        AndroidDriver driver = BasePage.getDriver();
        SplashPage splashPage = new SplashPage(driver);

        boolean splashFound = splashPage.isSplashDisplayed();
        assertTrue(splashFound, "Splash screen should appear at least once");
    }
}

