package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SplashPage {
    private AndroidDriver driver;

    // Ресурс-id splash-екрану, перевір через Appium Inspector
    private static final String SPLASH_ID = "com.androidsample.generalstore:id/splashscreen";


    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Перевіряє, чи відображається splash-екран.
     * Для CI таймаут збільшується, для локального запуску менший.
     *
     * @return true, якщо splash-екран видно, false інакше
     */
    public boolean isSplashDisplayed() {
    boolean isCI = System.getenv("CI") != null && System.getenv("CI").equalsIgnoreCase("true");
    long timeoutSeconds = isCI ? 90 : 15;

    try {
        // Чекаємо на старт SplashActivity
        WebDriverWait activityWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        activityWait.until(d -> driver.currentActivity().contains("SplashActivity"));

        // Чекаємо появу splash елемента
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement splash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SPLASH_ID)));

        System.out.println("✅ Splash screen detected!");
        return splash.isDisplayed();

    } catch (TimeoutException e) {
        System.out.println("⚠️ Splash screen not found after " + timeoutSeconds + " seconds");
        System.out.println("🔍 Current activity: " + driver.currentActivity());
        System.out.println("📄 Page source snippet: " + driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())) + "...");
        return false;
    } catch (Exception e) {
        System.out.println("❌ Unexpected error while checking splash: " + e.getMessage());
        return false;
    }
}

}
