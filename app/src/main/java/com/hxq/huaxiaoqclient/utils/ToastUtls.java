package com.hxq.huaxiaoqclient.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ToastUtls {
    private static String oldMsg;
    private static long time;

    public static void showToast(Context context, String msg, int duration) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (TextUtils.isEmpty(oldMsg) || !msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(context, msg, duration).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(context, msg, duration).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;
    }

}
