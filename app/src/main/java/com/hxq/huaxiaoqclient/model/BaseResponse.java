package com.hxq.huaxiaoqclient.model;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class BaseResponse<T> implements IResponse {
    public int res_code;
    private String message;
    public T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
