package com.example.steams.a10bands.components.budgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.components.budgets.models.Budget;
import com.example.steams.a10bands.components.budgets.viewModels.BudgetListItemAdapter;
import com.example.steams.a10bands.providers.StateFactory;

/**
 * Created by steams on 10/25/16.
 */

public class BudgetsUtil {

    public static void setupBudgets(final Activity context){
        final StateFactory stateFactory = StateFactory.getInstance();

        LinearLayout budgets_container;
        TextView budgets_container_label;
        ListView budgets_list;
        Button budgets_button;

        budgets_container = (LinearLayout)context.findViewById(R.id.budgets_container);
        budgets_list = (ListView)budgets_container.findViewById(R.id.category_list);
        budgets_container_label = (TextView) budgets_container.findViewById(R.id.category_label);

        budgets_container_label.setText("Budgets");
        budgets_list.setAdapter( new BudgetListItemAdapter(context,stateFactory.budgetViewModels));

        budgets_button = (Button)budgets_container.findViewById(R.id.category_addButton);
        budgets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateBudgetDialog(context);
            }
        });

    }

    public static void launchCreateBudgetDialog(Activity context){

        final StateFactory stateFactory = StateFactory.getInstance();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

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
}
