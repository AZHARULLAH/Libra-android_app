package com.example.harrispaul.aggregator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by saksham on 22/4/16.
 */
public class SearchItemAdapter extends ArrayAdapter<Searchitem> {

    public SearchItemAdapter(Activity context, List<Searchitem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Searchitem item = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_compared, parent, false);


        ImageView itemImageView = (ImageView)convertView.findViewById(R.id.itemIcon);
        Picasso.with(getContext()).load(item.getImageUrls().get(0).getUrl()).into(itemImageView);

        TextView itemNameTV = (TextView)convertView.findViewById(R.id.itemName);
        itemNameTV.setText(item.getTitle());

        TextView descriptionTV = (TextView)convertView.findViewById(R.id.description);
        descriptionTV.setText(item.getDescription());
//
//        TextView priceTV = (TextView)convertView.findViewById(R.id.price);
//        priceTV.setText(item.get);
        return convertView;
    }
}
