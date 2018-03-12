package com.hxq.huaxiaoqclient.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxq.huaxiaoqclient.R;

import java.util.List;

/**
 * Created by liuz on 16/6/3.
 */
public class SlidingLeftAdapter extends BaseAdapter {
    private Context mContext;
    private   List<String> mList;
    private   List<Integer> icons;

public SlidingLeftAdapter(Context mContext, List<String> mList,List<Integer> icons) {
        this.mContext = mContext;
        this.mList = mList;
        this.icons = icons;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int picWidth = dm.widthPixels;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sliding,parent,false);
            holder.sliding_iv = (ImageView) view.findViewById(R.id.sliding_iv);
            holder.sliding_tv = (TextView) view.findViewById(R.id.sliding_tv);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.sliding_iv.setImageResource(icons.get(position));
        holder.sliding_tv.setText(mList.get(position));
        return view;
    }
    static class ViewHolder {
        ImageView sliding_iv;
        TextView sliding_tv;
    }
}
