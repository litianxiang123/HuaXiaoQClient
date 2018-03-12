package com.hxq.huaxiaoqclient.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/25.
 */

public class CommonUtils {
    /**
     * 获取屏幕的宽度
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }



    public static int getSreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    /**
     * dp转换px
     *
     * @param context
     * @param value
     * @return
     */
    public static int dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 弹出Toast
     *
     * @param context
     * @param str
     */
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
    /** 含有标题、内容、两个按钮的对话框 **/
    public static  void showAlertDialog(Context context, String title, String message,
                                        String positiveText,
                                        DialogInterface.OnClickListener onPositiveClickListener,
                                        String negativeText,
                                        DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener).create();
        alertDialog.show();
    }
    //获取状态栏高度
    public static int getStatusBarHeight(Context context){
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2/2;
    }
    /**
     * 当点击其他View时隐藏软键盘
     * @param activity
     * @param ev
     * @param excludeView  点击这些View不会触发隐藏软键盘动作
     */
    public static final void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, View excludeView){


        if (ev.getAction() == MotionEvent.ACTION_DOWN){
           /* if (excludeViews != null && !excludeViews.isEmpty()){
                for (int i = 0; i < excludeViews.size(); i++){*/
                    if (isTouchView(excludeView, ev)){
                        return;

            }
            View v = activity.getCurrentFocus();
            if (CommonUtils.isShouldHideInput(v, ev)){
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null){
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    excludeView.setVisibility(View.GONE);
                }
            }

        }
    }

    public static void setHintTextSize(EditText editText, String hintText, int textSize) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hintText);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

    public static final void hideInputWhenTouchOtherView1(Activity activity, MotionEvent ev, View excludeView){


        if (ev.getAction() == MotionEvent.ACTION_DOWN){
           /* if (excludeViews != null && !excludeViews.isEmpty()){
                for (int i = 0; i < excludeViews.size(); i++){*/
            if (isTouchView(excludeView, ev)){
                return;

            }
            View v = activity.getCurrentFocus();
            if (CommonUtils.isShouldHideInput(v, ev)){
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null){
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

        }
    }
    public static final void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, View excludeView, EditText childView){


        if (ev.getAction() == MotionEvent.ACTION_DOWN){
           /* if (excludeViews != null && !excludeViews.isEmpty()){
                for (int i = 0; i < excludeViews.size(); i++){*/
            if (isTouchView(excludeView, ev)){
                return;

            }
            View v = activity.getCurrentFocus();
            if (CommonUtils.isShouldHideInput(v, ev)){
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null){
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    childView.setText("");
                    excludeView.setVisibility(View.GONE);
                }
            }

        }
    }
    public static final boolean isTouchView(View view, MotionEvent event){
        if (view == null || event == null){
            return false;
        }
        int[] leftTop = {0, 0};
        view.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + view.getHeight();
        int right = left + view.getWidth();
        if (event.getRawX() > left && event.getRawX() < right
                && event.getRawY() > top && event.getRawY() < bottom){
            return true;
        }
        return false;
    }

    public static final boolean isShouldHideInput(View v, MotionEvent event){
        if (v != null && (v instanceof EditText)){
            return !isTouchView(v, event);
        }
        return false;
    }
    public static void hideInput(View v, Activity activity){
        //View v = activity.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null){
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void setWindowBackAlpha(Activity activity, float bgAlpha){
        WindowManager.LayoutParams lp = (activity).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        (activity).getWindow().setAttributes(lp);
    }
}
