package com.gwolde.assetmanager.test;


import com.gwolde.assetmanager.enums.Categories;
import com.gwolde.assetmanager.modules.Recommendations;
import org.testng.annotations.*;
import org.testng.Assert;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class AssetManagerTest extends BaseTest {

    /**
     * Test Data Provider
     * @return multidimensional object Array
     */
    @DataProvider(name = "portfolio")
    public static Object[][] portfolio() {
        return new Object[][] {{100, 1000, 1500,140,500}, {20, 30000, 500,4000,50000},
                {200000, 300000, 500000,4000000,5000000}, {2000000, 300000, 500000,4000000,5000000},
                {-2000, 3000, 5000,40000,50000}, {20000, -30000, 50000,400000,50000},
                {200000, 300000, -500000,4000000,5000000}, {2000000, 300000, 500000,-4000000,-5000000}};
    }

    @Test(dataProvider = "portfolio", groups = {"smoke"},
   description="Asset manager update portfolio test")
    public void updatePortfolioTest(int[] values) throws Exception {
       // assetManager.setRiskRange(10);
       Recommendations recommendations =  assetManager.getRecommendations(Categories.values(), values);
       for(Categories cat : Categories.values()){
           Assert.assertNotNull(recommendations.getRecommendation(cat), "Recommendation for " + cat.getLable() + " is null");
       }
    }

}
