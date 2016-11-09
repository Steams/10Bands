package com.example.steams.a10bands.data.models.budgets;

import com.example.steams.a10bands.data.models.transactions.Transaction;
import com.example.steams.a10bands.data.models.Expense;

/**
 * Created by steams on 10/17/16.
 */
public class Budget extends Expense {
    public BudgetState state;
    public double refreshValue;

    public Budget(String name, double refreshValue) {
        super(name);
        this.refreshValue = refreshValue;
    }

    public Budget(BudgetState state){
        super(state.name,state.value);
        refreshValue = state.refreshValue;
        this.state = state;
    }

    public double refresh(double fundsRemaining){
        double topUpAmount = refreshValue - value;
        this.value += topUpAmount;
        return fundsRemaining - topUpAmount;
    }

    public boolean fulfillTransaction(Transaction t){
        if(this.value > t.value){
            this.value -= t.value;
            return true;
        }
        return false;
    }

    public void persistState(){
        state.update(this);
    }
}
