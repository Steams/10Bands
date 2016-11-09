package com.example.steams.a10bands.providers;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.example.steams.a10bands.data.models.bills.Bill;
import com.example.steams.a10bands.data.models.bills.BillState;
import com.example.steams.a10bands.data.models.buckets.Bucket;
import com.example.steams.a10bands.data.models.buckets.BucketState;
import com.example.steams.a10bands.data.models.budgets.Budget;
import com.example.steams.a10bands.data.models.budgets.BudgetState;
import com.example.steams.a10bands.data.models.goals.GoalState;
import com.example.steams.a10bands.data.models.transactions.Transaction;
import com.example.steams.a10bands.data.models.transactions.TransactionState;
import com.example.steams.a10bands.ui.transactionsActivity.viewModels.TransactionsListItemViewModel;
import com.example.steams.a10bands.data.models.Expense;
import com.example.steams.a10bands.data.models.FundsStatus;
import com.example.steams.a10bands.data.models.goals.Goal;
import com.example.steams.a10bands.data.models.IncomeModel;
import com.example.steams.a10bands.ui.mainActivity.viewModels.BillsListItemViewModel;
import com.example.steams.a10bands.ui.mainActivity.viewModels.BucketListItemViewModel;
import com.example.steams.a10bands.ui.mainActivity.viewModels.BudgetListItemViewModel;
import com.example.steams.a10bands.ui.mainActivity.viewModels.FundsStatusViewModel;
import com.example.steams.a10bands.ui.mainActivity.viewModels.GoalsListItemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by steams on 10/18/16.
 */

public class StateFactory {

    private static StateFactory instance = null;

    public Realm realm;

    private IncomeModel incomeModel = new IncomeModel("SMS Salary",180000.0);

    private FundsStatus fundsStatus = new FundsStatus();


    private Map<String,Budget> budgets = new HashMap<>();
    private Map<String,Bucket> buckets = new HashMap<>();
    private Map<String,Goal> goals = new HashMap<>();
    private Map<String,Bill> bills = new HashMap<>();
    private List<Transaction> transactionList = new ArrayList<>();

    public FundsStatusViewModel fundsStatusViewModel = new FundsStatusViewModel();

    public ObservableList<BudgetListItemViewModel> budgetViewModels = new ObservableArrayList<>();
    public ObservableList<BucketListItemViewModel> bucketViewModels = new ObservableArrayList<>();
    public ObservableList<GoalsListItemViewModel> goalsViewModels = new ObservableArrayList<>();
    public ObservableList<BillsListItemViewModel> billsViewModels = new ObservableArrayList<>();
    public ObservableList<TransactionsListItemViewModel> transactionsViewModels = new ObservableArrayList<>();

    private Map<String,String> expendituresToClass = new HashMap<>();

