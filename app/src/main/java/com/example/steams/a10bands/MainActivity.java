package com.example.steams.a10bands;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] words = {"hey","yo"};
    public ListView budgetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        budgetsList = (ListView)findViewById(R.id.budgets_list);
        budgetsList.setAdapter(
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        words)
        );
    }
}
