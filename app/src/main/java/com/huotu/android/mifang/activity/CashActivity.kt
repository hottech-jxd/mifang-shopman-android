package com.huotu.android.mifang.activity

import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.activity_cash.*
import kotlinx.android.synthetic.main.layout_header.*

class CashActivity : BaseActivity<IPresenter>(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)

        header_title.text="我要提现"
        header_left_image.setOnClickListener(this)
        cash_record.setOnClickListener(this)
        cash_lay_1.setOnClickListener(this)
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
                newIntent<PayAccountActivity>()
            }
        }
    }
}
