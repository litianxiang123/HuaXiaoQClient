package com.hxq.huaxiaoqclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class NetWorkUtil {
    private static final String TYPE_NAME = "mobile";

    /**
     * @Description 得到当前网络连接类型
     * @author huangke@yoka.com
     * @param context 环境
     * @return String 当前网络连接类型名称
     * @date 2011-12-5 上午11:02:07
     */
    public static String getCurrentNetworkTypeName(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return null;
        } else {// 当前存在网络接入方式，获取具体的网络接入类型值
            String typeName = networkInfo.getTypeName();
            if (typeName.contains(TYPE_NAME)) {
                String extraInfo = networkInfo.getExtraInfo();
                if (extraInfo == null) {
                    extraInfo = "ChinaTelecom";
                }
                return extraInfo;
            }
            return typeName;
        }
    }
    //检查网络连接状态，Monitor network connections (Wi-Fi, GPRS, UMTS, etc.)
    public static boolean checkNetWorkStatus(Context context){
        boolean result;
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if ( netinfo !=null && netinfo.isConnected() ) {
            result=true;
        }else{
            result=false;
        }
        return result;
    }
    //检查url 链接是否可用
    public static boolean checkURL(String url){
        boolean value=false;
        try {
            HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
            conn.setConnectTimeout(2000);
            int code=conn.getResponseCode();
            Log.d("--------------------------------------------playUrlCode:", String.valueOf(code));
            if(code!=200){
                value=false;
            }else{
                value=true;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }


    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }else {
            if (networkInfo.isConnected()) {
                return true;
            }else {
                return false;
            }
        }
    }
}
