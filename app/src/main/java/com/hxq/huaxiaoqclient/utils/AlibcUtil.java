package com.hxq.huaxiaoqclient.utils;

import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.hxq.huaxiaoqclient.interfaces.DemoTradeCallback;
import com.hxq.huaxiaoqclient.interfaces.onDialogClickListener;

import org.senydevpkg.utils.ALog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuanzhenyun on 2017/6/23.
 */

public class AlibcUtil {

    Activity mActivity;
    //设置淘客商品
    boolean isTaoke;
    String taokeID;
    private AlibcTaokeParams alibcTaokeParams = null;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数


    //商品ID
    String goodsID;
    AlibcBasePage alibcBasePage = null;

    //商品连接
    String url;
    AlibcPage alibcPage = null;


    public static final int ORDER_TYPE_ALL = 0;   //全部订单
    public static final int ORDER_TYPE_WAITING_PAY = 1;   //待付款
    public static final int ORDER_TYPE_WAITING_DELIVER = 2; //待发货
    public static final int ORDER_TYPE_WAITING_RECEIPT = 3; //待收货
    public static final int ORDER_TYPE_WAITING_EVALUATE = 4; //待评价


    //订单页面参数，仅在H5方式下有效
    private int orderType = 0;


    //web 代理事件接口
    WebViewClient webViewClient = null;
    WebChromeClient chromeClient = null;


    //登录回调接口
    AlibcLoginCallback loginCallback = null;

    //退出回调接口
    LogoutCallback logoutCallback;


    public AlibcUtil(Activity activity) {
        mActivity = activity;

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        alibcShowParams = new AlibcShowParams(OpenType.H5, false);
    }

    public AlibcUtil setWebViewClient(WebViewClient webViewClient) {
        this.webViewClient = webViewClient;
        return this;
    }

    public AlibcUtil setChromeClient(WebChromeClient chromeClient) {
        this.chromeClient = chromeClient;
        return this;
    }

    public AlibcUtil setUrl(String url) {
        this.url = url;
        alibcPage = new AlibcPage(url);
        return this;
    }

    //设置App打开商品
    public AlibcUtil setOpenNaive() {
        alibcShowParams = new AlibcShowParams(OpenType.Native, false);
        return this;
    }

    //设置H5打开商品
    public AlibcUtil setOpenH5() {
        alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        return this;
    }


    /**
     * 设置是否为淘客商品，如果为淘客商品需要taokeId , 不是淘客商品可以为空
     *
     * @param taoke
     * @return
     */
    public AlibcUtil setIsTaoKe(boolean taoke, String taokeID) {
        isTaoke = taoke;
        this.taokeID = taokeID;

        if (isTaoke) {
            alibcTaokeParams = new AlibcTaokeParams(this.taokeID, "", "");
        } else {
            alibcTaokeParams = null;
        }
        return this;
    }

    //设置商品ID
    public AlibcUtil setGoodsID(String goodsID) {
        this.goodsID = goodsID;
        alibcBasePage = new AlibcDetailPage(goodsID);
        return this;
    }

    public void showWebDetail1() {
        if (alibcBasePage != null) {

            AlibcTrade.show(mActivity,  alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback(mActivity));
        } else if (alibcPage != null) {
            AlibcTrade.show(mActivity, alibcPage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback(mActivity));
        } else {
            Toast.makeText(mActivity, "没有设置商品id或url", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * web代理打开淘宝详情，
     *
     * @param webView
     */
    public void showWebDetail(WebView webView) {
        setOpenH5();
        if (alibcBasePage != null) {

            AlibcTrade.show(mActivity, webView, webViewClient, chromeClient, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback(mActivity));
        } else if (alibcPage != null) {
            AlibcTrade.show(mActivity, webView, webViewClient, chromeClient, alibcPage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback(mActivity));
        } else {
            Toast.makeText(mActivity, "没有设置商品id或url", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * H5打开淘宝页面详情
     */
    public void show() {

        if (alibcBasePage != null) {
            AlibcTrade.show(mActivity, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback(mActivity));
        } else if (alibcPage == null) {
            AlibcTrade.show(mActivity, alibcPage, alibcShowParams, null, exParams, new DemoTradeCallback(mActivity));
        } else {
            Toast.makeText(mActivity, "没有设置商品id或url", Toast.LENGTH_LONG).show();
        }


    }


    //设置登录接口
    public AlibcUtil setLoginCallback(AlibcLoginCallback loginCallback) {
        this.loginCallback = loginCallback;
        return this;
    }

    /**
     * 登录
     */
    public void login() {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(mActivity, loginCallback);
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean getIsLogin() {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        return alibcLogin.isLogin();
    }


    //设置退出登录接口
    public AlibcUtil setLogoutCallback(LogoutCallback logoutCallback) {
        this.logoutCallback = logoutCallback;
        return this;
    }

    /**
     * 退出登录
     */
    public void logout(Activity activity) {
        ALog.d("elwekwlkrw");
        new DialogUtil(activity).setTitle("确定退出吗？").setConfirm("确定").setCancel("取消").setClickListener(new onDialogClickListener() {
            @Override
            public void onCancleClick() {

            }

            @Override
            public void onConfirmClick() {
                AlibcLogin alibcLogin = AlibcLogin.getInstance();
                alibcLogin.logout(mActivity, logoutCallback);
            }
        }).showDialog();

    }

    /**
     * 设置打开订单列表参数，必须使用上面定义的几个常量
     *
     * @param orderType
     * @return
     */
    public AlibcUtil setOrderType(int orderType) {
        this.orderType = orderType;
        return this;
    }

    /**
     * 分域显示我的订单，或者全部显示我的订单
     */
    public void showOrder() {
        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, true);
        AlibcTrade.show(mActivity, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback(mActivity));
    }


    /**
     * 显示我的购物车
     */
    public void showCart() {
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        AlibcTrade.show(mActivity, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback(mActivity));
    }
}




