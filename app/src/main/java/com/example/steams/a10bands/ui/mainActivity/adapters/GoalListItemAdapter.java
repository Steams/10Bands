package com.example.steams.a10bands.ui.mainActivity.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.steams.a10bands.ui.mainActivity.viewModels.GoalsListItemViewModel;
import com.example.steams.a10bands.databinding.GoalListItemBinding;
import com.example.steams.a10bands.providers.StateFactory;

import java.util.List;

/**
 * Created by steams on 10/25/16.
 */

public class GoalListItemAdapter extends ArrayAdapter<GoalsListItemViewModel> {
    final StateFactory stateFactory = StateFactory.getInstance();

    public GoalListItemAdapter(Context context, List<GoalsListItemViewModel> budgets){
        super(context,0,budgets);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoalListItemBinding binding;

        binding = GoalListItemBinding.inflate(LayoutInflater.from(getContext()),parent, false);
        convertView = binding.getRoot();

        binding.setGoal(stateFactory.goalsViewModels.get(position));

        return convertView;
    }
}
