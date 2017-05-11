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
public class Recommendations {

    private List<WebElement> recomendations;

    private Map<String, WebElement> suggestion = new HashMap<>();

    public Recommendations() {

        recomendations = AssetManagerUtil.findElements(By.className("list-group-item"));
    }

    /**
     * Get the Recommendation for specific category
     *
     * @param categories category whic we want get recommendation
     * @return the recommendation for the category or null if there is none
     */

    public int getRecommendation(Categories categories) {
        try {
            recomendations = AssetManagerUtil.findElements(By.className("list-group-item"));
        } catch (Exception e) {
            recomendations = null;
        }
        if (recomendations != null) {
            for (WebElement rec : recomendations) {
                String recommend = rec.getText();
                if (recommend.indexOf(categories.getLable().toLowerCase()) >= 0) {
                    String value = recommend.substring(recommend.indexOf("by") + 2, recommend.indexOf('%')).trim();
                    return Integer.parseInt(value);
                }
            }
        }
        return -1;
    }


    public String toString() {
        String recommendation = "";
        for (WebElement rec : recomendations) {
            recommendation += rec.getText() + "\n";
        }
        return recommendation;
    }

}
