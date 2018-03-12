package com.hxq.huaxiaoqclient.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxq.huaxiaoqclient.R;


/**
 * @author mayong
 * @date 创建时间 2017/6/8
 * @description 首页搜索view
 */
public class SearchWidget extends RelativeLayout implements View.OnClickListener {

    private EditText editText;
    private ImageView ivClear;
    private View content;
    private boolean isHide;
    private RelativeLayout rlContainer;
    private LinearLayout cont;
    editChange change;

    public SearchWidget(Context context) {
        super(context);
        init(context);
    }

    public SearchWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setChange(editChange change) {
        this.change = change;
    }

    public interface editChange {
        void onTextChanged(String txt);
    }

    private void init(final Context context) {
        content = View.inflate(context, R.layout.layout_search_frame, this);
        cont = (LinearLayout) content.findViewById(R.id.cont);
        editText = (EditText) content.findViewById(R.id.et_search);
        rlContainer = (RelativeLayout) findViewById(R.id.rl_search_widget);
        ivClear = (ImageView) content.findViewById(R.id.iv_homepage_cleartext);
        ivClear.setOnClickListener(this);
        ivClear.setVisibility(GONE);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (change != null) {
                    change.onTextChanged(editText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    ivClear.setVisibility(GONE);
                } else {
                    ivClear.setVisibility(VISIBLE);
                }
            }
        });

        editText
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void search() {

        String searchContext = editText.getText().toString().trim();
        if (TextUtils.isEmpty(searchContext)) {
            if (onSearchLinister != null) {
                onSearchLinister.onNoContent();
            }
        } else {
            // 调用搜索的API方法
            if (onSearchLinister != null) {
                onSearchLinister.onSearch(getText());
            }
        }
    }

    /**
     * 设置edittext是否可用
     *
     * @param enabled
     */
    public void setEditEnabled(boolean enabled) {
        editText.setFocusable(enabled);
    }

    public String getText() {
        String text = editText.getText().toString().trim();
        return text == null ? "" : text;
    }
    public void setText(String text) {
       editText.setText(text);
    }
    public void hideClearButton(boolean isHide) {
        this.isHide = isHide;
        if (isHide) {
            ivClear.setVisibility(GONE);
        } else {
            ivClear.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置灰色背景
     */
    public void setBackGroundGray() {
        rlContainer.setBackgroundResource(R.drawable.shp_search_frame_gray);
//        rlContainer.setBackground(getResources().getDrawable(R.drawable.shp_search_frame_gray));
    }

    /**
     * 设置白色背景
     */
    public void setBackgroundWhite() {
        rlContainer.setBackgroundResource(R.drawable.shp_search_frame);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_homepage_cleartext:
                editText.setText("");
                break;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isHide) {
            return true;
        } else {
            return false;
        }
    }

    private OnSearchLinister onSearchLinister;

    public void setOnSearchLinister(OnSearchLinister onSearchLinister) {
        this.onSearchLinister = onSearchLinister;
    }

    public void setHint(String hint) {
        editText.setHint(hint);
    }

    public void setEditText(String text) {
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
        }
    }

    public static interface OnSearchLinister {
        void onSearch(String text);

        void onNoContent();
    }

    //设置输入框中的字体
    public void setHind(String name) {
        editText.setHint(name);
    }

    //设置输入框中字体和搜索按钮的位置
    public void setPosition() {
        cont.setGravity(Gravity.CENTER);
    }

    /**
     * 输入框字体大小
     *
     * @param textSize 单位 dp
     */
    public void setEditTextSize(int textSize) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        editText.setTextSize(textSize);
    }

    /**
     * 弹出软键盘
     */
    public void showSoftBoard() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * 隐藏键盘
     */
    public void hidSoftBoard() {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
