package com.huotu.android.mifang.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.WalletContract
import com.huotu.android.mifang.mvp.presenter.WalletPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.newIntentForResult
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.layout_header.*
import java.math.BigDecimal
import java.text.NumberFormat

class MyWalletActivity : BaseActivity<WalletContract.Presenter>()
        ,WalletContract.View
        , View.OnClickListener{
    private var REQUEST_CODE=3003
    var iPresenter = WalletPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        header_title.text="我的钱包"
        header_left_image.setOnClickListener(this)
        mywallet_cash.setOnClickListener(this)

        mywallet_lay_1.setOnClickListener(this)
        mywallet_lay_2.setOnClickListener(this)
        mywallet_lay_3.setOnClickListener(this)
        mywallet_cash.setOnClickListener(this)

        iPresenter.myWallet()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.mywallet_cash->{
                newIntentForResult<CashActivity>(REQUEST_CODE)
            }
            R.id.mywallet_lay_1->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE, ScoreTypeEnum.MiBean.id)
            }
            R.id.mywallet_lay_2->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE, ScoreTypeEnum.Balance.id)
            }
            R.id.mywallet_lay_3->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE,ScoreTypeEnum.WaitAccounts.id)
            }

        }
    }

    override fun myWalletCallback(apiResult: ApiResult<MyWalletBean>) {
        hideProgress()
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("返回的数据不正确")
            return
        }

        var bean = apiResult.data!!

        var mibean = bean.UserMBean
        //mibean.setScale(2, BigDecimal.ROUND_HALF_UP)
        //mibean = mibean.divide(BigDecimal(100))
        //var numberFormat = NumberFormat.getCurrencyInstance()
        //numberFormat.minimumFractionDigits =2//设置数的小数部分所允许的最小位数(如果不足后面补0)
        //numberFormat.maximumFractionDigits =4//设置数的小数部分所允许的最大位数(如果超过会四舍五入)
        mywallet_midou.text = mibean.toString()//numberFormat.format( mibean.toDouble() )

        var balance = bean.UserIntegral
        balance.setScale(2, BigDecimal.ROUND_HALF_UP)
        balance = balance.divide(BigDecimal(100))
        mywallet_yue.text = balance.stripTrailingZeros().toPlainString()

        var tempIntegral = bean.UserTempIntegral
        tempIntegral.setScale(2,BigDecimal.ROUND_HALF_UP)
        tempIntegral = tempIntegral.divide(BigDecimal(100))
        mywallet_waitpay.text= tempIntegral.stripTrailingZeros().toPlainString()

        mywallet_yhq.text = apiResult.data!!.CouponNum.toString()+"张"
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        mywallet_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        mywallet_progress.visibility=View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE && Activity.RESULT_OK== resultCode){
            iPresenter.myWallet()
        }
    }
}
