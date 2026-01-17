package com.dailyfinance.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostModel {

    private String itemName;
    private int quantity;
    private String amount;
    private String purchaseDate;
    private String month;
    private String remarks;

    public CostModel(String itemName, int quantity, String amount,
                     String purchaseDate, String month, String remarks){

        this.itemName = itemName;
        this.quantity = quantity;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.month = month;
        this.remarks = remarks;

    }

    public  CostModel(){

    }
}
