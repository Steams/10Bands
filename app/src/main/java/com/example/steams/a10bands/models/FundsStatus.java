package com.example.steams.a10bands.models;

import android.databinding.Bindable;

/**
 * Created by steams on 10/22/16.
 */

public class FundsStatus {

    private double balance = 0;
    private double savings = 0;
    private double available = 0;
    private double free = 0;

    public FundsStatus(){

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) { this.balance = balance; }

    public double getSavings() { return savings; }

    public void setSavings(double savings) { this.savings = savings; }

    public double getAvailable() { return available; }

    public void setAvailable(double available) { this.available = available; }

    public double getFree() { return free; }

    public void setFree(double free) { this.free = free; }
}
