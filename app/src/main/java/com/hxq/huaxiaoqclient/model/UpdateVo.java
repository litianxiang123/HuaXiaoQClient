package com.hxq.huaxiaoqclient.model;

import org.senydevpkg.net.resp.IResponse;

/**
 * Created by Administrator on 2017/8/8.
 */

public class UpdateVo implements IResponse {

    /**
     * res_code : 1
     * message : Success
     * result : {"id":1,"appShort":"360FM","versionCode":1,"isForce":1,"updateInfo":"强制更新","downUrl":"http://download.gerenhao.com","addTime":1502173702000,"updateTime":1502173702000}
     */

    private int res_code;
    private String message;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * appShort : 360FM
         * versionCode : 1
         * isForce : 1
         * updateInfo : 强制更新
         * downUrl : http://download.gerenhao.com
         * addTime : 1502173702000
         * updateTime : 1502173702000
         */

        private int id;
        private String appShort;
        private int versionCode;
        private int isForce;
        private String updateInfo;
        private String downUrl;
        private long addTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppShort() {
            return appShort;
        }

        public void setAppShort(String appShort) {
            this.appShort = appShort;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public String getUpdateInfo() {
            return updateInfo;
        }

        public void setUpdateInfo(String updateInfo) {
            this.updateInfo = updateInfo;
        }

        public String getDownUrl() {
            return downUrl;
        }

        public void setDownUrl(String downUrl) {
            this.downUrl = downUrl;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
