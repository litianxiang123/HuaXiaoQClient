package com.hxq.huaxiaoqclient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.adapter.ShowMoreAdapter;
import com.hxq.huaxiaoqclient.base.BaseActivity;
import com.hxq.huaxiaoqclient.common.ConstantsHxq;
import com.hxq.huaxiaoqclient.model.HomeData;
import com.hxq.huaxiaoqclient.model.HomeVo;
import com.hxq.huaxiaoqclient.model.SeeProductCacheVo;
import com.hxq.huaxiaoqclient.utils.AlibcUtil;
import com.hxq.huaxiaoqclient.utils.FileUtil;
import com.hxq.huaxiaoqclient.utils.ParamsUtil;
import com.hxq.huaxiaoqclient.utils.StringUtils;
import com.hxq.huaxiaoqclient.utils.ToastUtls;
import com.hxq.huaxiaoqclient.widget.SearchWidget;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.VolleyErrorHelper;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class MoreProductsActivity extends BaseActivity implements HttpLoader.HttpListener,View.OnClickListener,PullToRefreshBase.OnRefreshListener2,AlibcLoginCallback{

    private HttpLoader httpLoader;
    private int pageIndex = 1;
    private boolean isHasMore = true;
    private PullToRefreshListView page_tab_listview;
    private List<HomeData> homeDatas = new ArrayList<>();
    private ShowMoreAdapter mAdapter;
    private boolean isSearch;
    private String keyWord;
    private SearchWidget search_goods;
    private RelativeLayout titleBack;
    private String dataType;
    private AlibcUtil alibcUtil;
    private int pos;

    @Override
    protected int initContentView() {
        return R.layout.activity_more;
    }

    @Override
    protected void initView() {
        httpLoader = HttpLoader.getInstance(this);
        titleBack = (RelativeLayout) findViewById(R.id.title_back);
        alibcUtil = new AlibcUtil(this);
    }

    @Override
    protected void initListener() {
        search_goods = (SearchWidget) findViewById(R.id.search_goods);
        page_tab_listview = (PullToRefreshListView) findViewById(R.id.page_tab_listview);
        titleBack.setOnClickListener(this);
        page_tab_listview.setOnRefreshListener(this);
        search_goods.setOnSearchLinister(new SearchWidget.OnSearchLinister() {
            @Override
            public void onSearch(String text) {
                isSearch = true;
                homeDatas.clear();
                pageIndex = 1;
                getKeyWord(text);
            }

            @Override
            public void onNoContent() {
                ToastUtls.showToast(MoreProductsActivity.this, "请输入搜索内容哟", Toast.LENGTH_SHORT);
            }
        });
        page_tab_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                aliHelper(position);
                readFormLoacl(MoreProductsActivity.this,homeDatas.get(position-1));

            }
        });
    }

    private void aliHelper(int position){
        if (alibcUtil.getIsLogin()) {
            loadAliLink(homeDatas.get(position - 1));
        }else {
            alibcUtil.setLoginCallback(this).login();
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
    @Override
    protected void initData() {
        Intent intent = getIntent();
        keyWord = intent.getStringExtra("keyWord");
        dataType = intent.getStringExtra("dataType");
        if (!StringUtils.isEmpty(keyWord)){
            search_goods.setText(keyWord);
        }
        getDataType();

    }
    private void getMore(){
        showProgressDialog();
        httpLoader.post(ConstantsHxq.getMoreUrl, ParamsUtil.getMoreByPageIndex(pageIndex), HomeVo.class,ConstantsHxq.getMoreCode,this).setTag(this);
    }

    private void getKeyWord(String searchWord){
        showProgressDialog();
        httpLoader.post(ConstantsHxq.getSearchByWordUrl,ParamsUtil.getGoodsBySearch(searchWord),HomeVo.class,ConstantsHxq.getSearchByWordCode,this).setTag(this);
    }
    private void getCarefullyChosen(){
        showProgressDialog();
        httpLoader.post(ConstantsHxq.getCarefullyUrl,ParamsUtil.getMoreByPageIndex(pageIndex),HomeVo.class,ConstantsHxq.getCarefullyCode,this).setTag(this);
    }
    //加载淘宝连接
    private void loadAliLink(HomeData vo) {
        if (!TextUtils.isEmpty(vo.getLinkUrl())) {
            new AlibcUtil(this).setUrl(vo.getLinkUrl()).setOpenNaive().showWebDetail1();
        }
    }
    private void getDataType(){
        if (TextUtils.isEmpty(keyWord)){
            getMore();
        }else {
            if (!StringUtils.isEmpty(dataType)&&dataType.equals("0")){
                getCarefullyChosen();
            }else {
                isSearch = true;
                getKeyWord(keyWord);
            }
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
                if (requestCode == ConstantsHxq.getMoreCode||requestCode == ConstantsHxq.getSearchByWordCode||requestCode == ConstantsHxq.getCarefullyCode){
                    dismissProgressDialog();
                    page_tab_listview.setMode(PullToRefreshBase.Mode.BOTH);
                    proComPleteRefresh();
                    listData(response);
                }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
        dismissProgressDialog();
        ToastUtls.showToast(this, VolleyErrorHelper.getMessage(error, this), Toast.LENGTH_LONG);
        proComPleteRefresh();
    }
    //停止刷新加载
    public void proComPleteRefresh() {
        page_tab_listview.postDelayed(new Runnable() {
            @Override
            public void run() {
                page_tab_listview.onRefreshComplete();
            }
        }, 1);
    }
    //列表数据
    private void listData(IResponse response) {
        if (response instanceof HomeVo) {
            HomeVo baseResponse = (HomeVo) response;
            if (baseResponse.getRes_code() == 1) {
                List<HomeData> list = baseResponse.getResult();
                if (list.size() == 0 && pageIndex > 1) {
                    isHasMore = false;
                } else {
                    if (pageIndex == 1) {
                        homeDatas.clear();
                    }
                    if (list.size() < 20) {
                        isHasMore = false;
                    }

                    homeDatas.addAll(baseResponse.getResult());

                    if (mAdapter == null) {
                        mAdapter = new ShowMoreAdapter(this,homeDatas);
                        page_tab_listview.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                }


            } else {
                if (pageIndex == 1) {
                    homeDatas.clear();
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex = 1;
        isHasMore = true;
        if (TextUtils.isEmpty(keyWord)){
            getMore();
        }else {
            getDataType();
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (!isHasMore) {
            proComPleteRefresh();
            return;
        } else {
            pageIndex++;
            if (TextUtils.isEmpty(keyWord)){
                getMore();
            }else {
                getDataType();
            }
        }
    }

    @Override
    protected void onStop() {
        httpLoader.cancelRequest(this);
        super.onStop();
    }

    @Override
    public void onSuccess() {
        aliHelper(pos);
    }

    @Override
    public void onFailure(int i, String s) {

    }
}
