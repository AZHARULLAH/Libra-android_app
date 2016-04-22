package com.example.harrispaul.aggregator;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harrispaul on 3/30/2016.
 */
public class SingleProductCustomBaseAdapter extends BaseAdapter{
    private static ArrayList<ItemContent> searchArrayList;

    private LayoutInflater mInflater;

    public SingleProductCustomBaseAdapter(Context context, ArrayList<ItemContent> results) {
        this.searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    public ImageView img,shopIcon;
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.items_compared, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.itemName);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            img = (ImageView) convertView.findViewById(R.id.itemIcon);
            shopIcon =(ImageView) convertView.findViewById(R.id.shopIcon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch(searchArrayList.get(position).getDescription()){
            case "FLIPKART" :
                shopIcon.setImageResource(R.drawable.flipkart);

                break;
            case "SNAPDEAL" :
                shopIcon.setImageResource(R.drawable.snapdeal);
                break;
            case "EBAY" :
                shopIcon.setImageResource(R.drawable.ebay);
                break;
            case "AMAZON" :
                shopIcon.setImageResource(R.drawable.amazon);
                break;

        }
        holder.txtName.setText(searchArrayList.get(position).getTitle());
        holder.description.setText(searchArrayList.get(position).getDescription());
        holder.price.setText("Price is " + searchArrayList.get(position).getmaxPrice());
        String Url = searchArrayList.get(position).getImgid();
        ImageId recv = new DownloadImageTask().getBitmap(Url);
        if(recv.getId()==Url){
            img.setImageBitmap(recv.getBm());
        }
        img.setImageBitmap(recv.getBm());
        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        public TextView description;
        TextView price;
    }

}
