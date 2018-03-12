package com.hxq.huaxiaoqclient.common;

import com.hxq.huaxiaoqclient.App;
import com.hxq.huaxiaoqclient.utils.DeviceInfoUtil;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ConstantsHxq {
    public static final String version = "1.2.3";
    public static final  String updateUrl  = getHxqDomain() + "/api/open/android/upgrade/get/" +version+".json";
    public static final String AppId = "1260835161000";
    public static final int updateCode = 6;
    public static final String appSort = "HXQ";
    public static  final String getMoreUrl = getHxqDomain() + "/api/open/keyword/getMore/" + version + ".json";
    public static final int getMoreCode = 5;

    public static final String getSearchByWordUrl = getHxqDomain() + "/api/open/coupon/hxq/searchByWord/" + version + ".json";
    public static final int getSearchByWordCode = 1;


    public static final String getDefaultWordUrl = getHxqDomain() + "/api/open/coupon/hxq/defaultWord/" + version + ".json";
    public static final int getDefaultWordCode = 2;

    public static final String getDefaultKeyUrl = getHxqDomain() + "/api/open/keyword/defaultKey/";
    public static final int getDefaultKeyCode = 3;

    public static final String getCarefullyUrl = getHxqDomain() + "/api/open/hxq/recommend/getList/"+version+".json";
    public static final int getCarefullyCode = 4;

    public static final String getIdfaUrl = getHxqDomain() + "/api/open/radio/idfa/insertSelective/" + version + ".json";
    public static final int getIdfaCode = 5;

    //获取手机设备号（imei）
    public static String getDeviceCode() {
        try {
            return DeviceInfoUtil.getDeviceInfo(App.application);
        } catch (Exception e) {
            return "00000000000";
        }

    }
    //获取手机设备号（imei）
    public static String getIdCode() {
        try {
            return DeviceInfoUtil.getIdInfo(App.application);
        } catch (Exception e) {
            return "00000000000";
        }

    }
    private static  String getHxqDomain(){
        return "http://fm.gerenhao.com";
    }
}
