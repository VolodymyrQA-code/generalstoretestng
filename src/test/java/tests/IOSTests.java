package tests;

import base.IOSBasePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.IOSHomePage;
import io.appium.java_client.AppiumBy;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IOSTests extends IOSBasePage {

    private IOSHomePage home;

    @BeforeAll
    void setupOnce() {
        System.out.println("🚀 LOGGING: Створюємо iOS Driver (одноразово для всіх тестів)...");
        startDriver(); // створює driver (з базового класу)
        home = new IOSHomePage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));
        System.out.println("✅ LOGGING: iOS Driver запущено успішно!");
    }

    @AfterAll
    void tearDownOnce() {
        System.out.println("🧹 LOGGING: Закриваємо iOS Driver після всіх тестів...");
        if (driver != null) {
            driver.quit();
            System.out.println("✅ LOGGING: Драйвер закрито.");
        }
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
        System.out.println("=== Тест 2: Тап по кнопці ===");
        home.tapLoginScreenButton();
    }

    @Test
    @Order(3)
    void testInputText() {
        System.out.println("=== Тест 3: Введення тексту ===");
        home.enterText("test@gmail.com");
    }

    @Test
    @Order(4)
    void testVerifyText() {
        System.out.println("=== Тест 4: Перевірка наявності тексту ===");
        home.verifyTextExists();
    }

    @Test
    @Order(5)
    void testSwipeAction() {
        System.out.println("=== Тест 5: Свіп ===");
        home.swipeVertical();
    }

    @Test
    @Order(6)
    void testScreenshot() {
        System.out.println("=== Тест 6: Скриншот ===");
        IOSBasePage.takeScreenshot("screenshot_test6");
    }

    @Test
    @Order(7)
    void testGetPageSource() {
        System.out.println("=== Тест 7: Перевірка джерела сторінки ===");
        assertTrue(driver.getPageSource().length() > 0);
    }

    @Test
    @Order(8)
    void testDeviceOrientation() {
        System.out.println("=== Тест 8: Орієнтація пристрою ===");
        assertNotNull(driver.getOrientation());
    }

    @Test
    @Order(9)
    void testFindElements() {
        System.out.println("=== Тест 9: Пошук елементів ===");
        assertTrue(driver.findElements(AppiumBy.xpath("//XCUIElementTypeButton")).size() > 0);
    }

    @Test
    @Order(10)
    void testWaitForElement() {
        System.out.println("=== Тест 10: Очікування елемента ===");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        assertNotNull(wait.until(d -> d.findElement(AppiumBy.xpath("//XCUIElementTypeButton"))));
    }
}
