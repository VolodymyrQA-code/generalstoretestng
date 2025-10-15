package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * SplashPage — замінює очікування UI-елемента на аналіз logcat.
 * Під час запуску на CI це значно стабільніше.
 */
public class SplashPage {

    private final AndroidDriver driver;

    public SplashPage(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Очікує появу запису про запуск SplashActivity у logcat.
     * Якщо не знайдено — тест вважається невдалим.
     */
    public boolean waitForSplashInLogs() {
        System.out.println("🔍 Waiting for SplashActivity in logcat...");

        long start = System.currentTimeMillis();
        boolean splashFound = false;

        for (int i = 0; i < 30; i++) {
            String logs = driver.manage().logs().get("logcat").getAll().toString();

            if (logs.contains("com.androidsample.generalstore/.SplashActivity")) {
                System.out.println("✅ SplashActivity detected in logs after " +
                        (System.currentTimeMillis() - start) / 1000 + "s");
                splashFound = true;
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }

        if (!splashFound) {
            System.out.println("⚠️ SplashActivity not found in logs after 60s");
        }

        return splashFound;
    }

    /**
     * Очікує, поки в логах з'явиться головна активність (наприклад, MainActivity).
     */
    public boolean waitForMainActivityInLogs() {
        System.out.println("🔍 Waiting for MainActivity in logcat...");

        long start = System.currentTimeMillis();
        boolean mainFound = false;

        for (int i = 0; i < 40; i++) {
            String logs = driver.manage().logs().get("logcat").getAll().toString();

            if (logs.contains("com.androidsample.generalstore/.MainActivity") ||
                logs.contains("Displayed com.androidsample.generalstore/.MainActivity")) {
                System.out.println("✅ MainActivity detected in logs after " +
                        (System.currentTimeMillis() - start) / 1000 + "s");
                mainFound = true;
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }

        if (!mainFound) {
            System.out.println("⚠️ MainActivity not found in logs after 80s");
        }

        return mainFound;
    }
}
