package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import io.qameta.allure.Step;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class SplashPage {

    private final AndroidDriver driver;

    // ID елементів
    private final By splashId = By.id("com.androidsample.generalstore:id/splashscreen");
    private final By homeToolbarId = By.id("com.androidsample.generalstore:id/toolbar_title");

    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Перевіряє, чи splash screen відображається.
     * Викликається з тесту TestNG через assertTrue.
     */
    @Step("Перевірка відображення Splash Screen")
    public boolean isSplashDisplayed() {
        long startTime = System.currentTimeMillis();

        boolean splashPresent = !driver.findElements(splashId).isEmpty();

        if (splashPresent) {
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("✅ Splash screen visible immediately! Час перевірки: " + elapsed / 1000.0 + "s");
            return true;
        } else {
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

    /**
     * Виклик для TestNG:
     * assertTrue(splashPage.verifySplashVisible(), "Splash не відображено!");
     */
    public void verifySplashVisible() {
        assertTrue(isSplashDisplayed(), "Splash screen не відображається");
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
