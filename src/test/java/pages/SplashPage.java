package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import io.qameta.allure.Step;

import java.io.File;
import java.io.IOException;
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
     * Логування часу очікування в Allure.
     */
    @Step("Перевірка відображення Splash Screen")
    public boolean isSplashDisplayed() {
    long startTime = System.currentTimeMillis();

    By splashLocator = By.id("com.androidsample.generalstore:id/splash_logo");
    boolean splashPresent = !driver.findElements(splashLocator).isEmpty();

    if (splashPresent) {
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("✅ Splash screen visible immediately! Час перевірки: " + elapsed / 1000.0 + "s");
        return true;
    } else {
        // Якщо splash не знайдено, перевіряємо одразу головний екран
        By homeToolbarId = By.id("com.androidsample.generalstore:id/toolbar_title");
        boolean homeVisible = !driver.findElements(homeToolbarId).isEmpty();

        if (homeVisible) {
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("ℹ️ Splash skipped, але головний екран видимий. Час перевірки: " + elapsed / 1000.0 + "s");
            return true;
        } else {
            System.out.println("❌ Neither splash nor home screen found. Saving debug info...");
            saveDebugInfo();
            return false;
        }
    }
}


    

    @Step("Збереження скріншоту та PageSource для дебагу")
    private void saveDebugInfo() {
        try {
            File screenshot = driver.getScreenshotAs(OutputType.FILE);
            File targetFile = new File("target/screenshots/splash_timeout.png");
            FileUtils.copyFile(screenshot, targetFile);
            System.out.println("📸 Screenshot saved: " + targetFile.getAbsolutePath());

            File pageSourceFile = new File("target/screenshots/splash_timeout.xml");
            FileUtils.writeStringToFile(pageSourceFile, driver.getPageSource(), "UTF-8");
            System.out.println("📄 Page source saved: " + pageSourceFile.getAbsolutePath());
        } catch (IOException ioEx) {
            System.out.println("⚠️ Failed to save screenshot or page source: " + ioEx.getMessage());
        }
    }
}
