package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SplashPage {
    private AndroidDriver driver;

    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean isSplashDisplayed() {
        boolean splashFound = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 10000) { // 10 сек
            List<WebElement> elements = driver.findElements(By.id("com.androidsample.generalstore:id/splashscreen"));
            if (!elements.isEmpty()) {
                splashFound = true;
                System.out.println("✅ Splash screen detected!");
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return splashFound;
    }
}
