package com.huotu.android.mifang.activity


import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.PayAccount
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.PayAccountContract
import com.huotu.android.mifang.mvp.presenter.PayAccountPresenter
import com.huotu.android.mifang.util.KeybordUtils
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.activity_edit_pay_account.*
import kotlinx.android.synthetic.main.layout_header.*

class EditPayAccountActivity : BaseActivity<PayAccountContract.Presenter>()
        , PayAccountContract.View
        ,View.OnClickListener{
    var type :Int = 0
    var payAccount : PayAccount?=null
    var isDefault= false
    var accountType = 0 /*提现账户类型，1支付宝 2 银行卡 4 微信零钱 5 结算通 6 API打款*/
    var iPresenter=PayAccountPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pay_account)

        type= intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,0)


        accountType = 1
        edit_pay_account_alipay.setBackgroundResource(R.drawable.shape_edit_pay_account_selected_bg)
        edit_pay_account_wepay.setBackgroundResource(R.drawable.shape_edit_pay_account_bg)


        if(intent.hasExtra(Constants.INTENT_BEAN)) {
            payAccount = intent.getSerializableExtra(Constants.INTENT_BEAN) as PayAccount
        }

        header_title.text= if(type==0) "新增提现账号" else "修改提现账号"

        header_left_image.setOnClickListener(this)
        edit_pay_account_alipay.setOnClickListener(this)
        edit_pay_account_wepay.setOnClickListener(this)
        edit_pay_account_default.setOnClickListener(this)
        edit_pay_account_operate.setOnClickListener(this)

        if(payAccount!=null){
            edit_pay_account_name.setText( payAccount!!.RealName)
            accountType = payAccount!!.AccountType
            if(accountType==1){
                edit_pay_account_alipay.setBackgroundResource(R.drawable.shape_edit_pay_account_selected_bg)
                edit_pay_account_wepay.setBackgroundResource(R.drawable.shape_edit_pay_account_bg)
                edit_pay_account_lay_account.visibility=View.VISIBLE
                edit_pay_account_account.setText(payAccount!!.AccountInfo)
            }else if(accountType==4) {
                edit_pay_account_alipay.setBackgroundResource(R.drawable.shape_edit_pay_account_bg)
                edit_pay_account_wepay.setBackgroundResource(R.drawable.shape_edit_pay_account_selected_bg)
                edit_pay_account_lay_account.visibility = View.GONE
            }
        }

        edit_pay_account_name.requestFocus()
        KeybordUtils.openKeybord(this, edit_pay_account_name)
    }

    override fun onDestroy() {
        super.onDestroy()
        KeybordUtils.closeKeyboard(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                KeybordUtils.closeKeyboard(this)
                finish()
            }
            R.id.edit_pay_account_alipay->{
                accountType= 1
                edit_pay_account_lay_account.visibility=View.VISIBLE
                edit_pay_account_alipay.setBackgroundResource(R.drawable.shape_edit_pay_account_selected_bg)
                edit_pay_account_wepay.setBackgroundResource(R.drawable.shape_edit_pay_account_bg)
            }
            R.id.edit_pay_account_wepay->{
                accountType = 4
                edit_pay_account_lay_account.visibility=View.GONE
                edit_pay_account_alipay.setBackgroundResource(R.drawable.shape_edit_pay_account_bg)
                edit_pay_account_wepay.setBackgroundResource(R.drawable.shape_edit_pay_account_selected_bg)
            }
            R.id.edit_pay_account_default->{
                isDefault=!isDefault
                var draw = ContextCompat.getDrawable(this , R.mipmap.check2)
                draw!!.setBounds(0,0,48,48)
                var draw2 = ContextCompat.getDrawable(this , R.mipmap.uncheck2)
                draw2!!.setBounds(0,0,48,48)
                edit_pay_account_default.setCompoundDrawables( if(isDefault) draw else draw2 , null,null,null )
            }
            R.id.edit_pay_account_operate->{
                save()
            }
        }
    }

    private fun save(){
        var realname = edit_pay_account_name.text.toString().trim()
        var account = edit_pay_account_account.text.toString().trim()


        if(TextUtils.isEmpty(realname)){
            toast("请输入姓名")
            return
        }
        if(accountType==1 && TextUtils.isEmpty(account)){
            toast("请输入支付宝账号")
            return
        }

        if(payAccount==null){
            payAccount=PayAccount( 0L, realname , if(isDefault) 1 else 0 , account ,accountType , account ,false )
        }else{
            payAccount!!.AccountInfo = account
            payAccount!!.IsDefault = if( isDefault) 1 else 0
            payAccount!!.RealName=realname
            payAccount!!.AccountType = accountType
            payAccount!!.Account = account
        }
        iPresenter.editPayAccount( payAccount!! )
    }


    override fun getAccountListCallback(apiResult: ApiResult<ArrayList<PayAccount>>) {

    }

    override fun setDefaultAccountCallback(apiResult: ApiResult<Any>) {

    }

    override fun editPayAccountCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun deleteAccountCallback(apiResult: ApiResult<Any>) {

    }
}
