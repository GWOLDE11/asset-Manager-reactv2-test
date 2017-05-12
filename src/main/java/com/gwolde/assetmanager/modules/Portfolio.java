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

    public Portfolio() {

        categories = AssetManagerUtil.findElements(By.xpath("//input[@type='text']"));
        submit = AssetManagerUtil.findElement(By.xpath("//input[@type='submit']"));

        for (WebElement input : categories) {
            inputs.put(input.getAttribute("placeholder").toLowerCase(), input);
        }
    }

    /**
     * Set the portfolio text field value
     * @param label the portfolio category
     * @param value the value to set the category text field
     */
    public void setCategory(Categories label, int value) {
        inputs.get(label.getLable().toLowerCase()).clear();
        inputs.get(label.getLable().toLowerCase()).sendKeys(String.valueOf(value));

    }

    /**
     * Set the portfolios  text fields values
     * @param labels array of portfolio labels
     * @param values array portfolio of values
     */
    public void setCategories(Categories[] labels, int[] values) {
        int i = 0;
        for(Categories c : labels){
            setCategory(c, values[i]);
            i++;
        }
    }

    /**
     * Submit current portfolio
     * @return Recommendation
     */
    public Recommendations submit() {
        submit.click();
        return new Recommendations();
    }
}
