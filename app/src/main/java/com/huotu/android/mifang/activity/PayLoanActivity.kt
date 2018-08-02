package com.huotu.android.mifang.activity

import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.library.libpay.alipay.AliOrderInfo
import com.huotu.android.library.libpay.alipayV2.AliPayResultV2
import com.huotu.android.library.libpay.weixin.WeiXinPayResult
import com.huotu.android.library.libpay.weixin.WeiXinPayUtil
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.MomeyAdapter
import com.huotu.android.mifang.adapter.PaymentAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.PayLoanContract
import com.huotu.android.mifang.mvp.presenter.PayLoanPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.receiver.PayReceiver
import com.huotu.android.mifang.receiver.QuitReceiver
import com.huotu.android.mifang.util.PayUtils
import com.huotu.android.mifang.widget.RecyclerViewDivider4
import com.huotu.android.mifang.widget.RecyclerViewDivider5
import kotlinx.android.synthetic.main.activity_applyagent.*
import kotlinx.android.synthetic.main.activity_payloan.*
import kotlinx.android.synthetic.main.layout_header_2.*

/**
 * 货款充值
 */
class PayLoanActivity : BaseActivity<PayLoanContract.Presenter>()
        ,PayLoanContract.View
        , Handler.Callback
        , PayReceiver.PayListener
        ,BaseQuickAdapter.OnItemClickListener
        ,View.OnClickListener{
    private var iPresenter=PayLoanPresenter(this)
    private var momeyAdapter:MomeyAdapter?=null
    private var paymentAdapter:PaymentAdapter ?=null
    private var handler=Handler(this)
    private var payReceiver:PayReceiver?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payloan)

        header_title.text ="货款充值"
        header_right_text.text="流水"
        header_right_image.visibility=View.GONE
        header_right_text.setCompoundDrawables(null,null,null,null)
        header_right_text.setOnClickListener(this)
        header_left_image.setOnClickListener(this)
        payloan_pay.setOnClickListener(this)

        registerPayReceiver()

        momeyAdapter = MomeyAdapter(ArrayList())
        momeyAdapter!!.onItemClickListener=this
        payloan_recyclerview_money.layoutManager = GridLayoutManager(this ,3)
        payloan_recyclerview_money.adapter = momeyAdapter
        payloan_recyclerview_money.addItemDecoration(RecyclerViewDivider5(this, ContextCompat.getColor(this,R.color.white) , 5f ))

        paymentAdapter = PaymentAdapter(ArrayList())
        paymentAdapter!!.onItemClickListener=this
        payloan_recyclerview_pay.layoutManager = GridLayoutManager(this ,3)
        payloan_recyclerview_pay.adapter = paymentAdapter
        payloan_recyclerview_pay.addItemDecoration(RecyclerViewDivider5(this, ContextCompat.getColor(this,R.color.white) , 5f ))


        iPresenter.getDepositIndex()
        iPresenter.getPaymentItems()
    }

    override fun onDestroy() {
        super.onDestroy()

        if( handler!=null){
            handler.removeCallbacksAndMessages(null)
        }
        unRegisterPayReceiver()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if(adapter is MomeyAdapter) {

            var current = (adapter!!.getItem(position) as DepositBean.DepositItem)
            for (bean in adapter!!.data) {
                var item = (bean as DepositBean.DepositItem)
                if (item.GoodsId == current.GoodsId) {
                    item.checked = !item.checked
                } else {
                    item.checked = false
                }
            }
            adapter!!.notifyDataSetChanged()
        }else {
            var current = (adapter!!.getItem(position) as PaymentItem)
            for (bean in adapter!!.data) {
                var item = (bean as PaymentItem)
                if (item.PaymentType == current.PaymentType) {
                    item.checked = !item.checked
                } else {
                    item.checked = false
                }
            }
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        payloan_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        payloan_progress.visibility=View.GONE
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_text->{
                newIntent<PayLoanFlowActivity>()
            }
            R.id.payloan_pay->{
                pay()
            }
        }
    }

    private fun pay(){
        if(paymentAdapter!!.data.isEmpty()){
            toast("请选择支付方式")
            return
        }
        if(momeyAdapter!!.data.isEmpty()){
            toast("充值金额空")
            return
        }

        var goodsId:Long=0
        var productId:Long=0

        for(bean in momeyAdapter!!.data){
            if(bean.checked){
                goodsId = bean.GoodsId
                productId=bean.ProductId
                break
            }
        }
        if(goodsId==0L||productId==0L){
            toast("请选择充值金额")
            return
        }

        var paymentType = -1
        for(bean in paymentAdapter!!.data){
            if(bean.checked){
                paymentType = bean.PaymentType
                break
            }
        }
        if(paymentType==-1){
            toast("请选择支付方式")
            return
        }

        iPresenter.submitGoodsDepositOrder(paymentType, goodsId, productId)

    }

    override fun getPaymentItemsCallback(apiResult: ApiResult<ArrayList<PaymentItem>>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null) {
            toast("缺少支付方式")
            return
        }
        paymentAdapter!!.setNewData(apiResult.data)
    }

    override fun getDepositIndexCallback(apiResult: ApiResult<DepositBean>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if (apiResult.data == null) return

        payloan_loan.text = apiResult.data!!.MyDeposit
        payloan_loan2.text ="（欠款￥"+ apiResult.data!!.OweDeposit+"元）"

        momeyAdapter!!.setNewData(apiResult.data!!.GoodsItems)

    }

    override fun submitGoodsDepositOrderCallback(apiResult: ApiResult<DepositOrderBean>) {

        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null) {
            toast("缺少支付信息")
            return
        }

        if(apiResult.data!!.payType == 0){
            wechatPay(apiResult.data!!)
        }else if(apiResult.data!!.payType==1){
            aliPay(apiResult.data!!)
        }

    }

    private fun wechatPay(orderBean: DepositOrderBean){
        var extData= "{orderNo:" + orderBean.UnionOrderId + "}"
        var payModel = PayModel( orderBean.appId , orderBean.partnerid
                , Constants.CUSTOMERID.toString() , orderBean.UnionOrderId
                , "" , 0 ,"","","","0" ,
                orderBean.prepayId , orderBean.`package`, orderBean.nonceStr ,
                orderBean.timeStamp , orderBean.sign , extData)

        PayUtils().wxPay(this , handler , payModel  )

    }
    private fun aliPay(orderBean: DepositOrderBean){
        var aliOrderInfo = AliOrderInfo()
        aliOrderInfo.body=""
        aliOrderInfo.orderNo= orderBean.UnionOrderId
        aliOrderInfo.subject=""
        aliOrderInfo.totalfee=0

        PayUtils().aliNativePay(aliOrderInfo ,this , orderBean.aliPayOrderString ,handler )
    }

    override fun getDepositListCallback(apiResult: ApiResult<ArrayList<PayLoanBean>>) {

    }

    override fun getFrozenFlowCallback(apiResult: ApiResult<ArrayList<FrozenFlow>>) {

    }

    override fun handleMessage(msg: Message?): Boolean {
        when( msg!!.what){
            PayUtils.SDK_Ali_PAY_V2_FLAG->{
                var data = msg.obj as AliPayResultV2
                toast( "支付成功")
            }
        }
        return true
    }


    private fun registerPayReceiver() {
        payReceiver = PayReceiver()
        payReceiver!!.setPayListener(this)
        val intentFilter = IntentFilter(PayReceiver.ACTION_PAY)
        this.registerReceiver(payReceiver, intentFilter)
    }

    private fun unRegisterPayReceiver(){
        if(payReceiver!=null){
            payReceiver!!.setPayListener(null)
            this.unregisterReceiver(payReceiver)
            payReceiver=null
        }
    }

    override fun payCallback(success: Boolean) {
        if(success){
            iPresenter.getDepositIndex()
        }
    }
}
