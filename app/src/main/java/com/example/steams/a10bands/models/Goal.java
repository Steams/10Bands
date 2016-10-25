package com.example.steams.a10bands.models;

/**
 * Created by steams on 10/17/16.
 */

public class Goal extends Expense{
    public double target;

    public Goal(String name, double target){
        super(name);
        this.target = target;
    }
}
