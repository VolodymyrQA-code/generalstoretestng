package pages;

import org.openqa.selenium.By;
import io.appium.java_client.android.AndroidDriver;
import utils.CommonUtilsAndroid;

public class ProductsPage {

    private final CommonUtilsAndroid utils;

    private final By ToolbarTitle = By.id("com.androidsample.generalstore:id/toolbar_title");
    private final By BackBtn = By.id("com.androidsample.generalstore:id/appbar_btn_back");
    private final By CartBtn = By.id("com.androidsample.generalstore:id/appbar_btn_cart");
    // TODO: додати локатори для продуктів

    public ProductsPage(AndroidDriver driver) {
        this.utils = new CommonUtilsAndroid(driver);
    }

    public void verifyToolbarTitle() {
        utils.verifyText(ToolbarTitle, "General Store");
    }

    public void clickBack() {
        utils.click(BackBtn);
    }

    public void clickCart() {
        utils.click(CartBtn);
    }

    // TODO: додати методи для вибору продуктів, додавання в кошик тощо
}
