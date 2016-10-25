package com.example.steams.a10bands.components.budgets.models;

import com.example.steams.a10bands.models.Expense;

/**
 * Created by steams on 10/17/16.
 */

public class Budget extends Expense {
    public double refreshValue;

    public Budget(String name, double refreshValue){
        super(name);
        this.refreshValue = refreshValue;
    }

    public double refresh(double fundsRemaining){
        double topUpAmount = refreshValue - value;
        this.value += topUpAmount;
        return fundsRemaining - topUpAmount;
    }
}
