package com.example.steams.a10bands.ui.transactionsActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.ui.transactionsActivity.viewModels.TransactionsListItemViewModel;
import com.example.steams.a10bands.databinding.TransactionListItemBinding;
import com.example.steams.a10bands.providers.StateFactory;

import java.util.List;

import io.realm.Realm;

/**
 * Created by steams on 10/26/16.
 */

public class TransactionsActivity extends AppCompatActivity {

    private StateFactory stateFactory = StateFactory.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Realm.init(this);

        ListView transactions_list;

        transactions_list = (ListView)findViewById(R.id.transactions_list);

        transactions_list.setAdapter( new TransactionListItemAdapter(this,stateFactory.transactionsViewModels));
    }

    public class TransactionListItemAdapter extends ArrayAdapter<TransactionsListItemViewModel> {
        StateFactory stateFactory = StateFactory.getInstance();

        public TransactionListItemAdapter(Context context, List<TransactionsListItemViewModel> transactions){
            super(context,0,transactions);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TransactionListItemBinding binding;

            binding = TransactionListItemBinding.inflate(LayoutInflater.from(getContext()),parent, false);
            convertView = binding.getRoot();

            binding.setTransaction(stateFactory.transactionsViewModels.get(position));

            return convertView;
        }
    }
}
