package com.example.harrispaul.aggregator;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harrispaul on 4/19/2016.
 */
public class CheckBoxAdapter extends BaseAdapter {
    private static ArrayList<Checkbox> searchArrayList;
    private LayoutInflater mInflater;
    public CheckBoxAdapter(Context context, ArrayList<Checkbox> results) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.brands_list, null);
            holder = new ViewHolder();
            holder.cb = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cb.setText(searchArrayList.get(position).getName());
        if(searchArrayList.get(position).getToggle()==0){
            holder.cb.setChecked(false);
        }
        else{
            holder.cb.setChecked(true);
        }
        return convertView;
    }

    static class ViewHolder {
        CheckBox cb;
    }
}
