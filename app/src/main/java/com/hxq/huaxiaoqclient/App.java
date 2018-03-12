package com.hxq.huaxiaoqclient;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;

import org.senydevpkg.utils.ALog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Administrator on 2016/10/17.
 */

public class App extends Application {
    /**
     * 全局Application，方便调用
     */
    public static App application;
    private static App _instance;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final String CONV_TITLE = "convTitle";
    public static final String TARGET_ID = "targetId";
    public static final String GROUP_ID = "groupId";
    public static final String DRAFT = "draft";
    public static Map<String, Long> map;


    private int count = 0;
    private boolean havaLocPermission = false;


    public static App get() {
        return _instance;
    }

    public static List<Bitmap> bitmapList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //Toast.makeText(App.this, "初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
//                Toast.makeText(App.this, "初始化失败,错误码="+code+" / 错误消息="+msg, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private static Map<String, Activity> destoryMap = new HashMap<>();

    public static void addBitmap(Bitmap bitmap) {
        bitmapList.add(bitmap);
    }

    public static void recyleBitmap() {
        if (null != bitmapList && bitmapList.size() > 0) {
            for (Bitmap bitmap : bitmapList) {
                // 先判断是否已经回收
                if (bitmap != null && !bitmap.isRecycled()) {
                    ALog.d("recyasas");
                    // 回收并且置为null
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            System.gc();
        }
    }

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity() {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
        destoryMap.clear();
    }

    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            if (key.equals(activityName)) {
                destoryMap.get(key).finish();
            }
        }
        destoryMap.clear();
    }



}
