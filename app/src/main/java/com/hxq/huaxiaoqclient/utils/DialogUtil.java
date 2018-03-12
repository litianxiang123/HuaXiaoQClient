package com.hxq.huaxiaoqclient.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.interfaces.onDialogClickListener;


public class DialogUtil {


    String title, message, confirm, cancel;
    Activity mActivity;
    boolean canceled;
    onDialogClickListener clickListener;


    public DialogUtil(Activity activity) {
        mActivity = activity;
    }

    public DialogUtil setClickListener(onDialogClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public DialogUtil setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogUtil setMessage(String message) {
        this.message = message;
        return this;
    }

    public DialogUtil setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    public DialogUtil setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    public DialogUtil setCanceled(boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    /**
     * 显示 弹框
     */
    public AlertDialog showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        int width = CommonUtils.getScreenWidth(mActivity) - ContextUtils.dip2px(mActivity, 50);
        window.setLayout(width, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        View view = mActivity.getLayoutInflater().inflate(R.layout.confirm_cancle_dialog, null);
        window.setContentView(view);//

        LinearLayout bg_layout = (LinearLayout) view.findViewById(R.id.bg_layout);
//        new ViewBg().setBg(mActivity, bg_layout, "#ffffff", "#ffffff", 30);


        TextView title_txt = (TextView) view.findViewById(R.id.title);
        TextView message_txt = (TextView) view.findViewById(R.id.message);
        TextView cancel_txt = (TextView) view.findViewById(R.id.cancle);
        TextView confirm_txt = (TextView) view.findViewById(R.id.confirm);

        if (title != null && !title.isEmpty()) {
            title_txt.setText(title);
            title_txt.setVisibility(View.VISIBLE);
        } else {
            title_txt.setVisibility(View.GONE);
        }

        if (message != null && !message.isEmpty()) {
            message_txt.setText(message);
            message_txt.setVisibility(View.VISIBLE);
        } else {
            message_txt.setVisibility(View.GONE);
        }

        if (cancel != null && !cancel.isEmpty()) {
            cancel_txt.setText(cancel);
            cancel_txt.setVisibility(View.VISIBLE);
        } else {
            cancel_txt.setVisibility(View.GONE);
        }

        if (confirm != null && !confirm.isEmpty()) {
            confirm_txt.setText(confirm);
            confirm_txt.setVisibility(View.VISIBLE);
        } else {
            confirm_txt.setVisibility(View.GONE);
        }

        dialog.setCanceledOnTouchOutside(canceled);

        cancel_txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onCancleClick();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });

        confirm_txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onConfirmClick();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });


        return dialog;
    }

    private static ProgressDialog pd;


}
