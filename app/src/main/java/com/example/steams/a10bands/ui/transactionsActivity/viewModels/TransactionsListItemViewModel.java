package com.example.steams.a10bands.ui.transactionsActivity.viewModels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.example.steams.a10bands.data.models.transactions.Transaction;
import com.example.steams.a10bands.utils.CurrencyService;

/**
 * Created by steams on 10/26/16.
 */

public class TransactionsListItemViewModel extends BaseObservable {
    private String source;
    private String value;
    private String description;
    private String category;
    private String date;

    public TransactionsListItemViewModel(Transaction t){
        setValue(t.value);
        setCategory(t.category);
        setSource(t.source);
        setDescription(t.description);
        setDate(t.date);
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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
}
