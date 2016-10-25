package com.example.steams.a10bands.components.buckets.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.steams.a10bands.BR;
import com.example.steams.a10bands.components.buckets.Bucket;
import com.example.steams.a10bands.utils.CurrencyService;


/**
 * Created by steams on 10/21/16.
 */

public class BucketListItemViewModel extends BaseObservable{
    public String name;
    public String value;

    public BucketListItemViewModel(Bucket budget){
        name = budget.name;
        value = CurrencyService.makeString(budget.value);
    }

    public void setModel(Bucket budget){
        name = budget.name;
        value = CurrencyService.makeString(budget.value);
        notifyPropertyChanged(BR._all);
    }

    public void setValue(double value){
        this.value = CurrencyService.makeString(value);
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public String getName(){
        return this.name;
    }

    @Bindable
    public String getValue(){
        return this.value;
    }
}
