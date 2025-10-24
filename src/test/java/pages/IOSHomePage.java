package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CommonUtilsIOS;
import base.IOSBasePage;

import static org.testng.Assert.assertTrue;

public class IOSHomePage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final CommonUtilsIOS utils;

    private final By loginScreenButton = By.xpath("//XCUIElementTypeOther[@name='Login Screen A fake login screen for testing']");
    private final By inputField = By.xpath("//XCUIElementTypeTextField");
    private final By staticTextElements = By.xpath("//XCUIElementTypeStaticText");

    public IOSHomePage(AppiumDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.utils = new CommonUtilsIOS(driver);
    }

    public void tapLoginScreenButton() throws InterruptedException {
        utils.tapElement(loginScreenButton);
        Thread.sleep(2000);
    }

    public void enterText(String text) {
        utils.enterText(inputField, text);
    }

    public void verifyTextExists() {
        assertTrue(utils.isElementPresent(staticTextElements), "Текстові елементи не знайдено");
    }

    public void swipeVertical() {
        utils.swipeVertical();
    }
}
