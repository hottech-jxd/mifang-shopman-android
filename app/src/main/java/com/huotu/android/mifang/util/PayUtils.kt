package com.huotu.android.mifang.util

import android.app.Activity
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log

import com.alipay.sdk.app.PayTask
import com.huotu.android.library.libpay.alipay.AliOrderInfo
import com.huotu.android.library.libpay.alipayV2.AliPayResultV2
import com.huotu.android.library.libpay.weixin.WeiXinOrderInfo
import com.huotu.android.library.libpay.weixin.WeiXinPayInfo
import com.huotu.android.library.libpay.weixin.WeiXinPayUtil
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.PayModel
import com.huotu.android.mifang.bean.PayOrderBean
import java.util.*


/**
 * Created by jinxiangdong on 2017/12/20.
 */
class PayUtils {

    companion object {
        val SDK_Ali_PAY_V2_FLAG = 6001
    }



    //    public static void paySuccessCallback(Context context, BaseResp resp) {
    //        PayResp payResp = (PayResp) resp;
    //        Bundle bundle = new Bundle();
    //        if (payResp != null && payResp.extData != null) {
    //            WxPaySuccessCallbackModel extData = JSONUtil.getGson().fromJson(payResp.extData, WxPaySuccessCallbackModel.class);
    //            bundle.putSerializable(Constants.HUOTU_PAY_CALLBACK_KEY, extData);
    //        }
    //        MyBroadcastReceiver.sendBroadcast( BaseApplication.single, MyBroadcastReceiver.ACTION_PAY_SUCCESS, bundle);
    //    }

    //    public static void wxPayCancelCallback( BaseResp resp){
    //        PayResp payResp = (PayResp) resp;
    //        Bundle bundle = new Bundle();
    //        if (payResp != null && payResp.extData != null) {
    //            WxPaySuccessCallbackModel extData = JSONUtil.getGson().fromJson(payResp.extData, WxPaySuccessCallbackModel.class);
    //            bundle.putSerializable(Constants.HUOTU_PAY_CALLBACK_KEY, extData);
    //        }
    //        MyBroadcastReceiver.sendBroadcast(BaseApplication.single , MyBroadcastReceiver.ACTION_WX_PAY_CANCEL_CALLBACK, bundle);
    //    }

    //    public static void wxPayErrorCallback(BaseResp resp){
    //        PayResp payResp = (PayResp) resp;
    //        Bundle bundle = new Bundle();
    //        if (payResp != null && payResp.extData != null) {
    //            WxPaySuccessCallbackModel extData = JSONUtil.getGson().fromJson(payResp.extData, WxPaySuccessCallbackModel.class);
    //            bundle.putSerializable(Constants.HUOTU_PAY_CALLBACK_KEY, extData);
    //        }
    //        MyBroadcastReceiver.sendBroadcast(BaseApplication.single , MyBroadcastReceiver.ACTION_WX_PAY_ERROR_CALLBACK, bundle);
    //    }


    /**
     * 支付宝支付
     * @param payOrderBean
     * @param aty
     * @param payInfo
     */
    fun aliNativePay( aliOrderInfo: AliOrderInfo , aty: Activity, payInfo: String, handler: Handler) {

        val payRunnable = Runnable {
            val alipay = PayTask(aty)
            val result = alipay.payV2(payInfo, true)
            Log.i("msp", result.toString())
            //todo 支付宝 支付完成回调
            //EventBus.getDefault().post(new AliPayReturnEvent( result , payOrderBean ));

            val msg = Message()
            msg.what = SDK_Ali_PAY_V2_FLAG
            val aliPayResultV2 = AliPayResultV2(result, aliOrderInfo)
            msg.obj = aliPayResultV2
            handler.sendMessage(msg)

        }

        val payThread = Thread(payRunnable)
        payThread.start()

    }


    /**
     * 微信支付
     * @param aty
     * @param mHandler
     * @param payModel
     */
     fun wxPay(aty :Activity, mHandler : Handler, payModel:PayModel ) {




        if (TextUtils.isEmpty(payModel.wxAppId) || TextUtils.isEmpty(payModel.wxAppMchId)) {//缺少支付信息
            ToastUtils.single.showToast("缺少微信支付Appid,mechid信息")
            return
        } else {

            var weiXinOrderInfo =WeiXinOrderInfo()
            weiXinOrderInfo.body = payModel.detail
            weiXinOrderInfo.orderNo = payModel.tradeNo + "_" + payModel.customId + "_" + Random().nextInt(999)
            weiXinOrderInfo.attach = payModel.attach
            weiXinOrderInfo.total_fee = payModel.amount
            var weiXinPayInfo = WeiXinPayInfo(payModel.wxAppId , payModel.wxAppMchId, "" , payModel.notifyurl )

            WeiXinPayUtil(aty , mHandler , weiXinPayInfo ).sendPayReq(weiXinOrderInfo , payModel.wxPrePayId )

            var weiXinPayUtil = WeiXinPayUtil(aty, mHandler, weiXinPayInfo)
            weiXinPayUtil.pay(weiXinOrderInfo)
        }
    }


}
