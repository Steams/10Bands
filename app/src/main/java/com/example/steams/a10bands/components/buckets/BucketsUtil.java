package com.example.steams.a10bands.components.buckets;

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
import com.example.steams.a10bands.components.buckets.viewModels.BucketsListItemAdapter;
import com.example.steams.a10bands.providers.StateFactory;

/**
 * Created by steams on 10/25/16.
 */

public class BucketsUtil {


    public static void setupBucketsListUI(final Activity context){
        final StateFactory stateFactory = StateFactory.getInstance();

        LinearLayout buckets_container;
        TextView buckets_container_label;
        ListView buckets_list;
        Button buckets_button;

        buckets_container = (LinearLayout)context.findViewById(R.id.buckets_container);
        buckets_list = (ListView)buckets_container.findViewById(R.id.category_list);
        buckets_container_label = (TextView) buckets_container.findViewById(R.id.category_label);

        buckets_container_label.setText("Buckets");
        buckets_list.setAdapter( new BucketsListItemAdapter(context,stateFactory.bucketViewModels));
        buckets_button = (Button)buckets_container.findViewById(R.id.category_addButton);
        buckets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateBucketDialog(context);
            }
        });

    }

    public static void launchCreateBucketDialog(Activity context){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        final StateFactory stateFactory = StateFactory.getInstance();

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
}

