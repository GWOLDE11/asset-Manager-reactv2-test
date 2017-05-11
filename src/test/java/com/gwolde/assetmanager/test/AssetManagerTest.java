package com.gwolde.assetmanager.test;


import com.gwolde.assetmanager.enums.Categories;
import com.gwolde.assetmanager.modules.Recommendations;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gwolde11 on 4/23/17.
 */
public class AssetManagerTest extends BaseTest {

    /**
     * Test Data Provider
     * @return multidimensional object Array
     */

    @DataProvider(name = "riskProfileExpected")
    public Object[][] createDataForRisk() {

        Map<Integer, int[]> risk = new HashMap();
        //risk.put(risk, {Bonds, Stocks,ETFs,Real Estate,Cash})
        risk.put(1, new int[]{10, 0, 0, 0, 80});
        risk.put(2, new int[]{10, 10, 0, 0, 80});
        risk.put(3, new int[]{10, 10, 10, 0, 70});
        risk.put(4, new int[]{20, 10, 10, 0, 60});
        risk.put(5, new int[]{15, 15, 10, 10, 50});
        risk.put(6, new int[]{15, 15, 5, 5, 60});
        risk.put(7, new int[]{10, 20, 20, 20, 30});
        risk.put(8, new int[]{5, 30, 10, 30, 20});
        risk.put(9, new int[]{0, 40, 5, 35, 20});
        risk.put(10, new int[]{5, 40, 5, 40, 10});
        return new Object[][]{
                {1, risk}, {2, risk}, {3, risk}, {4, risk}, {5, risk},
                {6, risk}, {7, risk}, {8, risk}, {9, risk}, {10, risk}
        };
    }

    @DataProvider(name = "portfolio")
    public static Object[][] portfolio() {

        return new Object[][]{
                {0, 0, 0, 0, 0, 1}, {1000, 0, 0, 0, 0, 2}, {1000, 1000, 0, 0, 0, 3},
                {1000, 1000, 1000, 0, 0, 4}, {1000, 1000, 1000, 1500, 0, 5},
                {1000, 1000, 1000, 1500, 20000, 6}, {-2000, -4000, -5000, 60000, 10000, 7},
                {-0, -0, -0, -0, -0, 8}, {-100, -15000, -1234, -456345654, -34534, 9},
                {0, 0, 0, 0, 10000000, 10}
        };
    }


    @Test(dataProvider = "riskProfileExpected", groups = {"smoke", "android"},
            description = "Asset manager What does your risk profile look like pie chart test", priority = 1)
    public void riskProfileLookLikePieChartTest(int risk, Map<Integer, int[]> expectedValues) throws Exception {
        int index = 0;
        assetManager.setRiskRange(risk);
        for (Categories cat : Categories.values()) {

            int actualPiChartValue = assetManager.piechart.getChartValue(cat);
            int expected = expectedValues.get(Integer.valueOf(risk))[index];
            Assert.assertEquals(actualPiChartValue, expected, String.format("Actual Pie chart %s value %d != %d expected value ",
                    cat.getLable(), actualPiChartValue, expected));
            index++;
        }


    }


    @Test(dataProvider = "riskProfileExpected", groups = {"smoke", "android"},
            description = "Asset manager What does your risk profile look like Labels test", priority = 2)
    public void riskProfileLookLikeLablesTest(int risk, Map<Integer, int[]> expectedValues) throws Exception {
        int index = 0;
        assetManager.setRiskRange(risk);
        for (Categories cat : Categories.values()) {
            int actualLabelValue = assetManager.piechart.getLebelValue(cat);
            int expected = expectedValues.get(Integer.valueOf(risk))[index];
            Assert.assertEquals(actualLabelValue, expected, String.format("Actual Label %s value %d != %d expected value.",
                    cat.getLable(), actualLabelValue, expected));
            index++;
        }


    }


    @Test(dataProvider = "portfolio", groups = {"smoke", "android"},
            description = "Asset manager update portfolio test")
    public void updatePortfolioTest(int[] values) throws Exception {
        int index = values[values.length - 1];

        Recommendations recommendations = assetManager.getRecommendations(Categories.values(), values);
        for (Categories cat : Categories.values()) {
            int expected = ExpectedRecommendations.getExpectedValues(index, cat);
            int actual = recommendations.getRecommendation(cat);
            Assert.assertEquals(actual, expected,
                    String.format("Actual recommendation for  %s  %d != %d ", cat.getLable(), actual, expected));
        }
    }


    /**
     * Utility class to get expected recommendation result
     */

    private static class ExpectedRecommendations {

        private static int[][] recommendations = {
                {}, {85, 15, 10, 10, 50}, {35, 35, 10, 10, 50}, {18, 18, 23, 10, 50},
                {7, 7, 12, 12, 50}, {11, 11, 6, -1, 32}, {18, 22, 18, -17, 33}, {},
                {15, 15, 10, -10, 50}, {15, 15, 10, 10, 50}

        };

        /**
         *
         * @param index the index of the test case
         * @param cat the category
         * @return return the category value
         */
        public static int getExpectedValues(int index, Categories cat) {
            if (recommendations[index - 1].length != 0) {
                switch (cat) {
                    case BONDS:
                        return recommendations[index - 1][0];
                    case STOCKS:
                        return recommendations[index - 1][1];
                    case ETFS:
                        return recommendations[index - 1][2];
                    case REALSTATE:
                        return recommendations[index - 1][3];
                    case CASH:
                        return recommendations[index - 1][4];
                }
            }
            return -1;
        }


    }

}
