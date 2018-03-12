package com.hxq.huaxiaoqclient.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxq.huaxiaoqclient.R;
import com.hxq.huaxiaoqclient.model.HomeData;
import com.hxq.huaxiaoqclient.utils.AmountUtils;
import com.hxq.huaxiaoqclient.utils.ViewBg;

import org.senydevpkg.base.AbsBaseAdapter;
import org.senydevpkg.base.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ShowMoreAdapter extends AbsBaseAdapter<HomeData> {
    private Context context;
    public ShowMoreAdapter(Context context, List<HomeData> data) {
        super(data);
        this.context = context;
    }
    @Override
    protected BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowMoreHolder(context);
    }
    static class ShowMoreHolder extends BaseHolder<HomeData> {
        private Context context;
        private ImageView crowd_icon;
        private TextView crowd_state;
        private TextView crowd_des;
        private ProgressBar crowd_item_progress;
        private TextView crowd_item_complate;
        private TextView crow_item_ramaiday;
        private TextView crowd_item_remain;
        private ImageView goods_img;
        private TextView goods_name;
        private TextView goods_old_price;
        private TextView goods_price;
        private TextView account;
        private ViewBg viewBg;
        private TextView couponcount;

        public ShowMoreHolder(Context context) {
            super(context);
            this.context=context;
        }

        @Override
        protected View initView() {
            View view = View.inflate(getContext(), R.layout.shouye_item_goods, null);
            goods_img = (ImageView) view.findViewById(R.id.goods_img);
            goods_name = (TextView) view.findViewById(R.id.goods_name);
            goods_old_price = (TextView) view.findViewById(R.id.goods_old_price);
            goods_price = (TextView) view.findViewById(R.id.goods_price);
            account = (TextView) view.findViewById(R.id.account);
            couponcount = (TextView) view.findViewById(R.id.couponcount);
            viewBg = new ViewBg();
            //按比例设置控件的宽高
            return view;
        }

        @Override
        public void bindData(final HomeData data) {
            super.bindData(data);
            viewBg.setBg(context,account,"#FC5459",11);
            couponcount.setText(String.format(context.getString(R.string.couponacount),String.valueOf(data.getCouponRemainCount())));
            Glide.with(context).load(data.getPicUrl()).into(goods_img);
            goods_name.setText(data.getBusinessTitle());
            Typeface face = Typeface.createFromAsset (context.getAssets(),"fonts/schrift.otf" );
            goods_price.setTypeface (face);
            try {
                goods_old_price.setText(AmountUtils.changeF2Y((long)data.getOriginalPrice()));
                goods_price.setText(AmountUtils.changeF2Y((long)data.getSalesPrice()));
                account.setText("立减"+AmountUtils.changeF2Y((long)data.getCouponPrice()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
