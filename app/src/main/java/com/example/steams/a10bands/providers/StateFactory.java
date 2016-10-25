package com.example.steams.a10bands.providers;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.steams.a10bands.components.bills.models.Bill;
import com.example.steams.a10bands.components.buckets.Bucket;
import com.example.steams.a10bands.components.budgets.models.Budget;
import com.example.steams.a10bands.models.FundsStatus;
import com.example.steams.a10bands.components.goals.models.Goal;
import com.example.steams.a10bands.models.IncomeModel;
import com.example.steams.a10bands.components.bills.viewModels.BillsListItemViewModel;
import com.example.steams.a10bands.components.buckets.viewModels.BucketListItemViewModel;
import com.example.steams.a10bands.components.budgets.viewModels.BudgetListItemViewModel;
import com.example.steams.a10bands.viewModels.FundsStatusViewModel;
import com.example.steams.a10bands.components.goals.viewModels.GoalsListItemViewModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steams on 10/18/16.
 */

public class StateFactory {

    private static StateFactory instance = null;

    private double currentFunds = 0;
    private double income = 0;

    private FundsStatus fundsStatus = new FundsStatus();
    public FundsStatusViewModel fundsStatusViewModel = new FundsStatusViewModel();

    private IncomeModel incomeModel = new IncomeModel("SMS Salary",180000.0);

    private Map<String,Budget> budgets = new HashMap<>();
    private Map<String,Bucket> buckets = new HashMap<>();
    private Map<String,Goal> goals = new HashMap<>();
    private Map<String,Bill> bills = new HashMap<>();

    public ObservableList<BudgetListItemViewModel> budgetViewModels = new ObservableArrayList<>();
    public ObservableList<BucketListItemViewModel> bucketViewModels = new ObservableArrayList<>();
    public ObservableList<GoalsListItemViewModel> goalsViewModels = new ObservableArrayList<>();
    public ObservableList<BillsListItemViewModel> billsViewModels = new ObservableArrayList<>();


    private StateFactory(){
        buckets.put("Free Funds",new Bucket("Free Funds"));
        buckets.put("Savings",new Bucket("Savings"));
        bucketViewModels.add(new BucketListItemViewModel(buckets.get("Free Funds")));
        bucketViewModels.add(new BucketListItemViewModel(buckets.get("Savings")));

        updateFundsStatus();
        fundsStatusViewModel.setModel(fundsStatus);
    }

    public static StateFactory getInstance(){
        if(instance ==null){
            instance = new StateFactory();
        }
        return instance;
    }

    public void injectIncome(){
        double fundsRemaining = incomeModel.value;

        //refresh all bills
        double billsTotal = 0;
        for(Bill x : bills.values()){
            billsTotal += x.refreshValue;
        }

        if(fundsRemaining > billsTotal){
            for(Bill x : bills.values()){
                fundsRemaining = x.refresh(fundsRemaining);
                updateBillVM(x);
            }
        } else {
            buckets.get("Free Funds").value += fundsRemaining;
            updateBucketVM(buckets.get("Free Funds"));
        }

        //refresh all budgets
        double budgetsTotal = 0;
        for(Budget x : budgets.values()){
            budgetsTotal += x.refreshValue;
        }

        if(fundsRemaining > budgetsTotal){
            for(Budget x : budgets.values()){
                fundsRemaining = x.refresh(fundsRemaining);
                updateBudgetVM(x);
            }
        }

        buckets.get("Free Funds").value += fundsRemaining;
        updateBucketVM(buckets.get("Free Funds"));

        updateFundsStatus();
    }

    public FundsStatus getFundsStatus(){
        return fundsStatus;
    }

    public void updateFundsStatus(){
        fundsStatusViewModel.setFree(buckets.get("Free Funds").value);
        fundsStatusViewModel.setSavings(buckets.get("Savings").value);

        //available = all budgets and buckets and goals combined
        double available = 0;

        for (Budget x : budgets.values()){
            available += x.value;
        }

        for (Bucket x : buckets.values()){
            available += x.value;
        }

        for (Goal x : goals.values()){
            available += x.value;
        }

        fundsStatusViewModel.setAvailable(available);

        //balance = avaialable + value of all unpaid bills
        double balance = 0;

        for (Bill x : bills.values()){
            if(!x.isPaid){
                balance += x.value;
            }
        }
        balance += available;

        fundsStatusViewModel.setBalance(balance);
    }

    public void addBudget(Budget budget){
        if(budgets.get(budget.name) ==null){
            budgets.put(budget.name,budget);
            budgetViewModels.add( new BudgetListItemViewModel(budget));
        }
    }

    public void addBucket(Bucket bucket){
        if(buckets.get(bucket.name) ==null){
            buckets.put(bucket.name,bucket);
            bucketViewModels.add( new BucketListItemViewModel(bucket));
        }
    }

    public void addBill(Bill bill){
        if(bills.get(bill.name) ==null){
            bills.put(bill.name,bill);
            billsViewModels.add( new BillsListItemViewModel(bill));
        }
    }

    public void addGoal(Goal goal){
        if(goals.get(goal.name) ==null){
            goals.put(goal.name,goal);
            goalsViewModels.add( new GoalsListItemViewModel(goal));
        }
    }

    private void updateBucketVM(Bucket b){
        for(BucketListItemViewModel x : bucketViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
    }

    private void updateBudgetVM(Budget b){
        for(BudgetListItemViewModel x : budgetViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
    }

    private void updateBillVM(Bill b){
        for(BillsListItemViewModel x : billsViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
    }

}
