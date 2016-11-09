package com.example.steams.a10bands.data.models.buckets;

import com.example.steams.a10bands.data.models.transactions.Transaction;
import com.example.steams.a10bands.data.models.Expense;

/**
 * Created by steams on 10/17/16.
 */

public class Bucket extends Expense {
    private BucketState state;

    public Bucket(String name){
        super(name);
    }

    public boolean fulfillTransaction(Transaction t){
        if(this.value > t.value){
            this.value -= t.value;
            return true;
        }
        return false;
    }

    public Bucket(BucketState state){
        super(state.name,state.value);
        this.state = state;
    }

    public void persistState(){
        state.update(this);
    }
}
