package com.huotu.android.mifang.activity

import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.R.id.*
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.activity_income.*

class IncomeActivity : BaseActivity<IPresenter>(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        header_title.text="收益中心"
        header_left_image.setOnClickListener(this)

        income_lay_day.setOnClickListener(this)
        income_lay_month.setOnClickListener(this)
        income_lay_week.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.income_lay_day->{
                newIntent<IncomeDetailActivity>(Constants.INTENT_OPERATE_TYPE , 1)
            }
            R.id.income_lay_week->{
                newIntent<IncomeDetailActivity>(Constants.INTENT_OPERATE_TYPE , 2)
            }
            R.id.income_lay_month->{
                newIntent<IncomeDetailActivity>(Constants.INTENT_OPERATE_TYPE ,3 )
            }
        }
    }
}
