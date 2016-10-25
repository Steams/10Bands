package com.example.steams.a10bands.components.bills.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.steams.a10bands.BR;
import com.example.steams.a10bands.components.bills.models.Bill;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/23/16.
 */

public class BillsListItemViewModel extends BaseObservable {
    public String name;
    public String value;

    public BillsListItemViewModel(Bill bill){
        name = bill.name;
        value = CurrencyService.makeString(bill.value);
    }

    public void setModel(Bill bill){
        name = bill.name;
        value = CurrencyService.makeString(bill.value);
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

    @Bindable
    public String getName(){
        return this.name;
    }

    @Bindable
    public String getValue(){
        return this.value;
    }

}
