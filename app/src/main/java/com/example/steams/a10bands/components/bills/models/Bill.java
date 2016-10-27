package com.example.steams.a10bands.components.bills.models;

import com.example.steams.a10bands.models.Expense;

import io.realm.RealmObject;

/**
 * Created by steams on 10/17/16.
 */

public class Bill extends Expense {
    public boolean isPaid;
    public double refreshValue;

    BillState state;

    public Bill(String name, double refreshValue ){
        super(name);
        this.refreshValue = refreshValue;
    }

    public Bill(BillState state){
        super(state.name, state.value);
        isPaid = state.isPaid;
        refreshValue = state.refreshValue;

        this.state = state;
    }

    public double refresh(double fundsRemaining){
        this.value += refreshValue;
        return fundsRemaining - value;
    }

    public void persistState(){
        state.update(this);
    }

}
