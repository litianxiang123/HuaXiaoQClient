package com.hxq.huaxiaoqclient.glide;


import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxq.huaxiaoqclient.R;


/**
 * 网络图片加载工具类
 */
public class GlideUtils {
    private static GlideUtils glideUtils;

    public static GlideUtils getInstance() {
        if (glideUtils == null)
            glideUtils = new GlideUtils();
        return glideUtils;
    }

    /**
     * 普通加载网络图片
     *
     * @param context
     * @param image
     * @param url
     */
    public void loadImage(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .dontAnimate()
                .into(image);
    }

    public void loadImageGrop(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context, 4))
                .into(image);
    }

    public void loadImageGrop(Context context, ImageView image, String url , int radio) {
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context, radio))
                .into(image);
    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param image
     * @param uri
     */
    public void loadImage(Context context, ImageView image, Uri uri) {
        Glide.with(context)
                .load(uri)
                .into(image);

    }
    public void loadImageCircle(Context context, ImageView image, int resourceId) {


            Glide.with(context).load(resourceId).
                    bitmapTransform(new CropCircleTransformation(context))
                    .dontAnimate()
                    .error(R.mipmap.ic_launcher).into(image);
    }

    public void loadImageCircle(Context context, ImageView image, String url) {


        Glide.with(context).load(url).
                bitmapTransform(new CropCircleTransformation(context))
                .dontAnimate()
                .error(R.mipmap.ic_launcher).into(image);
    }
        /**
         * 加载资源文件
         *
         * @param context
         * @param image
         * @param resourceId
         */
    public void loadImage(Context context, ImageView image, int resourceId) {
        Glide.with(context)
                .load(resourceId)
                .into(image);

    }


    /**
     * 加载圆形网络图片 设置监听
     */
    public void loadImageListenter(Context context, String url, final Handler handler) {
        Glide.with(context)
                .load(url)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Message msg = new Message();
                        msg.obj = resource;
                        handler.sendMessage(msg);
                    }
                });
    }

    /**
     * 加载网络图片 并设置正方形
     */
    public void loadImageSquare(Context context, ImageView image, String url) {
        Glide.with(context)
                .load(url)
                .bitmapTransform(new CropSquareTransformation(context))
                .into(image);

    }

    /**
     * 加载网络图片 并设置矩形 自定义高度+宽度
     */
    public void loadImage(Context context, ImageView image, String url, int height, int width) {
        Glide.with(context)
                .load(url)
                .bitmapTransform(new CropTransformation(context, height, width))
                .into(image);

    }



}
