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
import kotlinx.android.synthetic.main.fragment_my.*
import java.math.BigDecimal

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

        var userIntegral = data!!.UserIntegral
        userIntegral.setScale(2, BigDecimal.ROUND_HALF_UP)
        userIntegral = userIntegral.divide(BigDecimal(100))
        income_balance.text = userIntegral.toString()

        var userTempIntegral = data!!.UserTempIntegral
        userTempIntegral.setScale(2, BigDecimal.ROUND_HALF_UP)
        userTempIntegral = userTempIntegral.divide(BigDecimal(100))
        income_waitaccount.text = userTempIntegral.toString()


        var userProfitByToday = data.UserProfitByToday
        userProfitByToday.setScale(2, BigDecimal.ROUND_HALF_UP)
        userProfitByToday = userProfitByToday.divide(BigDecimal(100))

        income_today_income.text = userProfitByToday.toString()+"元"
        income_today_count.text = "预估"+ data.UserOrderNumByToday.toString()+"单"


        var userProfitByYesterday = data.UserProfitByYesterday
        userProfitByYesterday.setScale(2, BigDecimal.ROUND_HALF_UP)
        userProfitByYesterday = userProfitByYesterday.divide(BigDecimal(100))

        income_yestoday_income.text = userProfitByYesterday.toString()+"元"
        income_yestoday_count.text =  "预估"+data.UserOrderNumByYesterday.toString()+"单"


        var userProfitByWeek = data.UserProfitByWeek
        userProfitByWeek.setScale(2, BigDecimal.ROUND_HALF_UP)
        userProfitByWeek = userProfitByWeek.divide(BigDecimal(100))


        income_this_week_income.text = userProfitByWeek.toString()+"元"
        income_this_week_count.text =  "预估"+data.UserOrderNumByWeek.toString()+"单"

        var userProfitByLastWeek = data.UserProfitByLastWeek
        userProfitByLastWeek.setScale(2, BigDecimal.ROUND_HALF_UP)
        userProfitByLastWeek = userProfitByLastWeek.divide(BigDecimal(100))

        income_yestoday_week_income.text =userProfitByLastWeek.toString()+"元"
        income_yestoday_week_count.text =  "预估"+data.UserOrderNumByLastWeek.toString()+"单"

        var userProfitByMonth = data.UserProfitByMonth
        userProfitByMonth.setScale(2, BigDecimal.ROUND_HALF_UP)
        userProfitByMonth = userProfitByMonth.divide(BigDecimal(100))

        income_month_income.text = userProfitByMonth.toString()+"元"
        income_month_count.text =  "预估"+data.UserOrderNumByMonth.toString()+"单"


        var userProfitByLastMonth = data.UserProfitByLastMonth
        userProfitByLastMonth.setScale(2, BigDecimal.ROUND_HALF_UP)
        userProfitByLastMonth = userProfitByLastMonth.divide(BigDecimal(100))
        
        income_yestoday_month_income.text = userProfitByLastMonth.toString()+"元"
        income_yestoday_month_count.text =  "预估"+data.UserOrderNumByLastMonth.toString()+"单"

    }
}
