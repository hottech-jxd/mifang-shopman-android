package com.huotu.android.mifang.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.provider.SyncStateContract
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
import kotlinx.android.synthetic.main.activity_cash.*
import kotlinx.android.synthetic.main.layout_header.*

class CashActivity : BaseActivity<CashContract.Presenter>()
        , CashContract.View
        ,View.OnClickListener {
    var REQUEST_CODE = 6001
    var iPresenter=CashPresenter(this)
    var data:CashIndex?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)

        header_title.text="我要提现"
        header_left_image.setOnClickListener(this)
        cash_record.setOnClickListener(this)
        cash_lay_1.setOnClickListener(this)
        cash_get_all.setOnClickListener(this)

        cash_record.paint.flags = Paint. UNDERLINE_TEXT_FLAG

        iPresenter.cashIndex()

        cash_money.requestFocus()
        KeybordUtils.openKeybord(this, cash_money)
    }

    override fun onDestroy() {
        super.onDestroy()
        KeybordUtils.closeKeyboard(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.cash_record->{
                newIntent<CashRecordActivity>()
            }
            R.id.cash_lay_1->{
                newIntentForResult<PayAccountActivity>( REQUEST_CODE , Constants.INTENT_PAY_ACCOUNT_ID , if(data==null) 0L else  data!!.AccountId)
            }
            R.id.cash_get_all->{
                cash_money.setText( if(data==null) "0.00" else data!!.UserIntegral.toString() )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode== REQUEST_CODE && resultCode == Activity.RESULT_OK){
            var bean = data!!.getSerializableExtra(Constants.INTENT_PAY_ACCOUNT) as PayAccount
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
        if(apiResult.data==null){
            return
        }
        data = apiResult.data
        cash_name.text = apiResult.data!!.UserRealName
        cash_email.text = apiResult.data!!.AccountInfo
        cash_can_get_money.text = apiResult.data!!.UserIntegral.toString()
        cash_money.setText( apiResult.data!!.BaseMoney.toString())

    }

    override fun cashListCallback(apiResult: ApiResult<ArrayList<CashRecord>>) {

    }
}
