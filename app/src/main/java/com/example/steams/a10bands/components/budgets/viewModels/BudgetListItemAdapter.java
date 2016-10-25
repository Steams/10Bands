package com.example.steams.a10bands.components.budgets.viewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.databinding.BudgetListItemBinding;
import com.example.steams.a10bands.providers.StateFactory;

import java.util.List;

/**
 * Created by steams on 10/25/16.
 */

public class BudgetListItemAdapter extends ArrayAdapter<BudgetListItemViewModel> {

    final StateFactory stateFactory = StateFactory.getInstance();

    public BudgetListItemAdapter(Context context, List<BudgetListItemViewModel> budgets){
        super(context,0,budgets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BudgetListItemBinding binding;

        binding = BudgetListItemBinding.inflate(LayoutInflater.from(getContext()),parent, false);
        convertView = binding.getRoot();
        Button makeTransaction = (Button) convertView.findViewById(R.id.budget_make_transaction);

        binding.setBudget(stateFactory.budgetViewModels.get(position));

        return convertView;
    }

}

