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
public class PieChart {

    private WebElement riskButton;
    private WebElement riskLabel;
    private WebElement riskSlider;
    private List<WebElement> piechartArcs;
    private Map<String, String> arcs = new HashMap<>();
    private ProfileLables profileLables;

    /**
     * Constructs a Piechart
     */

    public PieChart() {

        riskButton = AssetManagerUtil.findElement(By.xpath("//button[@type='button']"));
        riskLabel = AssetManagerUtil.findElement(By.className("badge"));
        riskSlider = AssetManagerUtil.findElement(By.name("risk"));
        piechartArcs = AssetManagerUtil.findElements(By.className("rd3-piechart-arc"));
        List<WebElement> pieChartLabLes = AssetManagerUtil.findElements(By.className("rd3-piechart-label"));
        List<WebElement> pieChartValues = AssetManagerUtil.findElements(By.className("rd3-piechart-value"));
        profileLables = new ProfileLables();


        for (WebElement element : pieChartLabLes) {
            arcs.put(element.getText().trim(), pieChartValues.get(pieChartLabLes.indexOf(element)).getText());
        }

    }

    /**
     * Get the chart value of specific lable
     *
     * @param labels the label of the chart
     * @return the value of the named chart
     */

    public int getChartValue(Categories labels) {
        String strValue = arcs.get(labels.getLable());
        return Integer.parseInt(strValue.substring(0, strValue.indexOf('%')));
    }

    public int getRiskProfile() {
        return Integer.parseInt(riskLabel.getText().trim());
    }


    public void setRiskProfile(int range) {

        AssetManagerUtil.setRange(riskSlider, range);

    }


    public int getLebelValue(Categories cat) {
        return profileLables.getLebelValue(cat);
    }

}
