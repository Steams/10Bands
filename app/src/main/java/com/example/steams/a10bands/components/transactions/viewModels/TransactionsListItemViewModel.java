package com.example.steams.a10bands.components.transactions.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.example.steams.a10bands.components.transactions.Transaction;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/26/16.
 */

public class TransactionsListItemViewModel extends BaseObservable {
    private String source;
    private String value;
    private String description;
    private String category;
    private String dateTime;

    public TransactionsListItemViewModel(Transaction t){
        setValue(t.value);
        setCategory(t.category);
        setSource(t.source);
        setDescription(t.description);
    }

    @Bindable
    public String getSource() { return source; }

    public void setSource(String source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = CurrencyService.makeString(value);
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
        notifyPropertyChanged(BR.dateTime);
    }
}
