package com.huotu.android.mifang.activity


import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ScoreTypeEnum
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.layout_header.*

class MyWalletActivity : BaseActivity<IPresenter>() , View.OnClickListener{

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
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.mywallet_cash->{
                newIntent<CashActivity>()
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
            R.id.mywallet_cash->{
                toast("提现sssss")
            }
        }
    }
}
