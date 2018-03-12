package com.hxq.huaxiaoqclient.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.hxq.huaxiaoqclient.common.ConstantsHxq;
import com.hxq.huaxiaoqclient.model.UpdateVo;
import com.hxq.huaxiaoqclient.ui.activity.UpdateVersionActivity;
import com.hxq.huaxiaoqclient.utils.ParamsUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

/**
 * Created by yuanzhenyun on 2017/7/4.
 */

public class UploadService extends IntentService implements HttpLoader.HttpListener {

    private HttpLoader httpLoader;
    private int versionAppCode;
    private AlertDialog.Builder mDialog;
    AlertDialog.Builder builder;
    private Context mContext;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadService(String name) {
        super(name);
    }


    public UploadService() {
        super("UploadService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mContext = this;
        httpLoader = HttpLoader.getInstance(this);
        checkVersion();
    }

    private void checkVersion() {
        httpLoader.post(ConstantsHxq.updateUrl, ParamsUtil.getAppSortParams(ConstantsHxq.appSort), UpdateVo.class, ConstantsHxq.updateCode, this).setTag(this);
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        if (requestCode == ConstantsHxq.updateCode && response instanceof UpdateVo) {
            UpdateVo updateInfo = (UpdateVo) response;
            if (updateInfo.getRes_code() == 1) {
                int isForce = updateInfo.getResult().getIsForce();//是否需要强制更新
                String downUrl = updateInfo.getResult().getDownUrl();//apk下载地址
                String updateinfo = updateInfo.getResult().getUpdateInfo();//apk更新详情
                int versionCode = updateInfo.getResult().getVersionCode();
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = null;
                try {
                    packageInfo = pm.getPackageInfo(getPackageName(), 0);
                    // 版本号
                    versionAppCode = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (versionAppCode < versionCode) {

                    Bundle bundle = new Bundle();
                    bundle.putString(UpdateVersionActivity.VERSION_URL, downUrl);
                    bundle.putString(UpdateVersionActivity.VERSION_TITLE, updateinfo);

                    if (isForce == 1 && !TextUtils.isEmpty(updateinfo)) {
                        //强制更新
                        bundle.putBoolean(UpdateVersionActivity.VERSION_UMST, true);
                    } else {
                        //非强制更新
                        //正常升级
                        bundle.putBoolean(UpdateVersionActivity.VERSION_UMST, false);
                    }

                    Intent intent = new Intent(mContext, UpdateVersionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }


}
