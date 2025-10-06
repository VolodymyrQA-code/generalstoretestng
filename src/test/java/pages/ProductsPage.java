package pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import utils.CommonUtils;

public class ProductsPage {
    private final CommonUtils utils = null;


    private final By ToolbarTitle = By.id("com.androidsample.generalstore:id/toolbar_title");
    private final By BackBtn = By.id("com.androidsample.generalstore:id/appbar_btn_back");
    private final By CartBtn = By.id("com.androidsample.generalstore:id/appbar_btn_cart");
    // Plese add some product at first


    


    public void verifyToolbarTitle() {
    utils.verifyText(ToolbarTitle, "General Store");
    }
}
