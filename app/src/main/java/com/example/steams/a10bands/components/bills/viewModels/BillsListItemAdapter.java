package com.example.steams.a10bands.components.bills.viewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.annotations.NonNull;
import com.example.steams.a10bands.R;
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
        final BillListItemBinding binding;

        binding = BillListItemBinding.inflate(LayoutInflater.from(getContext()),parent, false);
        convertView = binding.getRoot();
        Button payBillButton = (Button) convertView.findViewById(R.id.bill_pay_bill);

        binding.setBill(stateFactory.billsViewModels.get(position));

        payBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateFactory.payBill(binding.getBill().getName());
            }
        });

        return convertView;
    }
}
