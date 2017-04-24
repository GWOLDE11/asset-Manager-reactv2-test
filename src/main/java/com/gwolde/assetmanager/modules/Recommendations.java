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

    public Recommendations(){

        recomendations = AssetManagerUtil.findElements(By.className("list-group-item"));
    }

    /**
     * Get the Recommendation for specific category
     * @param categories category whic we want get recommendation
     * @return the recommendation for the category or null if there is none
     */

    public String getRecommendation(Categories categories){

        for(WebElement rec : recomendations){
            String recommendation = rec.getText();
            if(recommendation.indexOf(categories.getLable().toLowerCase()) >= 0) {
                return recommendation;
            }
        }
        return null;
    }


    public String toString(){
        String recommendation = "";
        for(WebElement rec : recomendations){
            recommendation += rec.getText() + "\n";
        }
        return recommendation;
    }

}
