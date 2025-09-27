package pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SplashPage {
    private AndroidDriver driver;

    private final By splashLogo = By.id("com.androidsample.generalstore:id/splashscreen");

public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean isDisplayed() {
        return driver.findElement(splashLogo).isDisplayed();
    }
}



