package com.huotu.android.mifang.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.KeyboardShortcutGroup
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.library.libpay.alipay.AliOrderInfo
import com.huotu.android.library.libpay.alipayV2.AliPayResultV2
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.PaymentAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.BuyContract
import com.huotu.android.mifang.mvp.presenter.BuyPresenter
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.KeybordUtils
import com.huotu.android.mifang.util.PayUtils
import com.huotu.android.mifang.widget.OperateDialog
import com.huotu.android.mifang.widget.ProvinceCityAreaDialog
import kotlinx.android.synthetic.main.activity_applyagent.*
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.layout_header.*
import org.w3c.dom.Text
import java.math.BigDecimal

/**
 * 申请代理商
 */
class ApplyAgentActivity : BaseActivity<BuyContract.Presenter>()
        , BuyContract.View
        , Handler.Callback
        ,ProvinceCityAreaDialog.OnOperateListener
        ,OperateDialog.OnOperateListener<AddressBean>
        , BaseQuickAdapter.OnItemClickListener
        ,View.OnClickListener{

    private var iPresenter= BuyPresenter(this)
    private var paymentAdapter:PaymentAdapter ?=null
    private var goodsBean:AgentUpgradeGoodsBean?=null
    private var handler=Handler(this)
    private var address:AddressBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applyagent)

        header_title.text = "申请代理商"
        header_left_image.setOnClickListener(this)
        applyagent_lay_three.setOnClickListener(this)
        applyagent_operate.setOnClickListener(this)
        province_city_area_dialog_lay_address.setOnClickListener(this)

        paymentAdapter = PaymentAdapter(ArrayList())
        paymentAdapter!!.onItemClickListener=this
        applyagent_recyclerView.layoutManager = GridLayoutManager(this ,3)
        applyagent_recyclerView.adapter = paymentAdapter

        iPresenter.getPaymentItems()
        iPresenter.getAgentUpgradeGoods()
    }

    override fun onDestroy() {
        super.onDestroy()

        if( handler!=null){
            handler.removeCallbacksAndMessages(null)
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        applyagent_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        applyagent_progress.visibility=View.GONE
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
                KeybordUtils.closeKeyboard(this)
                finish()
            }
            R.id.applyagent_operate->{
                    submitOrder()
            }
            R.id.applyagent_lay_three->{
                openProvinceDialog()
            }
            R.id.province_city_area_dialog_lay_address->{
                iPresenter.getAddressList()
            }
        }
    }

    private fun openProvinceDialog(){
        var dialog = ProvinceCityAreaDialog(this , this)
        dialog.show("","","")
    }

    override fun operate(keyValue: AddressBean) {
        applyagent_zone.text = keyValue.province+"/"+keyValue.city+"/"+keyValue.county
        applyagent_address.setText( keyValue.address)
        applyagent_username.setText( keyValue.name)
        applyagent_mobile.setText(keyValue.mobile)

        address = keyValue
    }

    override fun operate(province: ProvinceCityArea, city: ProvinceCityArea, area: ProvinceCityArea?) {

        applyagent_zone.text = province.text+"/"+city.text+ if(area==null) "" else  "/"+area.text

        address = AddressBean(0 ,"","",province.text , city.text , area!!.text , "" , province.value, city.value, area.value )

    }

    private fun submitOrder(){

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

        if(goodsBean!!.NeedAddr==0){
            iPresenter.submitAgentUpgradeOrder("", "", "", "", "", paymentType)
        }else {
            var shipName = applyagent_username.text.trim().toString()
            var shipmobile = applyagent_mobile.text.trim().toString()
            var shipaddress = applyagent_address.text.trim().toString()
            var shiparea = applyagent_zone.text.trim().toString()


            if(TextUtils.isEmpty( applyagent_zone.text.trim() )){
                toast("请选择省市区")
                openProvinceDialog()
                return
            }

            if( TextUtils.isEmpty( shipaddress)){
                toast("请输入详细地址")
                applyagent_address.requestFocus()
                KeybordUtils.openKeybord(this,applyagent_address)
                return
            }
            if(TextUtils.isEmpty(shipName)){
                toast("请输入收件人姓名")
                applyagent_username.requestFocus()
                KeybordUtils.openKeybord(this,applyagent_username)
                return
            }
            if(TextUtils.isEmpty(shipmobile)){
                toast("请输入收件人手机号码")
                applyagent_mobile.requestFocus()
                KeybordUtils.openKeybord(this,applyagent_mobile)
                return
            }

            if(address==null){
                toast("请选择省市区")
                return
            }

            KeybordUtils.closeKeyboard(this)

            var shipareacode = address!!.provinceCode+"/"+address!!.cityCode+"/"+address!!.countyCode

            iPresenter.submitAgentUpgradeOrder(shipName, shipmobile, shipaddress, shiparea, shipareacode, paymentType)
        }

    }

    override fun getAddressListCallback(apiResult: ApiResult<ArrayList<AddressBean>>) {
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null || apiResult.data!!.size<1){
            toast("无地址可选择")
            return
        }

        var dialog = OperateDialog(this , this , apiResult.data!!, "选择地址")

        var screenHeight = DensityUtils.getScreenHeight(this)
        //dialog.setSize( ViewGroup.LayoutParams.MATCH_PARENT ,  screenHeight/2)

        dialog.show()






    }

    override fun getAgentUpgradeGoodsCallback(apiResult: ApiResult<AgentUpgradeGoodsBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        goodsBean = apiResult.data
        applyagent_goodsname.text =goodsBean!!.GoodsName
        applyagent_price.text =goodsBean!!.GoodsPrice+"元"
        applyagent_logo.setImageURI( goodsBean!!.GoodsImgURL )
        applyagent_lay_address.visibility = if(goodsBean!!.NeedAddr==0) View.GONE else View.VISIBLE
        applyagent_money.text= goodsBean!!.GoodsPrice
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
    }

    override fun submitAgentUpgradeOrderCallback(apiResult: ApiResult<InviteOrderBean>) {
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

    override fun submitOrderCallback(apiResult: ApiResult<InviteOrderBean>) {
    }

    private fun wechatPay(orderBean: InviteOrderBean){
        var extData = "{orderNo:" + orderBean.UnionOrderId + "}"
        var payModel = PayModel( orderBean.appId , orderBean.partnerid
                , Constants.CUSTOMERID.toString() , orderBean.UnionOrderId ,
                "" , 0 ,"","","","",
                orderBean.prepayId , orderBean.`package`, orderBean.nonceStr, orderBean.timeStamp, orderBean.sign, extData)
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

    override fun handleMessage(msg: Message?): Boolean {
        when( msg!!.what){
            PayUtils.SDK_Ali_PAY_V2_FLAG->{
                //var data = msg.obj as AliPayResultV2
                toast( "支付成功")
            }
        }
        return true
    }
}
