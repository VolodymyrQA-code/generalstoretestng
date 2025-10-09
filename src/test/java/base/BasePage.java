package base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.URI;
import java.time.Duration;



public class BasePage {
    protected static AndroidDriver driver;
    protected static WebDriverWait wait;
    

public boolean isTextEqual(By locator, String expectedText) {
    try {
        String actualText = driver.findElement(locator).getText().trim();
        return actualText.equals(expectedText.trim());
    } catch (NoSuchElementException e) {
        return false;
    }
}

@BeforeAll
static void setup() {
    try {
       UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setApp(System.getProperty("user.dir") + "/General-Store.apk")
                .setAppPackage("com.androidsample.generalstore")
                .setAppActivity("com.androidsample.generalstore.SplashActivity")
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(60));
                options.setCapability("appium:ignoreHiddenApiPolicyError", true);
                options.setCapability("adbExecTimeout", 60000);

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}

   

    @AfterAll

static void tearDown() {

    if (driver != null) {
        try {
            driver.removeApp("com.androidsample.generalstore"); 
        } catch (Exception e) {
            System.out.println("Не вдалося видалити додаток: " + e.getMessage());
        } finally {
            driver.quit(); 
        }
    }
}
}
