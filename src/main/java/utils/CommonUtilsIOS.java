package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import io.appium.java_client.ios.IOSDriver;
import java.time.Duration;
import java.util.Collections;

public class CommonUtilsIOS {

    private final AppiumDriver driver;

    public CommonUtilsIOS(AppiumDriver driver) {
        this.driver = driver;
    }

    // --- Базові дії ---
    public void tapElement(By locator) {
        driver.findElement(locator).click();
    }

    public void enterText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void swipeVertical() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    // --- Додаткові методи ---
    public void click(By locator) { tapElement(locator); }

    public void verifyText(By locator, String expectedText) {
        String actual = driver.findElement(locator).getText();
        if (!expectedText.equals(actual)) {
            throw new AssertionError("Expected: " + expectedText + ", but found: " + actual);
        }
    }

    public void scrollToTextAndClick(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"));"
        )).click();
    }

    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void type(By locator, String text) { enterText(locator, text); }

    public void closeKeyboard() {
        // hideKeyboard доступний лише в iOSDriver/AndroidDriver
        try {
            if (driver.getClass().getName().contains("AndroidDriver")) {
            ((io.appium.java_client.android.AndroidDriver) driver).hideKeyboard();
            } else if (driver.getClass().getName().contains("IOSDriver")) {
            ((io.appium.java_client.ios.IOSDriver) driver).hideKeyboard();
        }
        } catch (Exception e) {
            // клавіатура може бути вже прихована
        }
    }

    public boolean isTextVisibleNow(String text) {
        return !driver.findElements(AppiumBy.xpath("//*[contains(@text,'" + text + "')]")).isEmpty();
    }

    public void verifySelectedState(By locator, boolean expected) {
        WebElement element = driver.findElement(locator);
        boolean actual = Boolean.parseBoolean(element.getAttribute("checked"));
        if (actual != expected) {
            throw new AssertionError("Expected selected state: " + expected + ", but was: " + actual);
        }
    }

    public void clickOutside(By locator) {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int y = size.height - 10;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }
}
