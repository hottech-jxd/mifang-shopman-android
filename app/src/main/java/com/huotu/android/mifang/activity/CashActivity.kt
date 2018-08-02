package com.huotu.android.mifang.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.CashContract
import com.huotu.android.mifang.mvp.presenter.CashPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.newIntentForResult
import com.huotu.android.mifang.util.KeybordUtils
import com.huotu.android.mifang.widget.PayDialog
import kotlinx.android.synthetic.main.activity_cash.*
import kotlinx.android.synthetic.main.layout_header.*
import java.math.BigDecimal
import java.text.NumberFormat

class CashActivity : BaseActivity<CashContract.Presenter>()
        , CashContract.View
        ,PayDialog.OnOperateListener
        ,View.OnClickListener {
    var REQUEST_CODE = 6001
    var iPresenter=CashPresenter(this)
    private var data:CashIndex?=null
    private var payDialog:PayDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)

        header_title.text="我要提现"
        header_left_image.setOnClickListener(this)
        cash_record.setOnClickListener(this)
        cash_lay_1.setOnClickListener(this)
        cash_get_all.setOnClickListener(this)
        cash_operate.setOnClickListener(this)

        cash_record.paint.flags = Paint. UNDERLINE_TEXT_FLAG

        iPresenter.cashIndex()

        cash_money.requestFocus()
        KeybordUtils.openKeybord(this, cash_money)
    }

    override fun onDestroy() {
        KeybordUtils.closeKeyboard(this)
        super.onDestroy()
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        cash_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        cash_progress.visibility =View.GONE
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                KeybordUtils.closeKeyboard(this)
                finish()
            }
            R.id.cash_record->{
                KeybordUtils.closeKeyboard(this)
                newIntent<CashRecordActivity>()
            }
            R.id.cash_lay_1->{
                KeybordUtils.closeKeyboard(this)
                newIntentForResult<PayAccountActivity>( REQUEST_CODE , Constants.INTENT_PAY_ACCOUNT_ID , if(data==null) 0L else  data!!.AccountId)
            }
            R.id.cash_get_all->{
                cashAll()
            }
            R.id.cash_operate->{
                cash()
            }
        }
    }

    private fun cash(){

        if(data==null)return
        if(data!!.AccountId<1){
            toast("请选择提现账号")
            return
        }

        var moneyString = cash_money.text.trim().toString()
        if( TextUtils.isEmpty( moneyString)){
            cash_money.requestFocus()
            KeybordUtils.openKeybord(this, cash_money)
            return
        }
        var money = moneyString.toBigDecimal()
        if(money.compareTo( data!!.BaseMoney )<0){
            toast("最低起提金额为:"+ data!!.BaseMoney.toString()+"，请重新输入")
            cash_money.requestFocus()
            KeybordUtils.openKeybord(this,cash_money)
            return
        }



        if(data!!.IsSettingPayWord) {

            if (payDialog == null) {
                payDialog = PayDialog(this, this)
            }

            payDialog!!.show()
        }else {
            var accountId = data!!.AccountId
            var applyMoney = money.multiply(BigDecimal(100))
            iPresenter.submitApply( accountId , applyMoney.toLong())
        }
    }


    override fun operate() {
        var accountId = data!!.AccountId
        var moneyString = cash_money.text.trim().toString()
        var money = moneyString.toBigDecimal()
        var applyMoney = money.multiply(BigDecimal(100))
        iPresenter.submitApply( accountId , applyMoney.toLong())
    }

    private fun cashAll(){
        if(data==null) return

        var money = data!!.UserIntegral
        money.setScale(2,BigDecimal.ROUND_HALF_UP)
        money = money.divide(BigDecimal(100))
        var numberFormat = NumberFormat.getNumberInstance() //NumberFormat.getCurrencyInstance()

        numberFormat.minimumFractionDigits=2
        numberFormat.maximumFractionDigits=4
        var moneyString = numberFormat.format(money.toDouble())
        moneyString = moneyString.replace("￥","").replace(",","")

        cash_money.setText( moneyString )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode== REQUEST_CODE && resultCode == Activity.RESULT_OK){
            var bean = data!!.getSerializableExtra(Constants.INTENT_PAY_ACCOUNT) as PayAccount

            cash_hint.visibility=View.GONE
            cash_email.visibility=View.VISIBLE
            cash_name.visibility=View.VISIBLE

            cash_name.text= bean.RealName
            cash_email.text = bean.AccountInfo


            //cash_name.tag = bean
            this.data!!.AccountId = bean.AccountId
            this.data!!.UserRealName = bean.RealName
            this.data!!.AccountInfo = bean.AccountInfo
        }
    }

    override fun cashIndexCallback(apiResult: ApiResult<CashIndex>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        data = apiResult.data
        if(apiResult.data==null){
            cash_name.text="请选择支付账户"
            cash_email.text=""
            cash_hint.visibility=View.VISIBLE
            cash_name.visibility=View.GONE
            cash_email.visibility=View.GONE
            return
        }

        if(apiResult.data!!.AccountId == 0L){
            cash_hint.visibility=View.VISIBLE
            cash_name.visibility=View.GONE
            cash_email.visibility=View.GONE
        }else {
            cash_hint.visibility=View.GONE
            cash_name.visibility=View.VISIBLE
            cash_email.visibility=View.VISIBLE
            cash_name.text = apiResult.data!!.UserRealName
            cash_email.text = apiResult.data!!.AccountInfo
        }

        var money = apiResult.data!!.UserIntegral
        money.setScale(2,BigDecimal.ROUND_HALF_UP)
        money = money.divide(BigDecimal(100))
        var numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.minimumFractionDigits=2
        numberFormat.maximumFractionDigits=4
        var moneyString = numberFormat.format(money.toDouble())
        cash_can_get_money.text = moneyString
        cash_money.setText( apiResult.data!!.BaseMoney.toString())

        var descript= "1.起提金额为"+apiResult.data!!.BaseMoney + "元；2.每月提现次数为"+ apiResult.data!!.MonthCount +"次"
        cash_description.text = descript
    }

    override fun cashListCallback(apiResult: ApiResult<ArrayList<CashRecord>>) {

    }

    override fun judgePasswordCallback(apiResult: ApiResult<Any>) {

    }

    override fun submitApplyCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        toast(apiResult.msg)
        KeybordUtils.closeKeyboard(this)
        setResult(Activity.RESULT_OK)
        finish()
    }
}
