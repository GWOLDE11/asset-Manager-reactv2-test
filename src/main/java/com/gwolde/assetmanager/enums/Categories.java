package com.gwolde.assetmanager.enums;

/**
 * Created by gwolde11 on 4/23/17.
 */
public enum Categories {

    BONDS("Bonds"), STOCKS("Stocks"), ETFS("ETFs"), REALSTATE("Real Estate"), CASH("Cash");

    private String label;

    Categories(String lable) {
        this.label = lable;
    }

    public String getLable() {
        return this.label;
    }
}
