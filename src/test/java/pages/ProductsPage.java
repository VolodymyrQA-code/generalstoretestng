package pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import utils.CommonUtils;

public class ProductsPage {
    private final CommonUtils utils = null;


    private final By ToolbarTitle = By.id("com.androidsample.generalstore:id/toolbar_title");
    


    public void verifyToolbarTitle() {
    utils.verifyText(ToolbarTitle, "General Store");
    }
}
