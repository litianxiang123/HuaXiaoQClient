package com.hxq.huaxiaoqclient.model;

import org.senydevpkg.net.resp.IResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class HomeVo implements IResponse,Serializable {
    public int res_code;
    private String message;
    private List<HomeData> result;

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeData> getResult() {
        return result;
    }

    public void setResult(List<HomeData> result) {
        this.result = result;
    }
}
