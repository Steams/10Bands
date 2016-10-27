package com.example.steams.a10bands.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.steams.a10bands.BR;
import com.example.steams.a10bands.models.FundsStatus;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/18/16.
 */

public class FundsStatusViewModel extends BaseObservable {
    private String balance = "0";
    private String savings = "0";
    private String available = "0";
    private String free = "0";

    public FundsStatusViewModel(){

    }

    public FundsStatusViewModel(FundsStatus fundsStatus){
        setBalance(fundsStatus.getBalance());
        setSavings(fundsStatus.getSavings());
        setAvailable(fundsStatus.getAvailable());
        setFree(fundsStatus.getFree());
    }

    public void setModel(FundsStatus fundsStatus){
        setBalance(fundsStatus.getBalance());
        setSavings(fundsStatus.getSavings());
        setAvailable(fundsStatus.getAvailable());
        setFree(fundsStatus.getFree());

    }

    @Bindable
    public String getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = CurrencyService.makeString(balance);
        notifyPropertyChanged(BR.balance);
    }

    @Bindable
    public String getSavings() { return savings; }

    public void setSavings(double savings) {
        this.savings = CurrencyService.makeString(savings);
        notifyPropertyChanged(BR.savings);
    }

    @Bindable
    public String getAvailable() { return available; }

    public void setAvailable(double available) {
        this.available = CurrencyService.makeString(available);
        notifyPropertyChanged(BR.available);
    }

    @Bindable
    public String getFree() { return free; }

    public void setFree(double free) {
        this.free = CurrencyService.makeString(free);
        notifyPropertyChanged(BR.free);
    }
}
