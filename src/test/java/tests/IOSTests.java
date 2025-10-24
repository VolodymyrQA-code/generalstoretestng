package tests;

import base.IOSBasePage;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.IOSHomePage;
import io.appium.java_client.AppiumBy;

import java.time.Duration;

import static org.testng.Assert.*;

public class IOSTests extends IOSBasePage {

    private IOSHomePage home;

    @BeforeClass
    public void setupHomePage() {
        // Використовуємо driver з базового класу
        home = new IOSHomePage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));
    }

    @Test(priority = 1)
    public void testAppLaunch() {
        System.out.println("=== Тест 1: Запуск додатку ===");
        assertNotNull(driver.queryAppState("com.cloudinary.theapp"));
    }

    @Test(priority = 2)
    public void testTapButton() throws InterruptedException {
        System.out.println("=== Тест 2: Тап по кнопці ===");
        home.tapLoginScreenButton();
    }

    @Test(priority = 3)
    public void testInputText() {
        System.out.println("=== Тест 3: Введення тексту ===");
        home.enterText("test@gmail.com");
    }

    @Test(priority = 4)
    public void testVerifyText() {
        System.out.println("=== Тест 4: Перевірка наявності тексту ===");
        home.verifyTextExists();
    }

    @Test(priority = 5)
    public void testSwipeAction() {
        System.out.println("=== Тест 5: Свіп ===");
        home.swipeVertical();
    }

    @Test(priority = 6)
    public void testScreenshot() {
        System.out.println("=== Тест 6: Скриншот ===");
        IOSBasePage.takeScreenshot("screenshot_test6");
    }

    @Test(priority = 7)
    public void testGetPageSource() {
        System.out.println("=== Тест 7: Перевірка джерела сторінки ===");
        assertTrue(driver.getPageSource().length() > 0);
    }

    @Test(priority = 8)
    public void testDeviceOrientation() {
        System.out.println("=== Тест 8: Орієнтація пристрою ===");
        assertNotNull(driver.getOrientation());
    }

    @Test(priority = 9)
    public void testFindElements() {
        System.out.println("=== Тест 9: Пошук елементів ===");
        assertTrue(driver.findElements(AppiumBy.xpath("//XCUIElementTypeButton")).size() > 0);
    }

    @Test(priority = 10)
    public void testWaitForElement() {
        System.out.println("=== Тест 10: Очікування елемента ===");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        assertNotNull(wait.until(d -> d.findElement(AppiumBy.xpath("//XCUIElementTypeButton"))));
    }
}
