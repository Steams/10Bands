package com.example.steams.a10bands.components.transactions;


public class Transaction {
    public String source;
    public double value;
    public String description;
    public String category;

    public Transaction(String description, String source, double value, String category ){
        this.source = source;
        this.description = description;
        this.value = value;
        this.category = category;
    }

    public Transaction(TransactionState state){
        source = state.source;
        value = state.value;
        description = state.description;
        category = state.category;
    }
}
