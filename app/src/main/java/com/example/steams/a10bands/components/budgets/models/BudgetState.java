package com.example.steams.a10bands.components.budgets.models;

import com.example.steams.a10bands.providers.StateFactory;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by steams on 10/27/16.
 */

public class BudgetState extends RealmObject {
    public double refreshValue;
    public String name;
    public double value;

    @Ignore
    private StateFactory stateFactory;

    public BudgetState(){ }

    public Budget toBudget(){
        return new Budget(this);
    }

    public void update(Budget b){
        if(stateFactory == null){
            stateFactory = StateFactory.getInstance();
        }
        stateFactory.realm.beginTransaction();
        this.value = b.value;
        stateFactory.realm.commitTransaction();

    }
}
