package com.example.steams.a10bands.ui.mainActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.data.managers.BillsManager;
import com.example.steams.a10bands.data.managers.BucketsUtil;
import com.example.steams.a10bands.data.managers.BudgetsUtil;
import com.example.steams.a10bands.data.managers.GoalsUtil;
import com.example.steams.a10bands.data.managers.TransactionsUtil;
import com.example.steams.a10bands.databinding.ActivityMainBinding;
import com.example.steams.a10bands.providers.StateFactory;
import com.example.steams.a10bands.ui.transactionsActivity.TransactionsActivity;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private StateFactory stateFactory ;

    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        stateFactory = StateFactory.getInstance();

        alertBuilder  = new AlertDialog.Builder(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setFundsStatusVM(stateFactory.fundsStatusViewModel);

        BudgetsUtil.setupBudgetsListUI(this);
        BillsManager.setupBillsListUI(this);
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
            case R.id.menu_transfer:
                TransactionsUtil.launchTransferFundsDialog(this);
                return true;
            case R.id.menu_set_value:
                TransactionsUtil.launchSetModelValueDialog(this);
                return true;
            case R.id.menu_view_transactions:
                Intent i = new Intent(this, TransactionsActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_delete_db:
                stateFactory.clearDB();
        }
        return super.onOptionsItemSelected(item);
    }



}
