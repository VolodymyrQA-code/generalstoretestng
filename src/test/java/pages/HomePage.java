package pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import utils.CommonUtilsAndroid;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class HomePage {
    private CommonUtilsAndroid utils;
    private AndroidDriver driver;
    private WebDriverWait wait;

    private final By CountryDropdown = By.id("com.androidsample.generalstore:id/spinnerCountry");
    private final By CountryList = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout");
    private final By selectedCountryText = By.id("android:id/text1");
    private final By nameField = By.id("com.androidsample.generalstore:id/nameField");
    private final By maleRadio = By.id("com.androidsample.generalstore:id/radioMale");
    private final By femaleRadio = By.id("com.androidsample.generalstore:id/radioFemale");
    private final By letsShopBtn = By.id("com.androidsample.generalstore:id/btnLetsShop");
    private final By ToolbarTitle = By.id("com.androidsample.generalstore:id/toolbar_title");
    private final By CountryTitle = By.xpath("//android.widget.TextView[@text='Select the country where you want to shop']");
    private final By YourNameTitle = By.xpath("//android.widget.TextView[@text='Your Name']");
    private final By GenderTitle = By.xpath("//android.widget.TextView[@text='Gender']");

    public HomePage(AndroidDriver driver, WebDriverWait wait) {
    this.driver = driver;
    this.wait = wait;
    this.utils = new CommonUtilsAndroid(driver);
    }
    
    public void verifyToolbarTitle() {
    utils.verifyText(ToolbarTitle, "General Store");
    }

    public void verifyCountryTitle() {
    utils.verifyText(CountryTitle, "Select the country where you want to shop");
    }

    public void verifyYourNameTitle() {
    utils.verifyText(YourNameTitle, "Your Name");
    }

    public void verifyGenderTitle() {
    utils.verifyText(GenderTitle, "Gender");
    }

    public void verifyMaleRadio() {
    utils.verifyText(maleRadio, "Male");
    }

    public void verifyFemaleRadio() {
    utils.verifyText(femaleRadio, "Female");
    }

    public void verifyLetsShopBtnTitle() {
    utils.verifyText(letsShopBtn, "Let's Shop");
    }

    

    public void selectAndVerifyCountry(String countryName) {
    try {
        utils.click(CountryDropdown);
        System.out.println("[STEP] Відкрито список країн");
        utils.scrollToTextAndClick(countryName);
        System.out.println("[STEP] Обрано країну: " + countryName);

        String actualCountry = utils.getText(selectedCountryText).trim();
        if (countryName.trim().equals(actualCountry)) {
            System.out.println("[PASS] Вибрана країна відображається правильно: " + actualCountry);
        } else {
            System.out.println("[FAIL] Очікувалась '" + countryName + "', але відображається '" + actualCountry + "'");
            throw new AssertionError("Country mismatch: expected " + countryName + " but found " + actualCountry);
        }
    } catch (Exception e) {
        System.out.println("[ERROR] Не вдалося вибрати країну '" + countryName + "': " + e.getMessage());
        throw e;
    }
}

    public void openAndCloseCountryList() {
            utils.click(CountryDropdown);
            if (utils.isElementDisplayed(CountryList)) {
            System.out.println("[INFO] Список країн відображається після кліку по CountryDropdown");
            } else {
            throw new AssertionError("Country list не відображається після кліку по CountryDropdown");
            }
            utils.clickOutside(CountryList);
            if (!utils.isElementDisplayed(CountryList)) {
            System.out.println("[INFO] Country list сховався після кліку в сторону");
            } else {
            throw new AssertionError("Country list досі відображається після кліку в сторону");
            }
            }

    public void enterAndVerifyValidName(String name) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
    utils.click(nameField);
    utils.type(nameField, name);
    utils.verifyText(nameField, name); 
    utils.closeKeyboard();
    }

    public void checkEmptyName() {
    utils.click(letsShopBtn);
    boolean isErrorVisible = utils.isTextVisibleNow("Please enter your name");
    if (isErrorVisible) {
        System.out.println("[PASS] Повідомлення про помилку 'Please enter your name' відображається");
    } else {
        System.out.println("[FAIL] Повідомлення про помилку не з'явилось");
        throw new AssertionError("Повідомлення про помилку 'Please enter your name' не з'явилось");
    }
}

    public void verifyMaleRadioSelected() {
    WebElement maleRadioElement = wait.until(ExpectedConditions.elementToBeClickable(maleRadio));
    maleRadioElement.click();
    boolean isChecked = Boolean.parseBoolean(maleRadioElement.getAttribute("checked"));
    assertTrue(isChecked, "Male radio button should be selected");
}

public void verifyFemaleRadioSelected() {
    WebElement femaleRadioElement = wait.until(ExpectedConditions.elementToBeClickable(femaleRadio));
    femaleRadioElement.click();
    boolean isChecked = Boolean.parseBoolean(femaleRadioElement.getAttribute("checked"));
    assertTrue(isChecked, "Female radio button should be selected");
}


    public void verifyFemaleRadioNotSelected() {
    utils.verifySelectedState(femaleRadio, false);
    }

    public void verifyMaleRadioNotSelected() {
    utils.verifySelectedState(maleRadio, false);
    }
    
     public void clickLetsShop() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        utils.click(letsShopBtn);
    }
}