package com.example.steams.a10bands.components.transactions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.models.Expense;
import com.example.steams.a10bands.providers.StateFactory;

import java.util.List;

/**
 * Created by steams on 10/25/16.
 */

public class TransactionsUtil {

    public static void launchCreateTransactionDialog(Activity context, final String source, final String sourceType){

        final StateFactory stateFactory = StateFactory.getInstance();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder
                .setTitle("Create Transaction")
                .setView(R.layout.create_transaction_dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog d = (Dialog) dialog;
                        EditText value,description,category;

                        description = (EditText) d.findViewById(R.id.create_transaction_description);
                        category = (EditText) d.findViewById(R.id.create_transaction_category);
                        value = (EditText) d.findViewById(R.id.create_transaction_value);

                        stateFactory.makeTransaction(
                                new Transaction(
                                        description.getText().toString(),
                                        source,
                                        Double.parseDouble(value.getText().toString()),
                                        category.getText().toString()
                                ),
                                sourceType);
                    }
                });
        alertBuilder.show();

    }

    public static void launchTransferFundsDialog(Activity context) {

        final StateFactory stateFactory = StateFactory.getInstance();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View transfer_funds_container = inflater.inflate(R.layout.transfer_funds_dialog,null);

        Spinner transfer_from_spinner = (Spinner)transfer_funds_container.findViewById(R.id.transfer_funds_from);
        Spinner transfer_to_spinner = (Spinner)transfer_funds_container.findViewById(R.id.transfer_funds_to);

        List<String> expenditures = stateFactory.getExpendituresList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,expenditures);

        transfer_from_spinner.setAdapter(adapter);
        transfer_to_spinner.setAdapter(adapter);

        alertBuilder
                .setTitle("Transfer Funds")
                .setView(transfer_funds_container)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog d = (Dialog) dialog;
                        Spinner from,to;
                        EditText amount;

                        from = (Spinner) d.findViewById(R.id.transfer_funds_from);
                        to = (Spinner) d.findViewById(R.id.transfer_funds_to);
                        amount = (EditText) d.findViewById(R.id.transfer_funds_amount);

                        stateFactory.transferFunds(
                                from.getSelectedItem().toString(),
                                to.getSelectedItem().toString(),
                                Double.parseDouble(amount.getText().toString())
                        );
                    }
                });
        alertBuilder.show();
    }

    public static void launchSetModelValueDialog(Activity context) {

        final StateFactory stateFactory = StateFactory.getInstance();

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View set_model_value_container = inflater.inflate(R.layout.set_model_value_dialog,null);

        Spinner model_spinner = (Spinner)set_model_value_container.findViewById(R.id.set_model_value_model);

        List<String> expenditures = stateFactory.getExpendituresList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,expenditures);

        model_spinner.setAdapter(adapter);

        alertBuilder
                .setTitle("Set Model Value")
                .setView(set_model_value_container)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog d = (Dialog) dialog;
                        Spinner model;
                        EditText amount;

                        model = (Spinner) d.findViewById(R.id.set_model_value_model);
                        amount = (EditText) d.findViewById(R.id.set_model_value_value);

                        stateFactory.setModelValue(
                                model.getSelectedItem().toString(),
                                Double.parseDouble(amount.getText().toString())
                        );
                    }
                });
        alertBuilder.show();
    }
}
