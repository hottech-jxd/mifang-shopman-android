package com.huotu.android.mifang.util

import android.app.Activity
import android.util.Log

import com.alipay.sdk.app.PayTask
import com.huotu.android.mifang.bean.PayOrderBean


/**
 * Created by jinxiangdong on 2017/12/20.
 */
class PayUtils {

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
    fun aliNativePay(payOrderBean: PayOrderBean, aty: Activity, payInfo: String) {

        val payRunnable = Runnable {
            val alipay = PayTask(aty)
            val result = alipay.payV2(payInfo, true)
            Log.i("msp", result.toString())
            //todo 支付宝 支付完成回调
            //EventBus.getDefault().post(new AliPayReturnEvent( result , payOrderBean ));
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
    //    public void wxPay(Activity aty ,Handler mHandler , PayModel payModel ){
    //        if (!BaseApplication.single.scanWx()) {//缺少支付信息
    //            ToastUtils.showLongToast("缺少支付信息");
    //        } else {
    //
    //            payModel.setAttach(payModel.getCustomId() + "_0");
    //            //添加微信回调路径
    //            payModel.setNotifyurl( getNotifyUrl( BaseApplication.single.readWeixinDomain() , BaseApplication.single.readWeixinNotify() , BaseApplication.single.obtainMerchantUrl() ) );
    //
    //
    //            WeiXinOrderInfo weiXinOrderInfo = new WeiXinOrderInfo();
    //            weiXinOrderInfo.setBody(payModel.getDetail());
    //            //weiXinOrderInfo.setOrderNo(payModel.getTradeNo());
    //            weiXinOrderInfo.setOrderNo( payModel.getTradeNo() +"_"+ payModel.getCustomId() + "_" + new Random().nextInt(999));
    //            weiXinOrderInfo.setTotal_fee(payModel.getAmount());
    //            weiXinOrderInfo.setAttach(payModel.getAttach());
    //
    //            String wxAppId = BaseApplication.single.readWxpayAppId();
    //            String wxAppSecret = EncryptUtil.getInstance().decryptDES( BaseApplication.single.readWxpayAppKey() , Constants.getDES_KEY());
    //            String wxPartner=BaseApplication.single.readWxpayParentId();
    //            String notifyUrl = BaseApplication.single.obtainMerchantUrl() + BaseApplication.single.readWeixinNotify();
    //
    //            WeiXinPayInfo weiXinPayInfo = new WeiXinPayInfo( wxAppId , wxPartner , wxAppSecret , notifyUrl);
    //            WeiXinPayUtil weiXinPayUtil = new WeiXinPayUtil(aty , mHandler , weiXinPayInfo);
    //            weiXinPayUtil.pay(weiXinOrderInfo);
    //        }
    //    }


    //    protected String getNotifyUrl( String domain , String notifyUrl , String mallUrl){
    //        if( domain == null || domain.trim().isEmpty() ){
    //            return mallUrl + notifyUrl;
    //        }else{
    //            return domain+notifyUrl;
    //        }
    //    }
}