    private StateFactory(){
//        Realm.deleteRealm(Realm.getDefaultInstance().getConfiguration());
        realm = Realm.getDefaultInstance();
//        clearDB();

        RealmResults<BudgetState> budgetStates = realm.where(BudgetState.class).findAll();
        for(BudgetState b : budgetStates){
            budgets.put(b.name,b.toBudget());
            budgetViewModels.add(new BudgetListItemViewModel(budgets.get(b.name)));
        }

        RealmResults<BucketState> bucketStates = realm.where(BucketState.class).findAll();
        if(bucketStates.size() == 0){
            addBucket(new Bucket("Free Funds"));
            addBucket(new Bucket("Savings"));
        } else {
            for(BucketState b : bucketStates){
                buckets.put(b.name,b.toBucket());
                bucketViewModels.add(new BucketListItemViewModel(buckets.get(b.name)));
            }
        }

        RealmResults<BillState> billStates = realm.where(BillState.class).findAll();
        for(BillState b : billStates){
            bills.put(b.name,b.toBill());
            billsViewModels.add(new BillsListItemViewModel(bills.get(b.name)));
        }

        RealmResults<GoalState> goalStates = realm.where(GoalState.class).findAll();
        for(GoalState b : goalStates){
            goals.put(b.name,b.toGoal());
            goalsViewModels.add(new GoalsListItemViewModel(goals.get(b.name)));
        }

        RealmResults<TransactionState> transactionStates = realm.where(TransactionState.class).findAll();
        for(TransactionState b : transactionStates){
            transactionList.add(b.toTransaction());
            transactionsViewModels.add(new TransactionsListItemViewModel(b.toTransaction()));
        }

        fundsStatusViewModel.setModel(fundsStatus);
        updateFundsStatus();
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
                updateBillState(x);
            }
        } else {
            buckets.get("Free Funds").value += fundsRemaining;
            updateBucketState(buckets.get("Free Funds"));
        }

        //refresh all budgets
        double budgetsTotal = 0;
        for(Budget x : budgets.values()){
            budgetsTotal += x.refreshValue;
        }

        if(fundsRemaining > budgetsTotal){
            for(Budget x : budgets.values()){
                fundsRemaining = x.refresh(fundsRemaining);
                updateBudgetState(x);
            }
        }

        buckets.get("Free Funds").value += fundsRemaining;
        updateBucketState(buckets.get("Free Funds"));

        updateFundsStatus();
    }

    public void updateFundsStatus(){
        fundsStatusViewModel.setFree(buckets.get("Free Funds").value);
        fundsStatusViewModel.setSavings(buckets.get("Savings").value);

        //available = all budgets and buckets and goals combined minus unpaid bills
        double available = 0;

        for (Budget x : budgets.values()){
            available += x.value;
        }

        for (Bucket x : buckets.values()){
            if(!x.name.equals("Savings")){
                available += x.value;
            }
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
            realm.beginTransaction();

                BudgetState state = realm.createObject(BudgetState.class);
                state.name = budget.name;
                state.refreshValue = budget.refreshValue;
                state.value = budget.value;

            realm.commitTransaction();

            budgets.put(budget.name,state.toBudget());
            budgetViewModels.add( new BudgetListItemViewModel(budget));
        }
    }

    public void addBucket(Bucket bucket){
        if(buckets.get(bucket.name) ==null){

            realm.beginTransaction();

                BucketState state = realm.createObject(BucketState.class);
                state.name = bucket.name;
                state.value = bucket.value;

            realm.commitTransaction();

            buckets.put(bucket.name,state.toBucket());
            bucketViewModels.add( new BucketListItemViewModel(bucket));
        }
    }

    public void addBill(Bill bill){
        if(bills.get(bill.name) ==null){

            realm.beginTransaction();

                BillState state = realm.createObject(BillState.class);
                state.name = bill.name;
                state.value = bill.value;
                state.isPaid = bill.isPaid;
                state.refreshValue = bill.refreshValue;

            realm.commitTransaction();

            bills.put(bill.name,state.toBill());
            billsViewModels.add( new BillsListItemViewModel(bill));

            Bucket free = buckets.get("Free Funds");
            free.withdraw(bill.value);
            updateBucketState(free);
        }
    }

    public void addGoal(Goal goal){
        if(goals.get(goal.name) ==null){

            realm.beginTransaction();

                GoalState state = realm.createObject(GoalState.class);
                state.name = goal.name;
                state.value = goal.value;

            realm.commitTransaction();

            goals.put(goal.name,state.toGoal());
            goalsViewModels.add( new GoalsListItemViewModel(goal));
        }
    }

    private void updateBucketState(Bucket b){
        for(BucketListItemViewModel x : bucketViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
        b.persistState();
    }

    private void updateBudgetState(Budget b){
        for(BudgetListItemViewModel x : budgetViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
        b.persistState();
    }

    private void updateBillState(Bill b){
        for(BillsListItemViewModel x : billsViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
        b.persistState();
    }

    private void updateGoalState(Goal b){
        for(GoalsListItemViewModel x : goalsViewModels){
            if(x.getName().equals(b.name)){
                x.setValue(b.value);
            }
        }
        b.persistState();
    }

    public void makeTransaction(Transaction t, String type){
        switch (type){
            case "Budget":
                Budget budget = budgets.get(t.source);
                if(budget.fulfillTransaction(t)){
                    transactionList.add(t);
                    transactionsViewModels.add(new TransactionsListItemViewModel(t));
                    updateBudgetState(budget);
                    updateFundsStatus();
                }
                break;
            case "Bucket":
                Bucket bucket = buckets.get(t.source);
                if(bucket.fulfillTransaction(t)){
                    transactionList.add(t);
                    transactionsViewModels.add(new TransactionsListItemViewModel(t));
                    updateBucketState(bucket);
                    updateFundsStatus();
                }
                break;
        }
        realm.beginTransaction();
            TransactionState state = realm.createObject(TransactionState.class);
            state.category = t.category;
            state.value = t.value;
            state.description = t.description;
            state.source = t.source;
            state.date = t.date;
        realm.commitTransaction();
    }

    public List<String> getExpendituresList(){
        for(Bucket b : buckets.values()){
            expendituresToClass.put(b.name,"Bucket");
        }

        for(Budget b : budgets.values()){
            expendituresToClass.put(b.name,"Budget");
        }

        for(Bill b : bills.values()){
            expendituresToClass.put(b.name,"Bill");
        }

        for(Goal b : goals.values()){
            expendituresToClass.put(b.name,"Goal");
        }

        return new ArrayList<>(expendituresToClass.keySet());
    }

    public boolean transferFunds(String from, String to, double amount) {
        String fromClass = expendituresToClass.get(from);
        String toClass = expendituresToClass.get(to);

        Expense fromModel = null;
        Expense toModel = null;

        Log.d("DEBUG", "transferFunds: from : " + from + " to : " + to);

        switch (fromClass){
            case "Bucket":
                fromModel = buckets.get(from);
                break;
            case "Budget":
                fromModel = budgets.get(from);
                break;
            case "Bill":
                fromModel = bills.get(from);
                break;
            case "Goal":
                fromModel = goals.get(from);
                break;
        }

        if(fromModel.value < amount){
            return false;
        }

        switch (toClass){
            case "Bucket":
                toModel = buckets.get(to);
                break;
            case "Budget":
                toModel = budgets.get(to);
                break;
            case "Bill":
                toModel = bills.get(to);
                break;
            case "Goal":
                toModel = goals.get(to);
                break;
        }

        fromModel.withdraw(amount);
        toModel.deposit(amount);

        updateExpense(fromModel,fromClass);
        updateExpense(toModel,toClass);

        updateFundsStatus();
        return true;
    }

    public boolean setModelValue(String modelName, double amount) {
        String modelClass = expendituresToClass.get(modelName);

        Expense modelInstance = null;

        switch (modelClass){
            case "Bucket":
                modelInstance = buckets.get(modelName);
                break;
            case "Budget":
                modelInstance = budgets.get(modelName);
                break;
            case "Bill":
                modelInstance = bills.get(modelName);
                break;
            case "Goal":
                modelInstance = goals.get(modelName);
                break;
        }

        modelInstance.value = amount;

        updateExpense(modelInstance,modelClass);
        updateFundsStatus();

        return true;
    }

    public void updateExpense(Expense x, String type){
        switch (type){
            case "Bucket":
                updateBucketState((Bucket)x);
                break;
            case "Budget":
                updateBudgetState((Budget)x);
                break;
            case "Bill":
                updateBillState((Bill)x);
                break;
            case "Goal":
                updateGoalState((Goal)x);
                break;
        }
    }

    public void clearDB(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });

        realm.close();
        realm.deleteRealm(realm.getConfiguration());
    }

    public void payBill(String billName){
        Bill bill = bills.get(billName);
        Transaction t = bill.pay();

        transactionList.add(t);
        transactionsViewModels.add(new TransactionsListItemViewModel(t));

        realm.beginTransaction();
            TransactionState state = realm.createObject(TransactionState.class);
            state.description = t.description;
            state.value = t.value;
            state.source = t.source;
            state.category = t.category;
            state.date = t.date;
        realm.commitTransaction();

        updateBillState(bill);
        updateFundsStatus();
    }
}
