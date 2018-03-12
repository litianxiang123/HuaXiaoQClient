package com.hxq.huaxiaoqclient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;
import com.android.volley.VolleyError;
import com.hxq.huaxiaoqclient.adapter.SlidingLeftAdapter;
import com.hxq.huaxiaoqclient.common.ConstantsHxq;
import com.hxq.huaxiaoqclient.glide.GlideUtils;
import com.hxq.huaxiaoqclient.interfaces.onDialogClickListener;
import com.hxq.huaxiaoqclient.model.BaseResponse;
import com.hxq.huaxiaoqclient.model.KeyWord;
import com.hxq.huaxiaoqclient.service.UploadService;
import com.hxq.huaxiaoqclient.ui.activity.MoreProductsActivity;
import com.hxq.huaxiaoqclient.utils.AlibcUtil;
import com.hxq.huaxiaoqclient.utils.CommonUtils;
import com.hxq.huaxiaoqclient.utils.DialogUtil;
import com.hxq.huaxiaoqclient.utils.ImageCatchUtil;
import com.hxq.huaxiaoqclient.utils.JumpUtil;
import com.hxq.huaxiaoqclient.utils.ParamsUtil;
import com.hxq.huaxiaoqclient.utils.StatusBarUtils;
import com.hxq.huaxiaoqclient.utils.ToastUtls;
import com.hxq.huaxiaoqclient.utils.ViewBg;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.net.VolleyErrorHelper;
import org.senydevpkg.net.resp.IResponse;
import org.senydevpkg.utils.ALog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SlidingFragmentActivity implements HttpLoader.HttpListener, View.OnClickListener,AlibcLoginCallback,LogoutCallback {
    private static final String	LEFT_MUNE_TAG	= "LEFT_MUNE_TAG";
    private static final String	MAIN_MUNE_TAG	= "MAIN_MUNE_TAG";
    private ImageView bounds;
    private ImageView cart;
    private EditText search;
    private HttpLoader httpLoader;
    private TextView key_word_one;
    private TextView key_word_two;
    private TextView key_word_three;
    private TextView more;
    List<TextView> textList = new ArrayList<>();
    List<String> tvs = new ArrayList<>();
    List<Integer> ivs = new ArrayList<>();
    private ListView leftLv;
    private RelativeLayout search_contains;
    private LinearLayout searchAndPic;
    private int tranlation;
    private AlibcUtil alibcUtil;
    private int pos;
    private ImageView icon;
    private TextView username;
    private TextView login;
    private ImageView carefully_chosen;
    private LinearLayout searchAndet;
    private EditText search_et;
    private ImageView ser_icon;
    private RelativeLayout fl_main_menu;
    private int screenHeight = 0;
    private int keyHeight = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //设置窗口背景不透明
        getWindow().setBackgroundDrawable(null);
        StatusBarUtils.initStatusBar(this, R.color.transla);
        initView();// 初始化界面
        initData();
        initLisenter();
       // initMenu();

       // initData();// 初始化数据

    }

    private void initLisenter() {
        key_word_one.setOnClickListener(this);
        key_word_two.setOnClickListener(this);
        key_word_three.setOnClickListener(this);
        search.setOnClickListener(this);
        bounds.setOnClickListener(this);
        more.setOnClickListener(this);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    if (TextUtils.isEmpty(search.getText().toString().trim())){
                        ToastUtls.showToast(MainActivity.this, "请输入搜索内容哟", Toast.LENGTH_SHORT);
                    }else {
                        ALog.d("search");
                        Intent intent = new Intent(MainActivity.this,MoreProductsActivity.class);
                        intent.putExtra("keyWord",search.getText().toString().trim());
                        startActivity(intent);
                        search.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
        leftLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    toggle();
                }else if (position == 4){
                    new DialogUtil(MainActivity.this).setTitle("清除缓存吗?").setCancel("取消").setConfirm("确定").setClickListener(new onDialogClickListener() {
                        @Override
                        public void onCancleClick() {
                        }

                        @Override
                        public void onConfirmClick() {
                            ImageCatchUtil.get().clearImageAllCache();
                            ToastUtls.showToast(MainActivity.this, "清除成功", Toast.LENGTH_SHORT);
                        }
                    }).showDialog();
                }else {
                    pos = position;
                   jumpHelper(position);
                }
            }
        });
    }
    private void jumpHelper(int position){
        if (alibcUtil.getIsLogin()) {
            JumpUtil.jump(MainActivity.this,alibcUtil,position);
        } else {
            alibcUtil.setLoginCallback(this).login();
        }
    }
    private void initData() {
        tvs.add("搜索");
        tvs.add("我的订单");
        tvs.add("我的收藏");
        tvs.add("浏览记录");
        tvs.add("清除缓存");
        ivs.add(R.mipmap.ic_search_mirror);
        ivs.add(R.mipmap.order_gray);
        ivs.add(R.mipmap.collection_gray);
        ivs.add(R.mipmap.history_gray);
        ivs.add(R.mipmap.clean_cache_gray);
        SlidingLeftAdapter mAdapter = new SlidingLeftAdapter(this,tvs,ivs);
        leftLv.setAdapter(mAdapter);
       getDefautWord();
    }

    private void initView() {
        startService(new Intent(this, UploadService.class));
        alibcUtil = new AlibcUtil(this);
        alibcUtil.setLoginCallback(this);
        alibcUtil.setLogoutCallback(this);
        // 设置主界面
        setContentView(R.layout.fragment_content_tag);

        // 设置左侧菜单界面
        setBehindContentView(R.layout.fragment_left);

        // 设置滑动模式
        SlidingMenu sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT);// 只设置左侧可以滑动

        // 设置滑动位置为全屏
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        // 设置主界面左侧滑动后剩余的空间位置
        sm.setBehindOffset(CommonUtils.getScreenWidth(this)*2/5);// 设置主界面剩余的位置
        icon = (ImageView) findViewById(R.id.head_icon);
        search_contains = (RelativeLayout) findViewById(R.id.search_contains);
        httpLoader = HttpLoader.getInstance(this);
        bounds = (ImageView) findViewById(R.id.bounds);
        cart = (ImageView) findViewById(R.id.cart);
        search = (EditText) findViewById(R.id.search);
        key_word_one = (TextView) findViewById(R.id.key_word_one);
        key_word_two = (TextView) findViewById(R.id.key_word_two);
        key_word_three = (TextView) findViewById(R.id.key_word_three);
        searchAndPic = (LinearLayout) findViewById(R.id.searchAndPic);
        fl_main_menu = (RelativeLayout) findViewById(R.id.fl_main_menu);
        ser_icon = (ImageView) findViewById(R.id.ser_icon);
        searchAndet = (LinearLayout) findViewById(R.id.searchAndet);
        search_et = (EditText) findViewById(R.id.search_et);
        login = (TextView) findViewById(R.id.login);
        username = (TextView) findViewById(R.id.username);
        carefully_chosen = (ImageView) findViewById(R.id.carefully_chosen);
        tranlation = (search_contains.getWidth() - searchAndPic.getWidth())/2;
        leftLv = (ListView) findViewById(R.id.left_lv);
        textList.add(key_word_one);
        textList.add(key_word_two);
        textList.add(key_word_three);
        more = (TextView) findViewById(R.id.more);
        ViewBg viewBg = new ViewBg();
        viewBg.setBg(this,search_contains,"#FFFFFF",6);
        search_contains.setOnClickListener(this);
        search.setCursorVisible(false);
        cart.setOnClickListener(this);
        icon.setOnClickListener(this);
        login.setOnClickListener(this);
        username.setOnClickListener(this);
        carefully_chosen.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (alibcUtil.getIsLogin()){
            login.setText("退出登录");
            username.setText(AlibcLogin.getInstance().getSession().nick);
            GlideUtils.getInstance().loadImageCircle(MainActivity.this,icon,AlibcLogin.getInstance().getSession().avatarUrl);
        }else {
            username.setText("登录");
            login.setText("登录");
            GlideUtils.getInstance().loadImageCircle(MainActivity.this,icon,R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bounds:
                showLeftMenu();
                break;
            case R.id.cart:
                JumpUtil.jump(MainActivity.this,alibcUtil,2);
                break;
            case R.id.more:
                Intent intent = new Intent(MainActivity.this, MoreProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                search.setCursorVisible(true);
                /*Animation translate = AnimationUtils.loadAnimation(this, R.anim.tra);

               *//* TranslateAnimation translate = new TranslateAnimation(

                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,-0.8f,

                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                translate.setDuration(500);*//*
                translate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ALog.d("animation2");
                        searchAndet.setVisibility(View.VISIBLE);
                        search.setVisibility(View.INVISIBLE);
                        ser_icon.setVisibility(View.INVISIBLE);
                        search_et.setVisibility(View.VISIBLE);
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.showSoftInput(search_et,InputMethodManager.SHOW_FORCED);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                translate.setFillAfter(true);
                searchAndPic.startAnimation(translate);*/
                //showSoftInputFromWindow(MainActivity.this,search_et);
                //left();
                break;
            case R.id.head_icon:
                ALog.d("util:"+alibcUtil.getIsLogin());
                if (!alibcUtil.getIsLogin()){
                    alibcUtil.login();
                }
                break;
            case R.id.username:
                ALog.d("wlekwlew"+alibcUtil.getIsLogin());
                if (!alibcUtil.getIsLogin()){
                    alibcUtil.login();
                }
                break;
            case R.id.login:
                if (alibcUtil.getIsLogin()){
                    alibcUtil.logout(MainActivity.this);
                }else {
                    alibcUtil.login();
                }
                break;
            case R.id.carefully_chosen:
                Intent intent1 = new Intent(MainActivity.this,MoreProductsActivity.class);
                intent1.putExtra("dataType","0");
                startActivity(intent1);
                break;
        }
    }
    private void closeAnima(){
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.traright);
       /* TranslateAnimation translate = new TranslateAnimation(

                Animation.RELATIVE_TO_SELF, -0.8f, Animation.RELATIVE_TO_SELF,0,

                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);*/
        translate.setDuration(500);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                searchAndet.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
                ser_icon.setVisibility(View.VISIBLE);
                search_et.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_et.getWindowToken(), 0);;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ALog.d("animation2");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translate.setFillAfter(true);
        searchAndPic.startAnimation(translate);
    }
    public void showLeftMenu()
    {
        getSlidingMenu().showMenu();
    }
    private void getDefautWord(){
        httpLoader.get(ConstantsHxq.getDefaultKeyUrl, ParamsUtil.getDefaultParams(), KeyWord.class,ConstantsHxq.getDefaultKeyCode,this,false).setTag(this);
    }

    @Override
    public void onGetResponseSuccess(int requestCode, IResponse response) {
        switch (requestCode){
            case ConstantsHxq.getDefaultKeyCode:
                final BaseResponse<List<KeyWord>> baseResponse = (BaseResponse<List<KeyWord>>) response;
                if (baseResponse.getRes_code() == 1){
                    if (baseResponse.getResult()!=null){
                        for (int i =0;i<baseResponse.getResult().size();i++){
                            textList.get(i).setText(baseResponse.getResult().get(i).getKeyWord());
                            final int finalI = i;
                            textList.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(MainActivity.this,MoreProductsActivity.class);
                                    intent.putExtra("keyWord",baseResponse.getResult().get(finalI).getKeyWord());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
        }
    }

    @Override
    public void onGetResponseError(int requestCode, VolleyError error) {
            ToastUtls.showToast(this, VolleyErrorHelper.getMessage(error, this), Toast.LENGTH_LONG);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        CommonUtils.hideInputWhenTouchOtherView1(this, ev, search);
        search.setCursorVisible(false);
        //closeAnima();
        return super.dispatchTouchEvent(ev);

    }

    @Override
    protected void onStop() {
        httpLoader.cancelRequest(this);
        super.onStop();
    }

    @Override
    public void onSuccess() {
        if (alibcUtil.getIsLogin()) {
            username.setText(AlibcLogin.getInstance().getSession().nick);
            GlideUtils.getInstance().loadImageCircle(MainActivity.this,icon,AlibcLogin.getInstance().getSession().avatarUrl);
            login.setText("退出登录");
            JumpUtil.jump(MainActivity.this,alibcUtil, pos);
        }else {
            GlideUtils.getInstance().loadImageCircle(MainActivity.this,icon,R.mipmap.ic_launcher);
            username.setText("登录");
            login.setText("登录");
        }
    }

    @Override
    public void onFailure(int i, String s) {
        ALog.d("mainActivity"+s+i);
    }

}
