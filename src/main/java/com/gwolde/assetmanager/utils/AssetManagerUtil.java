package com.gwolde.assetmanager.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class AssetManagerUtil {

    private static AssetManagerUtil assetManagerUtil;
    private  static WebDriver driver = null;


    private AssetManagerUtil(WebDriver driver){
       this.driver = driver;
    }


    public  static AssetManagerUtil getInstance(WebDriver driver){

        if(assetManagerUtil == null )
            assetManagerUtil = new AssetManagerUtil(driver);
        return assetManagerUtil;
    }


    public static List<WebElement> findElements(By by){

        return driver.findElements(by);
    }



    public static WebElement findElement(By by){

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

        int minVal = Integer.valueOf(el.getAttribute("min"));
        int maxVal = Integer.valueOf(el.getAttribute("max"));
        int v = Math.max(minVal, maxVal);

        int width = el.getSize().width;
        int target = width * v;
        Actions action = new Actions(driver);
        action.moveToElement(el, target, 1);
        action.click();
        action.perform();
    }

}
