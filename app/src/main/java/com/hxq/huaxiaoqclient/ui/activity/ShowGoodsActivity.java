package com.hxq.huaxiaoqclient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.base.BaseActivity;
import com.hxq.huaxiaoqclient.model.HomeData;
import com.hxq.huaxiaoqclient.model.SeeProductCacheVo;
import com.hxq.huaxiaoqclient.utils.AlibcUtil;
import com.hxq.huaxiaoqclient.utils.FileUtil;

import org.senydevpkg.utils.ALog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ShowGoodsActivity extends BaseActivity {

    private WebView webView;
    private TextView titleTv;
    private myWebChromeClient chromeClient;
    private MyWebviewClient viewClient;
    private int errorWebCode = 20000;
    private LinearLayout no_net_connect_view;
    private LinearLayout error_net_connect_view;
    private String url;
    private HomeData vo;

    @Override
    protected int initContentView() {
        return R.layout.activity_showgoods;
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.webView);
        titleTv = (TextView) findViewById(R.id.title_tv);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        no_net_connect_view = (LinearLayout) findViewById(R.id.no_net_connect_view);
        error_net_connect_view = (LinearLayout) findViewById(R.id.error_net_connect_view);
        webSetting();
        chromeClient = new myWebChromeClient();
        webView.setWebChromeClient(chromeClient);

        viewClient = new MyWebviewClient();
        webView.setWebViewClient(viewClient);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vo = (HomeData) bundle.getSerializable("vo");
            loadAliLink();
            readFormLoacl(this,vo);
        }

    }

    //加载淘宝连接
    private void loadAliLink() {
        if (!TextUtils.isEmpty(vo.getLinkUrl())) {
            new AlibcUtil(this).setUrl(vo.getLinkUrl()).setOpenNaive().showWebDetail1();
        }
    }
    public void readFormLoacl(Context context, HomeData cache) {
        SeeProductCacheVo bean1 = new FileUtil<SeeProductCacheVo>().readObjectFromLocal(context, "hxCache.out");
        if (null!= bean1){
            for (HomeData homeData:bean1.getCacheList()){
                if (cache.getBusinessId() == homeData.getBusinessId()){
                    return;
                }
            }
            bean1.getCacheList().add(0,cache);
            writeToLocal(context,bean1);
        }else {
            SeeProductCacheVo cacheVo = new SeeProductCacheVo();
            List<HomeData> homeData = new ArrayList<>();
            homeData.add(cache);
            cacheVo.setCacheList(homeData);
            writeToLocal(this,cacheVo);
        }
    }
    public void writeToLocal(Context context, SeeProductCacheVo bean) {
        File file = new File("hxCache.out");
        if (file.exists()) {
            file.delete();
        }

        if (new FileUtil<SeeProductCacheVo>().writeObjectIntoLocal(context, "hxCache.out", bean)) {
            ALog.d("cache_sussess");
        }
    }
    private void webSetting() {
        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(true);
        s.setAppCacheEnabled(false);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);
        s.setUseWideViewPort(true);
        s.setAllowContentAccess(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptEnabled(true);
        s.setSupportZoom(false);


        s.setGeolocationEnabled(true);
        s.setDomStorageEnabled(true);
    }

    class myWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleTv.setText(title);
        }
    }

    class MyWebviewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (errorWebCode == -2) {
                webView.setVisibility(View.INVISIBLE);
                no_net_connect_view.setVisibility(VISIBLE);
                error_net_connect_view.setVisibility(View.GONE);


            } else if (errorWebCode == 20000) {

                webView.setVisibility(VISIBLE);
                no_net_connect_view.setVisibility(View.INVISIBLE);
                error_net_connect_view.setVisibility(View.INVISIBLE);
            } else {
                no_net_connect_view.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                error_net_connect_view.setVisibility(VISIBLE);
            }
            errorWebCode = 20000;
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            errorWebCode = errorCode;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            url = request.getUrl().toString();

            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            ALog.d("url:view" + url);
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
            }
            return true;


        }
    }
}
