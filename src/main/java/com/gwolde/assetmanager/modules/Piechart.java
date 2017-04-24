package com.gwolde.assetmanager.modules;

import com.gwolde.assetmanager.enums.Categories;
import com.gwolde.assetmanager.utils.AssetManagerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class Piechart {


    private List<WebElement> piechartArcs;
    private List<WebElement> piechartLables;
    private List<WebElement> piechartValues;
    private Map<String, String> arcs = new HashMap<>();

    /**
     * Constructs a Piechart

     */
    public Piechart() {

            piechartArcs = AssetManagerUtil.findElements(By.className("rd3-piechart-arc"));
            piechartLables = AssetManagerUtil.findElements(By.className("rd3-piechart-label"));
            piechartValues = AssetManagerUtil.findElements(By.className("rd3-piechart-value"));

            for(WebElement element : piechartLables){
                arcs.put(element.getText().trim(), piechartValues.get(piechartLables.indexOf(element)).getText());
            }

    }

    /**
     * Get the chart value of specific lable
     * @param labels the label of the chart
     * @return the value of the named chart
     */

    public String getChartValue(Categories labels ){

        return arcs.get(labels.getLable());
    }

}
