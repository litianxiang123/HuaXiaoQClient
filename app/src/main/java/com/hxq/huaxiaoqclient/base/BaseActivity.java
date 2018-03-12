package com.hxq.huaxiaoqclient.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentCallbacks;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hxq.huaxiaoqclient.AppManager;
import com.hxq.huaxiaoqclient.utils.PermissionUtils;
import com.hxq.huaxiaoqclient.utils.ToastUtls;

import org.senydevpkg.utils.ALog;




public abstract class BaseActivity extends Activity implements ComponentCallbacks {

    private ProgressDialog mProgressDialog;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected int mHeight;
    private AlertDialog alertDialog;
    private AppManager appManager;
    private PermissionHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //设置窗口背景不透明
        getWindow().setBackgroundDrawable(null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏.
        //StatusBarUtils.initStatusBar(this, R.color.trans);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在加载数据....");
        setContentView(initContentView());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        //是否可以签到
//        signRule();
        initView();
        initListener();
        initData();
        //极光相关
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ALog.d("namename:"+this.getClass());

        //订阅接收消息,子类只要重写onEvent就能收到
       // JMessageClient.registerEventReceiver(this);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 显示加载数据的dialog
     */
    protected void showProgressDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    protected void setProgressDialogCancel(){
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setCancelable(false);
        }
    }

    protected  void setmProgressDialogMessage(String message){
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
        }
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        if (alertDialog!=null){
            alertDialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * 请求权限
     *
     * @param permissions 权限列表
     * @param handler     回调
     */
    protected void requestPermission(String[] permissions, PermissionHandler handler) {
       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }*/
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            handler.onGranted();
        } else {
            mHandler = handler;
            ActivityCompat.requestPermissions(this, permissions, 001);
        }
    }


    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mHandler == null) return;

//        if (PermissionUtils.getTargetSdkVersion(this) < 23 && !PermissionUtils.hasSelfPermissions(this, permissions)) {
//            mHandler.onDenied();
//            return;
//        }

        if (PermissionUtils.verifyPermissions(grantResults)) {
            mHandler.onGranted();
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                if (!mHandler.onNeverAsk()) {
                    ToastUtls.showToast(this, "权限已被拒绝,请在设置-应用-权限中打开", Toast.LENGTH_SHORT);
                }
                mHandler.onDenied();

            } else {
                mHandler.onDenied();
            }
        }
    }


    /**
     * 权限回调接口
     */
    public abstract class PermissionHandler {
        /**
         * 权限通过
         */
        public abstract void onGranted();

        /**
         * 权限拒绝
         */
        public void onDenied() {
        }

        /**
         * 不再询问
         *
         * @return 如果要覆盖原有提示则返回true
         */
        public boolean onNeverAsk() {
            return false;
        }
    }
    /**
     * 返回界面的布局
     * @return layout id
     */
    protected abstract int initContentView();

    /**
     * 初始化控件，findViewById()
     */
    protected abstract void initView();

    /**
     * 给控件设置监听器，初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据（网络加载、本地加载数据等操作）
     */
    protected abstract void initData();

}

