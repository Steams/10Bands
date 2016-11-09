package com.example.steams.a10bands.data.models.bills;

import com.example.steams.a10bands.data.models.transactions.Transaction;
import com.example.steams.a10bands.data.models.Expense;

/**
 * Created by steams on 10/17/16.
 */

public class Bill extends Expense {
    public boolean isPaid;
    public double refreshValue;

    BillState state;

    public Bill(String name, double refreshValue ){
        super(name,refreshValue);
        this.refreshValue = refreshValue;
        isPaid = false;
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

    public Transaction pay(){
        this.isPaid = true;
        Transaction t =  new Transaction("Bill Payment",name,value,"");
        this.value = 0;

        return t;
    }
}
