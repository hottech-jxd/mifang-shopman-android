package com.huotu.mall.nton.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.util.ToastUtils
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * 微信支付回调类
 */
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    //private val api: IWXAPI? = null
    //private val application: BaseApplication? = null

    override fun onReq(baseReq: BaseReq) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView ( R.layout.pay_result );
        //        application = ( BaseApplication ) this.getApplication ();
        //        api = WXAPIFactory.createWXAPI ( this, application.readWxpayAppId ( ) );
        //        api.handleIntent ( getIntent ( ), this );

        try {
            val isDeal = AppInit.iwxApi!!.handleIntent(intent, this)
            if (!isDeal) {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onResp(resp: BaseResp) {

        Log.i("info", "onPayFinish, errCode = " + resp.errCode)

        //        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
        //            String msg = "";
        //            if( resp.errCode== 0)
        //            {
        //                BuyerPayUtil.paySuccessCallback( this , resp);
        //
        //                msg="支付成功";
        //                this.finish();
        //                ToastUtils.showLongToast (  msg );
        //                return;
        //            }else if( resp.errCode== -1){
        //                BuyerPayUtil.wxPayErrorCallback(resp);
        //
        //                msg="支付失败";
        //                ToastUtils.showLongToast (  msg );
        //                this.finish();
        //                return;
        //            }else if(resp.errCode ==-2){
        //                BuyerPayUtil.wxPayCancelCallback(resp);
        //
        //                msg="用户取消支付";
        //                ToastUtils.showLongToast( msg);
        //                this.finish();
        //                return;
        //            }
        //
        //            PayResp payResp = (PayResp)resp;
        //            if(null==payResp){
        //                Log.i("wxpay>>>payResp=null","");
        //                msg="支付失败";
        //                ToastUtils.showLongToast( msg);
        //                this.finish();
        //                return;
        //            }else{
        //                Log.i("wxpay>>>extData", payResp.extData==null? "": payResp.extData );
        //                //Log.i("wxpay>>>prepayid",payResp.prepayId);
        //            }
        //
        //        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        //api!!.handleIntent(intent, this)

        AppInit.iwxApi!!.handleIntent(intent, this)
    }
}
