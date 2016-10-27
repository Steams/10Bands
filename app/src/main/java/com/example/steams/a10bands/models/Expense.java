package com.example.steams.a10bands.models;

/**
 * Created by steams on 10/17/16.
 */

public class Expense {
    public String name;
    public double value;

    public Expense(String name){
        this.name = name;
        this.value = 0;
    }

    public Expense(String name, double value){
        this.name = name;
        this.value = value;
    }

    public boolean withdraw(double amount){
        if(value < amount){
            return false;
        }
        value -= amount;
        return true;
    }

    public void deposit(double amount){
        value += amount;
    }
}
