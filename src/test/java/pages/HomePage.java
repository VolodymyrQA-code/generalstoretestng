package pages;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;



public class HomePage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    private final By countryDropdown = By.id("com.androidsample.generalstore:id/spinnerCountry");
    private final By nameField = By.id("com.androidsample.generalstore:id/nameField");
    private final By maleRadio = By.id("com.androidsample.generalstore:id/radioMale");
    private final By femaleRadio = By.id("com.androidsample.generalstore:id/radioFemale");
    private final By letsShopBtn = By.id("com.androidsample.generalstore:id/btnLetsShop");
    private final By toolbarTitle = By.id("com.androidsample.generalstore:id/toolbar_title");
    private final By countryTitle = By.xpath("//android.widget.TextView[@text='Select the country where you want to shop']");
    private final By YourNameTitle = By.xpath("//android.widget.TextView[@text='Your Name']");
    private final By GenderTitle = By.xpath("//android.widget.TextView[@text='Gender']");

    
    public HomePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    



    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void selectCountry(String country) {
        waitForElement(countryDropdown).click();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + country + "\"))")).click();
    }    



    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

   public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("Male"))
            driver.findElement(maleRadio).click();
        else
            driver.findElement(femaleRadio).click();
    }

    public boolean isElementDisplayed(By locator) {
    try {
        WebElement el = driver.findElement(locator);
        return el.isDisplayed();
    } catch (NoSuchElementException e) {
        return false;
    }
    }

   

    public void clickLetsShop() {
        driver.findElement(letsShopBtn).click();
    }
}

