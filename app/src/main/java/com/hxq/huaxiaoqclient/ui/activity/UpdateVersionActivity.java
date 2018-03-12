package com.hxq.huaxiaoqclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.base.BaseActivity;
import com.hxq.huaxiaoqclient.interfaces.onDialogClickListener;
import com.hxq.huaxiaoqclient.service.UpdateApkService;
import com.hxq.huaxiaoqclient.utils.DialogUtil;


/*
 * 版本更新弹窗界面
 */
public class UpdateVersionActivity extends BaseActivity implements onDialogClickListener {

    private static final String TAG = "UpdateVersionActivity";
    private String android_link, android_title;
    private boolean mustUpdate;

    public static final String VERSION_URL = "version_url";
    public static final String VERSION_TITLE = "version_title";
    public static final String VERSION_UMST = "version_must";

    DialogUtil dialog;

    LinearLayout loading_layout;

    @Override
    protected int initContentView() {
        return R.layout.update_version;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            android_link = bundle.getString(UpdateVersionActivity.VERSION_URL);
            android_title = bundle.getString(UpdateVersionActivity.VERSION_TITLE);
            mustUpdate = bundle.getBoolean(UpdateVersionActivity.VERSION_UMST);
        }

        loading_layout = (LinearLayout) findViewById(R.id.loading_layout);
        loading_layout.setVisibility(View.GONE);

        dialog = new DialogUtil(this);
        if (!mustUpdate) {
            dialog.setCancel("暂不更新");
        } else {
//           dialog.setCancel("退出应用");
        }
        dialog.setConfirm("立即更新");
        dialog.setClickListener(this).setTitle(android_title);
        dialog.showDialog();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onCancleClick() {
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onConfirmClick() {
        //立即更新
        Bundle bundle = new Bundle();
        bundle.putString(VERSION_URL, android_link);
        bundle.putString(VERSION_TITLE, android_title);
        Intent intent = new Intent(this, UpdateApkService.class);
        intent.putExtras(bundle);
        startService(intent);

        if (mustUpdate) {
            loading_layout.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }
}
