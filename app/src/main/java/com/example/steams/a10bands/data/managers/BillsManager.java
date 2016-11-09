package com.example.steams.a10bands.data.managers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.data.models.bills.Bill;
import com.example.steams.a10bands.providers.StateFactory;
import com.example.steams.a10bands.ui.mainActivity.adapters.BillsListItemAdapter;

/**
 * Created by steams on 10/25/16.
 */

public class BillsManager {

    public static void setupBillsListUI(final Activity context){
        final StateFactory stateFactory = StateFactory.getInstance();

        LinearLayout bills_container;
        TextView bills_container_label;
        ListView bills_list;
        Button bills_button;

        bills_container = (LinearLayout)context.findViewById(R.id.bills_container);
        bills_list = (ListView)bills_container.findViewById(R.id.category_list);
        bills_container_label = (TextView) bills_container.findViewById(R.id.category_label);

        bills_container_label.setText("Bills");
        bills_list.setAdapter( new BillsListItemAdapter(context,stateFactory.billsViewModels));
        bills_button =  (Button)bills_container.findViewById(R.id.category_addButton);
        bills_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateBillDialog(context);
            }
        });
    }

    public static void launchCreateBillDialog(Activity context){
        final StateFactory stateFactory = StateFactory.getInstance();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
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
}
