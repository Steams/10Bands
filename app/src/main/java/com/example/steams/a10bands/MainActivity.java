package com.example.steams.a10bands;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.steams.a10bands.components.bills.BillsUtil;
import com.example.steams.a10bands.components.buckets.BucketsUtil;
import com.example.steams.a10bands.components.budgets.BudgetsUtil;
import com.example.steams.a10bands.components.goals.GoalsUtil;
import com.example.steams.a10bands.databinding.ActivityMainBinding;
import com.example.steams.a10bands.databinding.GoalListItemBinding;
import com.example.steams.a10bands.components.goals.models.Goal;
import com.example.steams.a10bands.providers.StateFactory;
import com.example.steams.a10bands.components.goals.viewModels.GoalsListItemViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    private StateFactory stateFactory;

    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stateFactory  = StateFactory.getInstance();
        alertBuilder  = new AlertDialog.Builder(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setFundsStatusVM(stateFactory.fundsStatusViewModel);

        BudgetsUtil.setupBudgetsListUI(this);
        BillsUtil.setupBillsListUI(this);
        BucketsUtil.setupBucketsListUI(this);
        GoalsUtil.setupGoalsListUI(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.menu_add_income:
                stateFactory.injectIncome();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
