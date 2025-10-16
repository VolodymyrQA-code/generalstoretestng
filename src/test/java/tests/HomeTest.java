package tests;

import base.BasePage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.SplashPage;
import utils.CommonUtils;
import utils.ConfigReader;
import java.time.Duration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@TestMethodOrder(OrderAnnotation.class)
public class HomeTest extends BasePage {
    private HomePage home;
    private SplashPage splash;
    private CommonUtils utils;

    @BeforeEach
    void setupHomePage() {
        // Ініціалізація WebDriverWait і HomePage перед кожним тестом
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        home = new HomePage(driver, wait);
        utils = new CommonUtils(driver);
    }

    @Test 
    @Order(1) 
    @Description("Перевіряємо, що заголовок тулбара відображається і має текст \"General Store\"")
    public void testToolbarTitle() {
        home.verifyToolbarTitle();
    }

    @Test 
    @Order(2) 
    @Description("Перевіряємо, що заголовок для вибору країни відображається і відповідає очікуваному тексту")
    public void testCountryTitle() {
        home.verifyCountryTitle();
    }
    @Test 
    @Order(3) 
    @Description("Перевіряємо, що заголовок поля для введення імені користувача відображається")
    public void testYourNameTitle() {
        home.verifyYourNameTitle();
    }

    @Test 
    @Order(4) 
    @Description("Перевіряємо, що заголовок для вибору статі відображається")
    public void testGenderTitle() {
        home.verifyGenderTitle();
}

    @Test 
    @Order(5) 
    @Description("Перевіряємо, що підпис для чоловічого радіо-батону відображається")
    public void testMaleRadioTitle() {
        home.verifyMaleRadio();
    }

    @Test 
    @Order(6) 
    @Description("Перевіряємо, що підпис для жіночого радіо-батону відображається")
    public void testFemaleRadioTitle() {
        home.verifyFemaleRadio();
    }

    @Test 
    @Order(7) 
    @Description("Перевіряємо, що кнопка \"Let's Shop\" відображається і має правильний текст")
    public void testLetsShopBtnTitle() {
        home.verifyLetsShopBtnTitle();
    }

    @Test 
    @Order(8) 
    @Description("Перевіряємо, що країна зі списку обрана")
    @Severity(SeverityLevel.CRITICAL) 
    public void testSelectCountryFromProperties() {
        String country = ConfigReader.getProperty("countryName"); // читаємо з config.properties
        home.selectAndVerifyCountry(country);
    }

    @Test @Order(9) @Description("Перевіряємо закриття списку країн після кліку поза ним")
    public void testOpenAndCloseCountryList() {
        home.openAndCloseCountryList();
    }

    @Test 
    @Order(10) 
    @Description("Перевіряємо ерор при порожньому імені")
    public void testVerifyErrorOnEmptyName() {
        home.checkEmptyName();
    }

    @Test 
    @Order(11) 
    @Description("Перевіряємо, що введене ім'я відображається у полі після введення")
    @Severity(SeverityLevel.CRITICAL)
    public void testVerifyValidityOfEnteredName() {
        String userName = ConfigReader.getProperty("userName"); // читаємо з config.properties
        home.enterAndVerifyValidName(userName);
    }

    @Test 
    @Order(12) 
    @Description("Перевіряємо, що жіночий радіо-батон обраний, а чоловічий не обраний")
    public void testVerifyFealeRadioButtonSelected() {
        home.verifyFemaleRadioSelected();
        home.verifyMaleRadioNotSelected();
    }

    @Test 
    @Order(13) 
    @Description("Перевіряємо, що чоловічий радіо-батон обраний, а жіночий не обраний")
    @Severity(SeverityLevel.CRITICAL)
    public void testVerifyMaleRadioButtonSelected() {
        home.verifyMaleRadioSelected();
        home.verifyFemaleRadioNotSelected();
    }
}
