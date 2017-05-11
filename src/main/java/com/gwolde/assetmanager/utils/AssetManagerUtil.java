package com.gwolde.assetmanager.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class AssetManagerUtil {

    private static AssetManagerUtil assetManagerUtil;
    private static WebDriver driver = null;


    private AssetManagerUtil(WebDriver driver) {
        this.driver = driver;
    }


    public static AssetManagerUtil getInstance(WebDriver driver) {

        if (assetManagerUtil == null)
            assetManagerUtil = new AssetManagerUtil(driver);
        return assetManagerUtil;
    }


    public static List<WebElement> findElements(By by) {

        return driver.findElements(by);
    }


    public static WebElement findElement(By by) {

        return driver.findElement(by);
    }


    public static void setRange1(WebElement element, int val) {

        int minval = Integer.valueOf(element.getAttribute("min")).intValue();
        int maxval = Integer.valueOf(element.getAttribute("max")).intValue();

        Actions action = new Actions(driver);
        action = action.moveToElement(element);
        action.moveByOffset(element.getSize().getWidth() - val, 0).click().perform();

    }

    public static void setRange(WebElement el, int val) {

        /*
        for i in range(10):
  slider.send_keys(Keys.RIGHT)
         */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:document.getElementsByName('risk')[0].value=0;");

        for (int i = 0; i < val; i++) {
            el.sendKeys(Keys.ARROW_RIGHT);
        }
/*
        int minVal = Integer.valueOf(el.getAttribute("min"));
        int maxVal = Integer.valueOf(el.getAttribute("max"));

        int width = el.getSize().width;
        System.out.println("Width " + width);
        int range = width / (maxVal - minVal);
        System.out.println("Range " + range);
        int target = range * val / 10;
        Actions action = new Actions(driver);
        System.out.println(target);
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:document.getElementsByName('risk')[0].value=0;");
        js.executeScript("arguments[0].value = arguments[1];", el, val);
        action.dragAndDropBy(el, val, 0).build().perform();
        */

    }

    public WebElement expandShadowRoot(WebElement shadowRootElement) {
        WebElement shdowTreeParent = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].shadowRoot", shadowRootElement);
        return shdowTreeParent;
    }

}
