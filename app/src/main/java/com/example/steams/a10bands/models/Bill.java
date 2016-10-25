package com.example.steams.a10bands.models;

/**
 * Created by steams on 10/17/16.
 */

public class Bill extends Expense{
    public boolean isPaid;
    public double refreshValue;

    public Bill(String name, double refreshValue ){
        super(name);
        this.refreshValue = refreshValue;
    }

    public double refresh(double fundsRemaining){
        this.value += refreshValue;
        return fundsRemaining - value;
    }
}
