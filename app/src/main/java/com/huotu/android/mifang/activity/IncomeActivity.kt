package com.huotu.android.mifang.activity

import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.R.id.*
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.ProfitContract
import com.huotu.android.mifang.mvp.presenter.ProfitPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.activity_income.*

class IncomeActivity : BaseActivity<ProfitContract.Presenter>()
        ,ProfitContract.View
        ,View.OnClickListener {
    var iPresenter=ProfitPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        header_title.text="收益中心"
        header_left_image.setOnClickListener(this)

        income_lay_day.setOnClickListener(this)
        income_lay_month.setOnClickListener(this)
        income_lay_week.setOnClickListener(this)

        iPresenter.getProfitIndex()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.income_lay_day->{
                newIntent<IncomeDetailActivity>(Constants.INTENT_OPERATE_TYPE , 0)
            }
            R.id.income_lay_week->{
                newIntent<IncomeDetailActivity>(Constants.INTENT_OPERATE_TYPE , 1)
            }
            R.id.income_lay_month->{
                newIntent<IncomeDetailActivity>(Constants.INTENT_OPERATE_TYPE , 2)
            }
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        income_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        income_progress.visibility=View.GONE
    }

    override fun getProfitItemsCallback(apiResult: ApiResult<ArrayList<ProfitItemBean>>) {

    }

    override fun getProfitIndexCallback(apiResult: ApiResult<ProfitIndexBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("数据格式错误")
            return
        }

        var data =apiResult.data!!
        income_balance.text = (data.UserIntegral/100).toString()
        income_waitaccount.text = data.UserTempIntegral.toString()

        income_today_income.text = data.UserProfitByToday.toString()+"元"
        income_today_count.text = "预估"+ data.UserOrderNumByToday.toString()+"单"

        income_yestoday_income.text = data.UserProfitByYesterday.toString()+"元"
        income_yestoday_count.text =  "预估"+data.UserOrderNumByYesterday.toString()+"单"

        income_this_week_income.text = data.UserProfitByWeek.toString()+"元"
        income_this_week_count.text =  "预估"+data.UserOrderNumByWeek.toString()+"单"

        income_yestoday_week_income.text = data.UserProfitByLastWeek.toString()+"元"
        income_yestoday_week_count.text =  "预估"+data.UserOrderNumByLastWeek.toString()+"单"

        income_month_income.text = data.UserProfitByMonth.toString()+"元"
        income_month_count.text =  "预估"+data.UserOrderNumByMonth.toString()+"单"

        income_yestoday_month_income.text = data.UserProfitByLastMonth.toString()+"元"
        income_yestoday_month_count.text =  "预估"+data.UserOrderNumByLastMonth.toString()+"单"

    }
}
