package com.example.steams.a10bands.data.models.bills;

import com.example.steams.a10bands.providers.StateFactory;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by steams on 10/27/16.
 */

public class BillState extends RealmObject {
    public boolean isPaid;
    public double refreshValue;
    public String name;
    public double value;

    public Bill toBill(){
        return new Bill(this);
    }

    @Ignore
    private StateFactory stateFactory;


    public void update(Bill b){
        if(stateFactory == null){
            stateFactory = StateFactory.getInstance();
        }
        stateFactory.realm.beginTransaction();
            this.value = b.value;
            this.isPaid = b.isPaid;
        stateFactory.realm.commitTransaction();

    }
}
