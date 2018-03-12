package com.hxq.huaxiaoqclient.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.CountDownTimer;

import com.android.volley.VolleyError;
import com.hxq.huaxiaoqclient.MainActivity;
import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.base.BaseActivity;
import com.hxq.huaxiaoqclient.common.ConstantsHxq;
import com.hxq.huaxiaoqclient.model.BaseResponse;
import com.hxq.huaxiaoqclient.utils.ParamsUtil;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.resp.IResponse;

/**
 * Created by Administrator on 2017/9/6.
 */

public class SplashActivity extends BaseActivity implements HttpLoader.HttpListener{

    private HttpLoader httpLoader;

    @Override
    protected int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        httpLoader = HttpLoader.getInstance(this);
    }

    @Override
    protected void initListener() {
        requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionHandler() {
            @Override
            public void onGranted() {
                    registerReport(true);
                new MyCount(1000,1000).start();
            }

            @Override
            public void onDenied() {
                registerReport(false);
                new MyCount(1000,1000).start();
                super.onDenied();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void registerReport(boolean  isHas){
        if (isHas) {
            httpLoader.post(ConstantsHxq.getIdfaUrl, ParamsUtil.getRegisterReportParams(ConstantsHxq.AppId, ConstantsHxq.getDeviceCode()), BaseResponse.class, ConstantsHxq.getIdfaCode, this).setTag(this);
        }else {
            httpLoader.post(ConstantsHxq.getIdfaUrl, ParamsUtil.getRegisterReportParams(ConstantsHxq.AppId, ConstantsHxq.getIdCode()), BaseResponse.class, ConstantsHxq.getIdfaCode, this).setTag(this);

        }
        }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {

    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {

    }

    class MyCount extends CountDownTimer{
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.no_effect_in, R.anim.no_effect_out);
            finish();
        }
    }
}
