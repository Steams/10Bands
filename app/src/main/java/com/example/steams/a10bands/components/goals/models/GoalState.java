package com.example.steams.a10bands.components.goals.models;

import com.example.steams.a10bands.providers.StateFactory;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by steams on 10/27/16.
 */

public class GoalState extends RealmObject {
    public String name;
    public double value;

    public Goal toGoal(){
        return new Goal(this);
    }

    @Ignore
    private StateFactory stateFactory;


    public void update(Goal b){
        if(stateFactory == null){
            stateFactory = StateFactory.getInstance();
        }
        stateFactory.realm.beginTransaction();
            this.value = b.value;
        stateFactory.realm.commitTransaction();

    }
}
