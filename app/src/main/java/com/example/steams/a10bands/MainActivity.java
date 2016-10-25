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

import com.example.steams.a10bands.databinding.ActivityMainBinding;
import com.example.steams.a10bands.databinding.BillListItemBinding;
import com.example.steams.a10bands.databinding.BucketListItemBinding;
import com.example.steams.a10bands.databinding.BudgetListItemBinding;
import com.example.steams.a10bands.databinding.GoalListItemBinding;
import com.example.steams.a10bands.models.Bill;
import com.example.steams.a10bands.models.Bucket;
import com.example.steams.a10bands.components.budgets.models.Budget;
import com.example.steams.a10bands.models.Goal;
import com.example.steams.a10bands.providers.StateFactory;
import com.example.steams.a10bands.viewModels.BillsListItemViewModel;
import com.example.steams.a10bands.viewModels.BucketListItemViewModel;
import com.example.steams.a10bands.components.budgets.viewModels.BudgetListItemViewModel;
import com.example.steams.a10bands.viewModels.GoalsListItemViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public LinearLayout bills_container;
    public TextView bills_container_label;
    public ListView bills_list;
    public Button bills_button;

    public LinearLayout buckets_container;
    public TextView buckets_container_label;
    public ListView buckets_list;
    public Button buckets_button;

    public LinearLayout goals_container;
    public TextView goals_container_label;
    public ListView goals_list;
    public Button goals_button;

    private StateFactory stateFactory;

    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stateFactory  = StateFactory.getInstance();
        alertBuilder  = new AlertDialog.Builder(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setFundsStatusVM(stateFactory.fundsStatusViewModel);

        setupBudgets();
        setupBuckets();
        setupBills();
        setupGoals();
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

    private void setupBudgets(){
        LinearLayout budgets_container;
        TextView budgets_container_label;
        ListView budgets_list;
        Button budgets_button;

        budgets_container = (LinearLayout)findViewById(R.id.budgets_container);
        budgets_list = (ListView)budgets_container.findViewById(R.id.category_list);
        budgets_container_label = (TextView) budgets_container.findViewById(R.id.category_label);

        budgets_container_label.setText("Budgets");
        budgets_list.setAdapter( new BudgetViewModelAdapter(this,stateFactory.budgetViewModels));

        budgets_button = (Button)budgets_container.findViewById(R.id.category_addButton);
        budgets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateBudgetDialog();
            }
        });

    }

    public void launchCreateBudgetDialog(){
        alertBuilder.setTitle("Create new Budget")
                .setView(R.layout.create_budget_dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Dialog d = (Dialog) dialog;
                        EditText name,value;
                        name = (EditText) d.findViewById(R.id.create_budget_name);
                        value = (EditText) d.findViewById(R.id.create_budget_value);
                        stateFactory.addBudget(
                                new Budget(
                                    name.getText().toString().trim(),
                                    Double.parseDouble(value.getText().toString().trim()
                                )));
                    }
                });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void setupBuckets(){
        buckets_container = (LinearLayout)findViewById(R.id.buckets_container);
        buckets_list = (ListView)buckets_container.findViewById(R.id.category_list);
        buckets_container_label = (TextView) buckets_container.findViewById(R.id.category_label);

        buckets_container_label.setText("Buckets");
        buckets_list.setAdapter( new BucketViewModelAdapter(this,stateFactory.bucketViewModels));
        buckets_button = (Button)buckets_container.findViewById(R.id.category_addButton);
        buckets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateBucketDialog();
            }
        });

    }

    public void launchCreateBucketDialog(){
        alertBuilder.setTitle("Create new Bucket")
                .setView(R.layout.create_bucket_dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Dialog d = (Dialog) dialog;
                        EditText name,value;
                        name = (EditText) d.findViewById(R.id.create_bucket_name);
                        stateFactory.addBucket(
                                new Bucket( name.getText().toString().trim())
                        );
                    }
                });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void setupBills(){
        bills_container = (LinearLayout)findViewById(R.id.bills_container);
        bills_list = (ListView)bills_container.findViewById(R.id.category_list);
        bills_container_label = (TextView) bills_container.findViewById(R.id.category_label);

        bills_container_label.setText("Bills");
        bills_list.setAdapter( new BillViewModelAdapter(this,stateFactory.billsViewModels));
        bills_button =  (Button)bills_container.findViewById(R.id.category_addButton);
        bills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateBillDialog();
            }
        });
    }

    public void launchCreateBillDialog(){
        alertBuilder.setTitle("Create new Bill")
                .setView(R.layout.create_bill_dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Dialog d = (Dialog) dialog;
                        EditText name,value;
                        name = (EditText) d.findViewById(R.id.create_bill_name);
                        value = (EditText) d.findViewById(R.id.create_bill_value);
                        stateFactory.addBill(
                                new Bill(
                                        name.getText().toString().trim(),
                                        Double.parseDouble(value.getText().toString().trim()
                                        )));
                    }
                });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void setupGoals(){
        goals_container = (LinearLayout)findViewById(R.id.goals_container);
        goals_list = (ListView)goals_container.findViewById(R.id.category_list);
        goals_container_label = (TextView) goals_container.findViewById(R.id.category_label);

        goals_container_label.setText("Goals");
        goals_list.setAdapter( new GoalViewModelAdapter(this,stateFactory.goalsViewModels));
        goals_button = (Button)goals_container.findViewById(R.id.category_addButton);
        goals_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateGoalDialog();
            }
        });
    }

    public void launchCreateGoalDialog(){
        alertBuilder.setTitle("Create new Goal")
                .setView(R.layout.create_goal_dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Dialog d = (Dialog) dialog;
                        EditText name,value;
                        name = (EditText) d.findViewById(R.id.create_goal_name);
                        value = (EditText) d.findViewById(R.id.create_goal_target);
                        stateFactory.addGoal(
                                new Goal(
                                        name.getText().toString().trim(),
                                        Double.parseDouble(value.getText().toString().trim()
                                        )));
                    }
                });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }


    public class BudgetViewModelAdapter extends ArrayAdapter<BudgetListItemViewModel> {

        public BudgetViewModelAdapter(Context context, List<BudgetListItemViewModel> budgets){
            super(context,0,budgets);
        }

        @NonNull
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

    public class BucketViewModelAdapter extends ArrayAdapter<BucketListItemViewModel> {

        public BucketViewModelAdapter(Context context, List<BucketListItemViewModel> budgets){
            super(context,0,budgets);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            BucketListItemBinding binding;

            binding = BucketListItemBinding.inflate(LayoutInflater.from(getContext()),parent, false);
            convertView = binding.getRoot();
            Button button = (Button) convertView.findViewById(R.id.bucket_list_item_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            binding.setBucket(stateFactory.bucketViewModels.get(position));

            return convertView;
        }
    }

    public class GoalViewModelAdapter extends ArrayAdapter<GoalsListItemViewModel> {

        public GoalViewModelAdapter(Context context, List<GoalsListItemViewModel> budgets){
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

    public class BillViewModelAdapter extends ArrayAdapter<BillsListItemViewModel> {

        public BillViewModelAdapter(Context context, List<BillsListItemViewModel> budgets){
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
}
