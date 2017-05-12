package com.gwolde.assetmanager.modules;

import com.gwolde.assetmanager.enums.Categories;
import com.gwolde.assetmanager.utils.AssetManagerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by gwolde11 on 5/10/17.
 */
public class ProfileLables {

    private WebElement bonds;
    private WebElement stocks;
    private WebElement etfs;
    private WebElement realState;
    private WebElement cash;


    public ProfileLables() {

        bonds = AssetManagerUtil.findElement(By.xpath("//span[@data-test-id='bonds']/span[2]"));
        stocks = AssetManagerUtil.findElement(By.xpath("//span[@data-test-id='stocks']/span[2]"));
        etfs = AssetManagerUtil.findElement(By.xpath("//span[@data-test-id='etfs']/span[2]"));
        realState = AssetManagerUtil.findElement(By.xpath("//span[@data-test-id='realstate']/span[2]"));
        cash = AssetManagerUtil.findElement(By.xpath("//span[@data-test-id='cash']/span[2]"));
    }

    /**
     * Get portfolio value for Category
     * @param cat the category
     * @return the portfolio value
     */

    public int getLabelValues(Categories cat) {
        switch (cat) {
            case BONDS:
                return Integer.parseInt(bonds.getText().trim());
            case STOCKS:
                return Integer.parseInt(stocks.getText().trim());
            case ETFS:
                return Integer.parseInt(etfs.getText().trim());
            case REALSTATE:
                return Integer.parseInt(realState.getText().trim());
            case CASH:
                return Integer.parseInt(cash.getText().trim());
            default:
                return 0;

        }
    }

}
