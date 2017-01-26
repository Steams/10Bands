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
import com.example.steams.a10bands.data.models.goals.Goal;
import com.example.steams.a10bands.ui.mainActivity.adapters.GoalListItemAdapter;
import com.example.steams.a10bands.providers.StateFactory;

/**
 * Created by steams on 10/25/16.
 */

public class GoalsUtil {
    final static StateFactory stateFactory = StateFactory.getInstance();

    public static void setupGoalsListUI(final Activity context){
        LinearLayout goals_container;
        TextView goals_container_label;
        ListView goals_list;
        Button goals_button;

        goals_container = (LinearLayout)context.findViewById(R.id.goals_container);
        goals_list = (ListView)goals_container.findViewById(R.id.category_list);
        goals_container_label = (TextView) goals_container.findViewById(R.id.category_label);

        goals_container_label.setText("Goals");
        goals_list.setAdapter( new GoalListItemAdapter(context,stateFactory.goalsViewModels));

        goals_button = (Button)goals_container.findViewById(R.id.category_addButton);
        goals_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateGoalDialog(context);
            }
        });
    }

    public static void launchCreateGoalDialog(Activity context){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
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
                                        Double.parseDouble(value.getText().toString().trim())
                                         ));
                    }
                });

        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}
