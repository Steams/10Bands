package com.example.steams.a10bands.components.bills.viewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.annotations.NonNull;
import com.example.steams.a10bands.databinding.BillListItemBinding;
import com.example.steams.a10bands.providers.StateFactory;

import java.util.List;

/**
 * Created by steams on 10/25/16.
 */

public class BillsListItemAdapter extends ArrayAdapter<BillsListItemViewModel> {
    StateFactory stateFactory = StateFactory.getInstance();

    public BillsListItemAdapter(Context context, List<BillsListItemViewModel> budgets){
        super(context,0,budgets);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillListItemBinding binding;

        binding = BillListItemBinding.inflate(LayoutInflater.from(getContext()),parent, false);
        convertView = binding.getRoot();

        binding.setBill(stateFactory.billsViewModels.get(position));

        return convertView;
    }
}
