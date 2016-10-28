package com.example.steams.a10bands.components.transactions;


import java.util.Date;

public class Transaction {
    public String source;
    public double value;
    public String description;
    public String category;
    public String date;

    public Transaction(String description, String source, double value, String category ){
        this.source = source;
        this.description = description;
        this.value = value;
        this.category = category;
        date = new Date().toString();
    }

    public Transaction(TransactionState state){
        source = state.source;
        value = state.value;
        description = state.description;
        category = state.category;
        date = state.date;
    }
}
