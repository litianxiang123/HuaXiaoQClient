package com.hxq.huaxiaoqclient.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hxq.huaxiaoqclient.model.HomeData;
import com.hxq.huaxiaoqclient.ui.activity.BrowsingHostoryActivity;
import com.hxq.huaxiaoqclient.ui.activity.ShowGoodsActivity;

/**
 * Created by Administrator on 2017/8/18.
 */

public class JumpUtil
{
    public static void jump(Context context,AlibcUtil alibcUtil,int position){
        switch (position){
            case 1:
                alibcUtil.showOrder();
                break;
            case 2:
                alibcUtil.showCart();
                break;
            case 3:
               Intent intent = new Intent(context, BrowsingHostoryActivity.class);
               context.startActivity(intent);
                break;
        }
    }

    //外链
    public static void startAD(Context context,HomeData vo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("vo", vo);
        Intent intent = new Intent(context, ShowGoodsActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
