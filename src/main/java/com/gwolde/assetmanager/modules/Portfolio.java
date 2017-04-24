package com.gwolde.assetmanager.modules;

import com.gwolde.assetmanager.enums.Categories;
import com.gwolde.assetmanager.utils.AssetManagerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class Portfolio {

    private List<WebElement> categories;
    private WebElement submit;
    private Map<String, WebElement> inputs = new HashMap<>();
    private WebDriver driver;

    public Portfolio(){
        categories = AssetManagerUtil.findElements(By.xpath("//input[@type='text']"));
        submit = AssetManagerUtil.findElement(By.xpath("//input[@type='submit']"));

        for(WebElement input : categories){
            inputs.put(input.getAttribute("placeholder"), input);
        }
    }

    public void setCategory(Categories label, int value){
        inputs.get(label.getLable()).clear();
        inputs.get(label.getLable()).sendKeys(String.valueOf(value));

    }


    public Recommendations submit(){
        submit.click();
        return new Recommendations();
    }
}
