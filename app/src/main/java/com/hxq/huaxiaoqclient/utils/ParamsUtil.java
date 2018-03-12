package com.hxq.huaxiaoqclient.utils;

import com.hxq.huaxiaoqclient.common.ConstantsHxq;

import org.senydevpkg.net.HttpParams;

/**
 * Created by Administrator on 2017/8/10.
 */

public class ParamsUtil {
    public static HttpParams getDefaultParams(){
        HttpParams httpParams = new HttpParams();
        httpParams.put("version", ConstantsHxq.version);
        return httpParams;
    }
    public static HttpParams getMoreByPageIndex(int pageIndex){
        HttpParams httpParams = new HttpParams();
        httpParams.put("pageIndex",String.valueOf(pageIndex))
                .put("version",ConstantsHxq.version);
        return httpParams;
    }

    public static HttpParams getGoodsBySearch(String searchWord){
        HttpParams httpParams = new HttpParams();
        httpParams.put("searchWord",searchWord);
        httpParams.put("",ConstantsHxq.version);
        return httpParams;
    }
    public static HttpParams getAppSortParams(String appShort){
        HttpParams httpParams = new HttpParams();
        httpParams.put("appShort",appShort);
        return httpParams;
    }

    public static HttpParams getRegisterReportParams(String appid,String idfa){
        HttpParams httpParams = new HttpParams();
        httpParams.put("appid",appid);
        httpParams.put("idfa",idfa);
        httpParams.put("version",ConstantsHxq.version);
        return httpParams;
    }
}
