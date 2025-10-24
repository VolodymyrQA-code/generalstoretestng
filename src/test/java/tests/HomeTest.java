package tests;

import base.BasePage;
import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.SplashPage;
import utils.CommonUtilsAndroid;
import utils.ConfigReader;
import java.time.Duration;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class HomeTest extends BasePage {

    private HomePage home;
    private SplashPage splash;
    private CommonUtilsAndroid utils;

    @BeforeClass
    public void setupHomePage() {
        // Ініціалізація WebDriverWait і HomePage перед усіма тестами
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        home = new HomePage(driver, wait);
        utils = new CommonUtilsAndroid(driver);
    }

    @Test(priority = 1)
    @Description("Перевіряємо, що заголовок тулбара відображається і має текст 'General Store'")
    public void testToolbarTitle() {
        home.verifyToolbarTitle();
    }

    @Test(priority = 2)
    @Description("Перевіряємо, що заголовок для вибору країни відображається і відповідає очікуваному тексту")
    public void testCountryTitle() {
        home.verifyCountryTitle();
    }

    @Test(priority = 3)
    @Description("Перевіряємо, що заголовок поля для введення імені користувача відображається")
    public void testYourNameTitle() {
        home.verifyYourNameTitle();
    }

    @Test(priority = 4)
    @Description("Перевіряємо, що заголовок для вибору статі відображається")
    public void testGenderTitle() {
        home.verifyGenderTitle();
    }

    @Test(priority = 5)
    @Description("Перевіряємо, що підпис для чоловічого радіо-батону відображається")
    public void testMaleRadioTitle() {
        home.verifyMaleRadio();
    }

    @Test(priority = 6)
    @Description("Перевіряємо, що підпис для жіночого радіо-батону відображається")
    public void testFemaleRadioTitle() {
        home.verifyFemaleRadio();
    }

    @Test(priority = 7)
    @Description("Перевіряємо, що кнопка 'Let's Shop' відображається і має правильний текст")
    public void testLetsShopBtnTitle() {
        home.verifyLetsShopBtnTitle();
    }

    @Test(priority = 8)
    @Description("Перевіряємо, що країна зі списку обрана")
    @Severity(SeverityLevel.CRITICAL)
    public void testSelectCountryFromProperties() {
        String country = ConfigReader.getProperty("countryName");
        home.selectAndVerifyCountry(country);
    }

    @Test(priority = 9)
    @Description("Перевіряємо закриття списку країн після кліку поза ним")
    public void testOpenAndCloseCountryList() {
        home.openAndCloseCountryList();
    }

    @Test(priority = 10)
    @Description("Перевіряємо ерор при порожньому імені")
    public void testVerifyErrorOnEmptyName() {
        home.checkEmptyName();
    }

    @Test(priority = 11)
    @Description("Перевіряємо, що введене ім'я відображається у полі після введення")
    @Severity(SeverityLevel.CRITICAL)
    public void testVerifyValidityOfEnteredName() {
        String userName = ConfigReader.getProperty("userName");
        home.enterAndVerifyValidName(userName);
    }

    @Test(priority = 12)
    @Description("Перевіряємо, що жіночий радіо-батон обраний, а чоловічий не обраний")
    public void testVerifyFemaleRadioButtonSelected() {
        home.verifyFemaleRadioSelected();
        home.verifyMaleRadioNotSelected();
    }

    @Test(priority = 13)
    @Description("Перевіряємо, що чоловічий радіо-батон обраний, а жіночий не обраний")
    @Severity(SeverityLevel.CRITICAL)
    public void testVerifyMaleRadioButtonSelected() {
        home.verifyMaleRadioSelected();
        home.verifyFemaleRadioNotSelected();
    }
}
