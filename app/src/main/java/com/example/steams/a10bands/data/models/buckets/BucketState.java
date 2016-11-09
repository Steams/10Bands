package com.example.steams.a10bands.data.models.buckets;

import com.example.steams.a10bands.providers.StateFactory;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by steams on 10/27/16.
 */

public class BucketState extends RealmObject {
    public String name;
    public double value;

    public Bucket toBucket(){
        return new Bucket(this);
    }

    @Ignore
    private StateFactory stateFactory;


    public void update(Bucket b){
        if(stateFactory == null){
            stateFactory = StateFactory.getInstance();
        }
        stateFactory.realm.beginTransaction();
            this.value = b.value;
        stateFactory.realm.commitTransaction();

    }
}
