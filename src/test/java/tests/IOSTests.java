package tests;

import base.IOSBasePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.IOSHomePage;
import io.appium.java_client.AppiumBy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IOSTests extends IOSBasePage {

    private IOSHomePage home;

    @BeforeEach
    void setupHomePage() {
        home = new IOSHomePage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));
    }

    @Test
    @Order(1)
    void testAppLaunch() {
        System.out.println("=== Тест 1: Запуск додатку ===");
        assertNotNull(driver.queryAppState("com.cloudinary.theapp"));
    }

    @Test
    @Order(2)
    void testTapButton() throws InterruptedException {
        home.tapLoginScreenButton();
    }

    @Test
    @Order(3)
    void testInputText() {
        home.enterText("test@gmail.com");
    }

    @Test
    @Order(4)
    void testVerifyText() {
        home.verifyTextExists();
    }

    @Test
    @Order(5)
    void testSwipeAction() {
        home.swipeVertical();
    }

    @Test
    @Order(6)
    void testScreenshot() {
        IOSBasePage.takeScreenshot("screenshot");
    }

    @Test
    @Order(7)
    void testGetPageSource() {
        assertTrue(driver.getPageSource().length() > 0);
    }

    @Test
    @Order(8)
    void testDeviceOrientation() {
        assertNotNull(driver.getOrientation());
    }

    @Test
    @Order(9)
    void testFindElements() {
        assertTrue(driver.findElements(AppiumBy.xpath("//XCUIElementTypeButton")).size() > 0);
    }

    @Test
    @Order(10)
    void testWaitForElement() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        assertNotNull(wait.until(d -> d.findElement(AppiumBy.xpath("//XCUIElementTypeButton"))));
    }
}
