package com.hxq.huaxiaoqclient.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hxq.huaxiaoqclient.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class LeftFragment extends Fragment
{
    private View mView;
    private List<String> mDatas = Arrays
            .asList("聊天", "发现", "通讯录", "朋友圈", "订阅号");
    private ListAdapter mAdapter;
    private ListView mCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if(mView == null)
        {
            mView = inflater.inflate(R.layout.left_menu, container, false);
            mCategories = (ListView) mView
                    .findViewById(R.id.id_listview_categories);
            mAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, mDatas);
            mCategories.setAdapter(mAdapter);
        }
        return mView ;
    }
}
