package com.hxq.huaxiaoqclient.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.adapter.ShowMoreAdapter;
import com.hxq.huaxiaoqclient.base.BaseActivity;
import com.hxq.huaxiaoqclient.model.HomeData;
import com.hxq.huaxiaoqclient.model.SeeProductCacheVo;
import com.hxq.huaxiaoqclient.utils.FileUtil;
import com.hxq.huaxiaoqclient.utils.JumpUtil;
import com.hxq.huaxiaoqclient.utils.ToastUtls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class BrowsingHostoryActivity extends BaseActivity implements View.OnClickListener{
    private List<HomeData> homeDatas = new ArrayList<>();
    private RelativeLayout titleItemBack;
    private TextView rightContent;
    private ListView browsing_hostory_lv;
    private ShowMoreAdapter mAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_browsing_hostory;
    }

    @Override
    protected void initView() {
        browsing_hostory_lv = (ListView) findViewById(R.id.browsing_hostory_lv);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("浏览记录");
        rightContent = (TextView) findViewById(R.id.right_content);
        rightContent.setText("清除记录");
        titleItemBack = (RelativeLayout) findViewById(R.id.title_item_back);
        SeeProductCacheVo bean1 = new FileUtil<SeeProductCacheVo>().readObjectFromLocal(this, "hxCache.out");
        if (null!= bean1){
            if (bean1.getCacheList().size()<=20) {
                homeDatas = bean1.getCacheList();
            }else {
                homeDatas = bean1.getCacheList().subList(0,20);
            }
            mAdapter = new ShowMoreAdapter(this, homeDatas);
            browsing_hostory_lv.setAdapter(mAdapter);
        }else {
            ToastUtls.showToast(this,"暂无浏览记录", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void initListener() {
        rightContent.setOnClickListener(this);
        titleItemBack.setOnClickListener(this);
        browsing_hostory_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JumpUtil.startAD(BrowsingHostoryActivity.this,homeDatas.get(position));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_item_back:
                finish();
                break;
            case R.id.right_content:

                if (new FileUtil<SeeProductCacheVo>().writeObjectIntoLocal(BrowsingHostoryActivity.this, "hxCache.out", null)) {
                    browsing_hostory_lv.setAdapter(null);
                }
                /*File file = new File("hxCache.out");
                if (file.isFile()&&file.exists()){
                    file.delete();
                }*/
                break;
        }
    }
}
