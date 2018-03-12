package com.hxq.huaxiaoqclient.interfaces;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.hxq.huaxiaoqclient.utils.ToastUtls;

import org.senydevpkg.net.HttpLoader;
import org.senydevpkg.utils.ALog;

/**
 * Created by fenghaoxiu on 16/8/23.
 */
public class DemoTradeCallback implements AlibcTradeCallback {

    private static final String TAG = "DemoTradeCallback";


    Activity mActivity;
    HttpLoader httpLoader;

    public DemoTradeCallback(Activity activity) {
        mActivity = activity;
        httpLoader = HttpLoader.getInstance(activity);
    }


    @Override
    public void onFailure(int errCode, String errMsg) {
        ToastUtls.showToast(mActivity, "电商SDK出错,错误码=" + errCode + " / 错误消息=", Toast.LENGTH_LONG);
    }

    @Override
    public void onTradeSuccess(TradeResult tradeResult) {
        if (tradeResult.resultType.equals(tradeResult.resultType.TYPECART)) {
            ALog.e("add card success");
            //加购成功
//            ToastUtls.showToast(mActivity, "加购成功", Toast.LENGTH_LONG);
        } else if (tradeResult.resultType.equals(tradeResult.resultType.TYPEPAY)) {


           /* List payFailedOrders = tradeResult.payResult.payFailedOrders;
            for (int i = 0; i < payFailedOrders.size(); i++) {
                Intent intent = new Intent(mActivity, AlibcPaySuccessService.class);
                intent.putExtra(AlibcPaySuccessService.ALIBC_ORDER_ID, payFailedOrders.get(i).toString());
                mActivity.startService(intent);
            }*/

            //            ToastUtls.showToast(mActivity, "支付成功,成功订单号为" + tradeResult.payResult.paySuccessOrders, Toast.LENGTH_SHORT);
        }
    }
}
