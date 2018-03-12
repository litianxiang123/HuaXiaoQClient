package com.hxq.huaxiaoqclient.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class SeeProductCacheVo implements Serializable{
    private List<HomeData> cacheList;

    public List<HomeData> getCacheList() {
        return cacheList;
    }

    public void setCacheList(List<HomeData> cacheList) {
        this.cacheList = cacheList;
    }
}
