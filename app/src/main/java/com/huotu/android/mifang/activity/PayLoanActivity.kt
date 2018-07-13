package com.huotu.android.mifang.activity

import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.activity_payloan.*
import kotlinx.android.synthetic.main.layout_header_2.*


class PayLoanActivity : BaseActivity<IPresenter>() ,View.OnClickListener{

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
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_text->{
                newIntent<PayLoanFlowActivity>()
            }
            R.id.payloan_pay->{
                //todo
            }
        }
    }
}
