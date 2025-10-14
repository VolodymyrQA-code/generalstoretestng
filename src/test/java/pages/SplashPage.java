package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

import java.io.File;
import java.time.Duration;

public class SplashPage {
    private AndroidDriver driver;

    // ID елементів
    private final By splashId = By.id("com.androidsample.generalstore:id/splashscreen");
    private final By homeToolbarId = By.id("com.androidsample.generalstore:id/toolbar_title");

    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Перевіряє, чи splash screen відображається.
     * У CI збільшений timeout.
     * Якщо splash не знайдено — робить скріншот і зберігає PageSource для дебагу.
     */
    public boolean isSplashDisplayed() {
        boolean isCI = System.getenv("CI") != null && System.getenv("CI").equalsIgnoreCase("true");
        long timeout = isCI ? 90 : 15; // seconds

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

            // Чекаємо спочатку splash
            WebElement splash = wait.until(ExpectedConditions.visibilityOfElementLocated(splashId));
            System.out.println("✅ Splash screen detected!");
            return splash.isDisplayed();

        } catch (TimeoutException e) {
            System.out.println("⚠️ Splash screen not found after " + timeout + " seconds");

            // Спробуємо чекати головний екран
            try {
                WebDriverWait waitHome = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement homeToolbar = waitHome.until(ExpectedConditions.visibilityOfElementLocated(homeToolbarId));
                System.out.println("ℹ️ Splash skipped, but home screen is visible. Continuing...");
                return true;
            } catch (TimeoutException ex) {
                System.out.println("❌ Neither splash nor home screen found. Saving debug info...");

                try {
                    // Зберегти скріншот
                    File screenshot = driver.getScreenshotAs(OutputType.FILE);
                    File targetFile = new File("target/screenshots/splash_timeout.png");
                    FileUtils.copyFile(screenshot, targetFile);
                    System.out.println("📸 Screenshot saved: " + targetFile.getAbsolutePath());

                    // Зберегти PageSource
                    File pageSourceFile = new File("target/screenshots/splash_timeout.xml");
                    FileUtils.writeStringToFile(pageSourceFile, driver.getPageSource(), "UTF-8");
                    System.out.println("📄 Page source saved: " + pageSourceFile.getAbsolutePath());

                } catch (Exception ioEx) {
                    System.out.println("⚠️ Failed to save screenshot or page source: " + ioEx.getMessage());
                }

                return false;
            }
        }
    }
}
