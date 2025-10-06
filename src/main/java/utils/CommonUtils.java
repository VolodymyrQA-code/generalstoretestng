package utils;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.interactions.Sequence;

public class CommonUtils {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CommonUtils(WebDriver driver2) {
        this.driver = (AndroidDriver) driver2;
        this.wait = new WebDriverWait(driver2, Duration.ofSeconds(10));
    }

    // Очікування елемента
    public WebElement waitForElement(By locator) {
    try {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    } catch (TimeoutException e) {
        System.out.println("[WARN] Елемент не став видимим за відведений час: " + locator);
        return null;
    } catch (Exception e) {
        System.out.println("[ERROR] Не вдалося знайти елемент: " + locator + " -> " + e.getMessage());
        return null;
    }
    }
    public boolean isElementDisplayed(By locator) {
    WebElement element = waitForElement(locator);
    if (element != null) {
        boolean displayed = element.isDisplayed();
        System.out.println("[INFO] Елемент " + locator + " відображається: " + displayed);
        return displayed;
    } else {
        System.out.println("[INFO] Елемент " + locator + " не знайдено або не відображається");
        return false;
    }
    }

    // Клік по елементу
    public void click(By locator) {
        try {
            WebElement el = waitForElement(locator);
            if (el != null) {
                el.click();
                System.out.println("[INFO] Клік по елементу: " + locator);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Не вдалося клікнути по елементу: " + locator + " -> " + e.getMessage());
        }
    }

    // Клік в стороні від елемента (щоб закрити попап)
    public void clickOutside(By popupLocator) {
    try {
        WebElement popup = waitForElement(popupLocator);
        if (popup != null) {
            // Беремо координати елемента
            int x = popup.getLocation().getX();
            int y = popup.getLocation().getY();

            // Точка поза елементом (наприклад, ліворуч і трохи вище)
            int clickX = x - 10;
            int clickY = y - 10;

            // Створюємо PointerInput для тапу
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), clickX, clickY));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Виконуємо жест
            driver.perform(Collections.singletonList(tap));

            System.out.println("[INFO] Виконано клік в стороні від елемента: " + popupLocator);
        }
    } catch (Exception e) {
        System.out.println("[ERROR] Не вдалося виконати клік в стороні від елемента: " + popupLocator + " -> " + e.getMessage());
    }
    }


   // Перевірка, чи елемент вибраний відповідно до очікуваного стану
    public void verifySelectedState(By locator, boolean expectedSelected) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement el = wait.until(driver -> {
            WebElement element = driver.findElement(locator);
            if (element.isSelected() == expectedSelected) {
                return element;
            } else {
                return null; // продовжує чекати
            }
        });
        boolean actualSelected = el.isSelected();
        if (actualSelected == expectedSelected) {
            System.out.println("[PASS] Елемент " + locator + " має очікуваний стан selected=" + expectedSelected);
        } else {
            System.out.println("[FAIL] Елемент " + locator + " має фактичний стан selected=" + actualSelected + ", очікувався " + expectedSelected);
            throw new AssertionError("Selected state mismatch for locator: " + locator);
        }
    } catch (Exception e) {
        System.out.println("[ERROR] Не вдалося перевірити стан елемента: " + locator + " -> " + e.getMessage());
        throw new AssertionError("Cannot verify selected state for locator: " + locator);
    }
}

    // Введення тексту
    public void type(By locator, String text) {
        try {
            WebElement el = waitForElement(locator);
            if (el != null) {
                el.clear();
                el.sendKeys(text);
                System.out.println("[INFO] Введено текст '" + text + "' у поле: " + locator);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Не вдалося ввести текст у поле: " + locator + " -> " + e.getMessage());
        }
    }

    // Закриття клавіатури
    public void closeKeyboard() {
    try {
        driver.hideKeyboard();
        System.out.println("[UTILS] Клавіатура схована");
    } catch (Exception e) {
        System.out.println("[UTILS][WARN] Клавіатуру не вдалося сховати: " + e.getMessage());
    }
}
    
    // Допоміжний метод для нормалізації тексту
    private String normalizeText(String text) {
    if (text == null) return null;
    // прибираємо пробіли на початку/кінці та замінюємо всі послідовності пробілів одним пробілом
    return text.trim().replaceAll("\\s+", " ");
}

    // Отримати текст
    public String getText(By locator) {
    try {
        WebElement el = waitForElement(locator);
        if (el != null) {
            String text = el.getText();
            System.out.println("[INFO] Отримано текст з елемента " + locator + ": " + text);
            return normalizeText(text); // нормалізований текст
        }
    } catch (Exception e) {
        System.out.println("[ERROR] Не вдалося отримати текст з елемента: " + locator + " -> " + e.getMessage());
    }
    return null;
}

    // Перевірка тексту елемента
    public void verifyText(By locator, String expectedText) {
    String actualText = getText(locator);
    if (actualText != null && expectedText != null && normalizeText(expectedText).equals(actualText)) {
        System.out.println("[PASS] Текст елемента відповідає очікуваному: " + actualText);
    } else {
        System.out.println("[FAIL] Очікувався текст '" + expectedText + "', але отримано: " + actualText);
        throw new AssertionError("Text mismatch for locator: " + locator);
    }
}

// Скролить до тексту і клікає по ньому
public void scrollToTextAndClick(String visibleText) {
    try {
        driver.findElement(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().text(\"" + visibleText + "\"))"
        )).click();
        System.out.println("[INFO] Знайдено і клікнуто по елементу з текстом: " + visibleText);
    } catch (Exception e) {
        System.out.println("[ERROR] Не вдалося знайти елемент з текстом: " + visibleText + " -> " + e.getMessage());
        throw e;
    }
}


}


