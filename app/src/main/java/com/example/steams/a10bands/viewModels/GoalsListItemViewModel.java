package com.example.steams.a10bands.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.steams.a10bands.BR;
import com.example.steams.a10bands.models.Goal;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/23/16.
 */

public class GoalsListItemViewModel extends BaseObservable {
    public String name;
    public String value;
    public String target;

    public GoalsListItemViewModel(Goal goal) {
        name = goal.name;
        value = CurrencyService.makeString(goal.value);
        target = CurrencyService.makeString(goal.target);
    }

    public void setModel(Goal goal){
        name = goal.name;
        value = CurrencyService.makeString(goal.value);
        target = CurrencyService.makeString(goal.target);
        notifyPropertyChanged(BR._all);
    }

    public void setValue(double value){
        this.value = CurrencyService.makeString(value);
        notifyPropertyChanged(BR.value);
    }

    public void setName(String name){
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setTarget(String target){
        this.target = target;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getName(){
        return this.name;
    }

    @Bindable
    public String getValue(){
        return this.value;
    }

    @Bindable
    public String getTarget(){
        return this.target;
    }

}
