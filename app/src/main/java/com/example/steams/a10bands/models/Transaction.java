package com.example.steams.a10bands.models;


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
}
