package com.gwolde.assetmanager.page;

import com.gwolde.assetmanager.enums.Categories;
import com.gwolde.assetmanager.modules.PieChart;
import com.gwolde.assetmanager.modules.Portfolio;
import com.gwolde.assetmanager.modules.Recommendations;
import com.gwolde.assetmanager.utils.AssetManagerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class AssetManager {

    private WebElement range;

    public PieChart piechart;

    public Portfolio portfolio;

    public Recommendations recommendations;

    public AssetManager() {

        range = AssetManagerUtil.findElement(By.name("risk"));

        piechart = new PieChart();
        portfolio = new Portfolio();
    }

    /**
     * Get Recommendations
     *
     * @param categories Asset categories (Bond, Stocks ...)
     * @param values     categories values
     * @return List of recommendations
     */
    public Recommendations getRecommendations(Categories[] categories, int[] values) {
        for (int i = 0; i < categories.length; i++) {
            portfolio.setCategory(categories[i], values[i]);
        }
        recommendations = portfolio.submit();
        return recommendations;
    }

    /**
     * Set slider range value
     * @param value
     */
    public void setRiskRange(int value) {
        AssetManagerUtil.setRange(range, value);
        piechart = new PieChart();
    }


}
