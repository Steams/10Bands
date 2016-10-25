package com.example.steams.a10bands.components.buckets.viewModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.steams.a10bands.R;
import com.example.steams.a10bands.databinding.BucketListItemBinding;
import com.example.steams.a10bands.providers.StateFactory;

import java.util.List;

/**
 * Created by steams on 10/25/16.
 */

public class BucketsListItemAdapter extends ArrayAdapter<BucketListItemViewModel> {
    final StateFactory stateFactory = StateFactory.getInstance();

    public BucketsListItemAdapter(Context context, List<BucketListItemViewModel> budgets){
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
