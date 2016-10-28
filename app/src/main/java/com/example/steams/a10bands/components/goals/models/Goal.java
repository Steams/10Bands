package com.example.steams.a10bands.components.goals.models;

import com.example.steams.a10bands.models.Expense;

/**
 * Created by steams on 10/17/16.
 */

public class Goal extends Expense {
    public double target;
    private GoalState state;

    public Goal(String name, double target){
        super(name);
        this.target = target;
    }

    public Goal(GoalState state ){
        super(state.name,state.value);
        this.state = state;
        this.target = state.target;
    }

    public void persistState(){
        state.update(this);
    }
}
