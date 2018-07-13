package com.huotu.android.mifang.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.KeyboardShortcutGroup
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.library.libpay.alipay.AliOrderInfo
import com.huotu.android.library.libpay.alipayV2.AliPayResultV2
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.PaymentAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.BuyContract
import com.huotu.android.mifang.mvp.presenter.BuyPresenter
import com.huotu.android.mifang.util.KeybordUtils
import com.huotu.android.mifang.util.PayUtils
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.layout_header.*
import java.math.BigDecimal

class BuyActivity : BaseActivity<BuyContract.Presenter>()
        , BuyContract.View
        , Handler.Callback
        , BaseQuickAdapter.OnItemClickListener
        ,View.OnClickListener{
    private var type = 0 //0:购买店主账号，1：续费
    private var iPresenter= BuyPresenter(this)
    private var paymentAdapter:PaymentAdapter ?=null
    private var goodsBean:GoodsBean?=null
    private var handler=Handler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)


        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,0)
        }
        if(type == 0 ) {
            header_title.text = "购买开店账号"

            buy_lay_three.visibility = View.GONE
            buy_lay_four.visibility = View.GONE
            buy_line3.visibility = View.GONE

            buy_size.setText("1")
            buy_summary_count.text = "总共计：1个"
            buy_money.text = "0.00"

        }else if(type==1){
            header_title.text = "续费"

            buy_lay_three.visibility = View.VISIBLE
            buy_lay_four.visibility = View.VISIBLE
            buy_line3.visibility = View.VISIBLE

            buy_size.setText("1")
            buy_summary_count.text = "总共计：1个/抵扣：0个"
            buy_money.text = "0.00"
        }

        header_left_image.setOnClickListener(this)
        buy_add.setOnClickListener(this)
        buy_jian.setOnClickListener(this)
        buy_operate.setOnClickListener(this)

        paymentAdapter = PaymentAdapter(ArrayList())
        paymentAdapter!!.onItemClickListener=this
        buy_pay_recyclerView.layoutManager = GridLayoutManager(this ,3)
        buy_pay_recyclerView.adapter = paymentAdapter


        iPresenter.getBuyInfo()
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        buy_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        buy_progress.visibility=View.GONE
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var current =(adapter!!.getItem(position) as PaymentItem)
        for(bean in adapter!!.data){
            var item =(bean as PaymentItem)
            if( item.PaymentType == current.PaymentType){
                item.checked=!item.checked
            }else{
                item.checked=false
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.buy_add->{
                add()
            }
            R.id.buy_jian->{
                minus()
            }
            R.id.buy_operate->{
                if(type==0) {
                    submitOrder()
                }else if(type==1){
                    //todo 续费
                }
            }
        }
    }

    private fun submitOrder(){
        var countStr = buy_size.toString()
        if(TextUtils.isEmpty(countStr)){
            buy_size.requestFocus()
            KeybordUtils.openKeybord(this,buy_size)
            return
        }
        var count = countStr.toInt()

        if(paymentAdapter!!.data.isEmpty()){
            toast("请选择支付方式")
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

        iPresenter.submitOrder( count , paymentType)

    }

    private fun add(){

        var size = if( TextUtils.isEmpty( buy_size.text )) 1 else buy_size.text.toString().toInt()
        size +=1
        buy_size.setText(size.toString())

        if(type == 0 ) {
            buy_summary_count.text = "总共计：" + size + "个"
            buy_money.text = if( goodsBean ==null ) "0.00" else goodsBean!!.GoodsPrice.multiply( BigDecimal(size)).toString()
        }else {
            buy_summary_count.text = "总共计：" + size + "个/抵扣：0个"
            buy_money.text = if( goodsBean==null ) "0.00" else  goodsBean!!.GoodsPrice.multiply( BigDecimal(size)).toString()
        }
    }

    private fun minus(){
        var size = if( TextUtils.isEmpty( buy_size.text )) 1 else buy_size.text.toString().toInt()
        size -=1
        buy_size.setText(size.toString())
        if(type == 0) {
            buy_summary_count.text = "总共计：" + size + "个"
            buy_money.text = if ( goodsBean==null ) "0.00" else goodsBean!!.GoodsPrice.multiply( BigDecimal(size)).toString()
        }else {
            buy_summary_count.text = "总共计：" + size + "个/抵扣：0个"
            buy_money.text = if(goodsBean ==null) "0.00" else goodsBean!!.GoodsPrice.multiply( BigDecimal(size)).toString()
        }
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

    override fun getRenewGoodsCallback(apiResult: ApiResult<GoodsBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null) {
            toast("缺少支付方式")
            return
        }

        goodsBean = apiResult.data!!
        buy_logo.setImageURI(apiResult.data!!.GoodsImgURL)
        buy_goodsname.text = apiResult.data!!.GoodsName
        buy_goodsprice.text = "单价"+ apiResult.data!!.GoodsPrice+"元/个"
        buy_size.setText("1")
        if(type == 0) {
            buy_summary_count.text = "总共计：1个"
        }else {
            buy_summary_count.text = "总共计：1个/抵扣：0个"
        }
        buy_money.text = goodsBean!!.GoodsPrice.toString()

    }

    override fun submitOrderCallback(apiResult: ApiResult<InviteOrderBean>) {
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

    private fun wechatPay(orderBean: InviteOrderBean){
        var payModel = PayModel( orderBean.WxAppId , orderBean.WxAppMchId , "", orderBean.UnionOrderId , "" , 0 ,"","","","", orderBean.PrepayId)
        PayUtils().wxPay(this , handler , payModel  )
    }
    private fun aliPay(orderBean: InviteOrderBean){
        var aliOrderInfo = AliOrderInfo()
        aliOrderInfo.body=""
        aliOrderInfo.orderNo= orderBean.UnionOrderId
        aliOrderInfo.subject=""
        aliOrderInfo.totalfee=0

        PayUtils().aliNativePay(aliOrderInfo ,this , orderBean.aliPayOrderString ,handler )
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
}
