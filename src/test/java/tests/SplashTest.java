package tests;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplashTest extends BasePage {

    @Test
    public void testSplashAppears() {
        AndroidDriver driver = BasePage.getDriver();
        assert driver != null;

        boolean isCI = System.getenv("CI") != null && System.getenv("CI").equalsIgnoreCase("true");
        long timeoutSeconds = isCI ? 90 : 15;

        try {
            // 1️⃣ Чекаємо, поки activity буде SplashActivity
            WebDriverWait activityWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            activityWait.until(d -> driver.currentActivity().contains("SplashActivity"));
            System.out.println("👀 SplashActivity started.");

            // 2️⃣ Чекаємо видимість splash-елемента
            By splashLocator = By.id("com.androidsample.generalstore:id/action_bar_root"); // або справжній SPLASH_ID
            WebDriverWait splashWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            WebElement splash = splashWait.until(ExpectedConditions.visibilityOfElementLocated(splashLocator));

            System.out.println("✅ Splash screen detected: " + splash.isDisplayed());
            assertTrue(splash.isDisplayed(), "Splash screen should appear at least once");

        } catch (TimeoutException e) {
            System.out.println("⚠️ Splash screen not found after " + timeoutSeconds + " seconds");
            System.out.println("🔍 Current activity: " + driver.currentActivity());
            System.out.println("📄 Page source snippet: " + driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())) + "...");
            assertTrue(false, "Splash screen should appear at least once");
        }
    }
}
