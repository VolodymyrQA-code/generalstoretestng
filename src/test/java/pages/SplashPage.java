package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException; // <- правильний TimeoutException
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SplashPage {
    private AndroidDriver driver;

    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean isSplashDisplayed() {
        boolean isCI = System.getenv("CI") != null && System.getenv("CI").equalsIgnoreCase("true");
        long timeout = isCI ? 60 : 15; // seconds

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement splash = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("com.androidsample.generalstore:id/splashscreen"))); // перевір ресурс-id
            System.out.println("Splash screen detected!");
            return splash.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Splash screen not found after " + timeout + " seconds");
            return false;
        }
    }
}
