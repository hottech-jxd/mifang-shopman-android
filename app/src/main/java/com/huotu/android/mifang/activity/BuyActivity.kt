package com.huotu.android.mifang.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import com.huotu.android.mifang.widget.RecyclerViewDivider5
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.layout_header.*
import java.math.BigDecimal
import java.text.NumberFormat

class BuyActivity : BaseActivity<BuyContract.Presenter>()
        , BuyContract.View
        , Handler.Callback
        , TextWatcher
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
        buy_size.addTextChangedListener(this)

        paymentAdapter = PaymentAdapter(ArrayList())
        paymentAdapter!!.onItemClickListener=this
        buy_pay_recyclerView.layoutManager = GridLayoutManager(this ,3)
        buy_pay_recyclerView.adapter = paymentAdapter
        buy_pay_recyclerView.addItemDecoration(RecyclerViewDivider5(this,ContextCompat.getColor(this ,R.color.white), 5f))


        iPresenter.getBuyInfo()
    }

    override fun onDestroy() {
        super.onDestroy()

        if( handler!=null){
            handler.removeCallbacksAndMessages(null)
        }
    }

    override fun afterTextChanged(s: Editable?) {
        try {
            var size = buy_size.text.toString()
            if(size.toInt()<1) {
                size="1"
                buy_size.setText("1")
            }
            calc(size.toInt())
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

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
        adapter.notifyDataSetChanged()
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
        var countStr = buy_size.text.toString()
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

        KeybordUtils.closeKeyboard(this)
        iPresenter.submitOrder( count , paymentType)

    }

    private fun add(){

        var size = if( TextUtils.isEmpty( buy_size.text )) 1 else buy_size.text.toString().toInt()
        size +=1
        if(size>9999) size=9999
        buy_size.setText(size.toString())
        calc(size )
//        buy_size.setText(size.toString())
//
//        if(type == 0 ) {
//            buy_summary_count.text = "总共计：" + size + "个"
//
//            var moneyStr="￥0.00"
//
//            if( goodsBean !=null) {
//                var momey = goodsBean!!.GoodsPrice
//                momey.setScale(2, BigDecimal.ROUND_HALF_UP)
//                momey.multiply(BigDecimal(size))
//                var numberFormat = NumberFormat.getCurrencyInstance()
//                numberFormat.minimumFractionDigits = 2
//                numberFormat.maximumFractionDigits = 2
//                moneyStr = numberFormat.format(momey.toDouble())
//            }
//            buy_money.text = moneyStr
//        }else {
//            buy_summary_count.text = "总共计：" + size + "个/抵扣：0个"
//            buy_money.text = if( goodsBean==null ) "￥0.00" else  goodsBean!!.GoodsPrice.multiply( BigDecimal(size)).toString()
//        }
    }

    private fun calc( size :Int ){
        //var size = if( TextUtils.isEmpty( buy_size.text )) 1 else buy_size.text.toString().toInt()
        //size +=1


        var moneyStr="￥0.00"
        if(goodsBean!=null){
            var momey = goodsBean!!.GoodsPrice
            momey.setScale(2, BigDecimal.ROUND_HALF_UP)
            momey = momey.multiply(BigDecimal(size))
            var numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.minimumFractionDigits = 2
            numberFormat.maximumFractionDigits = 2
            moneyStr = numberFormat.format(momey.toDouble())
        }

        if( type == 0){
            buy_summary_count.text = "总共计：" + size + "个"
            buy_money.text = moneyStr
        }else{
            buy_summary_count.text = "总共计：" + size + "个/抵扣：0个"
            buy_money.text = moneyStr
        }
    }

    private fun minus(){
        var size = if( TextUtils.isEmpty( buy_size.text )) 1 else buy_size.text.toString().toInt()
        size -=1
        if(size<1) size=1
        buy_size.setText(size.toString())
        calc(size )
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

        var price = apiResult.data!!.GoodsPrice
        var numberFormat=NumberFormat.getCurrencyInstance()
        numberFormat.maximumFractionDigits=4
        numberFormat.minimumFractionDigits=2
        var priceString = numberFormat.format(price.toDouble())

        buy_goodsprice.text = "单价"+ priceString +"元/个"
        buy_size.setText("1")
        if(type == 0) {
            buy_summary_count.text = "总共计：1个"
        }else {
            buy_summary_count.text = "总共计：1个/抵扣：0个"
        }
        buy_money.text = priceString //goodsBean!!.GoodsPrice.toString()

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
        var extData= "{orderNo:" + orderBean.UnionOrderId + "}"
        var payModel = PayModel( orderBean.appId , orderBean.partnerid
                , Constants.CUSTOMERID.toString() , orderBean.UnionOrderId
                , "" , 0 ,"","","","",
                orderBean.prepayId , orderBean.`package`, orderBean.nonceStr ,
                orderBean.timeStamp , orderBean.sign , extData)
        PayUtils().wxPay(this.applicationContext , handler , payModel  )
    }
    private fun aliPay(orderBean: InviteOrderBean){
        var aliOrderInfo = AliOrderInfo()
        aliOrderInfo.body=""
        aliOrderInfo.orderNo= orderBean.UnionOrderId
        aliOrderInfo.subject=""
        aliOrderInfo.totalfee=0

        PayUtils().aliNativePay(aliOrderInfo ,this , orderBean.aliPayOrderString ,handler )
    }

    override fun getAgentUpgradeGoodsCallback(apiResult: ApiResult<AgentUpgradeGoodsBean>) {

    }

    override fun getAddressListCallback(apiResult: ApiResult<ArrayList<AddressBean>>) {

    }

    override fun submitAgentUpgradeOrderCallback(apiResult: ApiResult<InviteOrderBean>) {

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
