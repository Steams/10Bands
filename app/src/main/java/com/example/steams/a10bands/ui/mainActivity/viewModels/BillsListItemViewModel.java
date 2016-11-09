package com.example.steams.a10bands.ui.mainActivity.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.steams.a10bands.BR;
import com.example.steams.a10bands.data.models.bills.Bill;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/23/16.
 */

public class BillsListItemViewModel extends BaseObservable {
    public String name;
    public String value;
    public String refreshValue;

    public BillsListItemViewModel(Bill bill) {
        name = bill.name;
        setValue(bill.value);
        refreshValue = CurrencyService.makeString(bill.refreshValue);
    }

    public void setModel(Bill bill) {
        name = bill.name;
        setValue(bill.value);
        refreshValue = CurrencyService.makeString(bill.refreshValue);
        notifyPropertyChanged(BR._all);
    }

    public void setValue(double value) {
        if(value > 0){
            this.value = CurrencyService.makeString(value);
        } else {
            this.value = "paid";
        }
        notifyPropertyChanged(BR.value);
    }

    public void setrefreshValue(double value) {
        this.refreshValue = CurrencyService.makeString(value);
        notifyPropertyChanged(BR.refreshValue);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }


    @Bindable
    public String getName() {
        return this.name;
    }

    @Bindable
    public String getValue() {
        return this.value;
    }

    @Bindable
    public String getRefreshValue() {
        return this.refreshValue;
    }
}
