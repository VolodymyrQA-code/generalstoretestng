package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

public class SplashPage {
    private AndroidDriver driver;

    private final By splashLogo = By.id("com.androidsample.generalstore:id/splashscreen");

    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Очікує появу splash screen протягом timeoutInSeconds секунд.
     * @param timeoutInSeconds максимальний час очікування
     * @return true, якщо елемент видно, інакше false
     */
    public boolean waitForSplashToAppear(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(splashLogo));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

   public void openSplashPage() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    try {
        WebElement splash = wait.until(ExpectedConditions.visibilityOfElementLocated(splashLogo));
        assertTrue(splash.isDisplayed(), "Splash screen should be visible");
        System.out.println("[TEST] Splash screen видимий: true");
    } catch (Exception e) {
        System.out.println("[TEST] Splash screen не відображається: " + e.getMessage());
        throw e;
    }
}
}
