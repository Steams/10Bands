package com.example.steams.a10bands.components.transactions;

import io.realm.RealmObject;

/**
 * Created by steams on 10/27/16.
 */

public class TransactionState extends RealmObject{
    public String source;
    public double value;
    public String description;
    public String category;
    public String date;

    public TransactionState(){}

    public Transaction toTransaction(){
        return new Transaction(this);
    }
}
