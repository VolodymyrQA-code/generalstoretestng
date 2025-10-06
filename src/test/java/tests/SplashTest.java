package tests;

import java.sql.DriverManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import base.BasePage;
import pages.SplashPage;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
public class SplashTest extends BasePage {

    @Test
public void testSplashIsDisplayed() {
    SplashPage splashPage = new SplashPage(driver);
    splashPage.openSplashPage(); // assert всередині методу
}
    
}
