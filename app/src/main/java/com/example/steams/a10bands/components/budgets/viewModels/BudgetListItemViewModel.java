package com.example.steams.a10bands.components.budgets.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.steams.a10bands.BR;
import com.example.steams.a10bands.components.budgets.models.Budget;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/21/16.
 */

public class BudgetListItemViewModel extends BaseObservable{
    public String name;
    public String value;
    public String refreshValue;

    public BudgetListItemViewModel(Budget budget){
        name = budget.name;
        value = CurrencyService.makeString(budget.value);
        refreshValue = CurrencyService.makeString(budget.refreshValue);
    }

    public void setModel(Budget budget){
        name = budget.name;
        value = CurrencyService.makeString(budget.value);
        refreshValue = CurrencyService.makeString(budget.refreshValue);
        notifyPropertyChanged(BR._all);
    }

    public void setValue(double value){
        this.value = CurrencyService.makeString(value);
        notifyPropertyChanged(BR.value);
    }

    public void setRefreshValue(double refreshValue){
        this.refreshValue = CurrencyService.makeString(refreshValue);
        notifyPropertyChanged(BR.refreshValue);
    }

    public void setName(String name){
        this.name = name;
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
    public String getRefreshValue(){
        return this.refreshValue;
    }

}
