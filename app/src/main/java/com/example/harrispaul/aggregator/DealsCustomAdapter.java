package com.example.harrispaul.aggregator;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harrispaul on 4/4/2016.
 */
public class DealsCustomAdapter extends BaseAdapter{
    private static ArrayList<ItemContent> searchArrayList;

    private LayoutInflater mInflater;

    public DealsCustomAdapter(Context context, ArrayList<ItemContent> results) {
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
    public ImageView img;
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.items_compared, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.itemName);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            img = (ImageView) convertView.findViewById(R.id.itemIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getTitle());
        holder.description.setText(searchArrayList.get(position).getDescription());
        holder.price.setText(searchArrayList.get(position).getmaxPrice());
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
