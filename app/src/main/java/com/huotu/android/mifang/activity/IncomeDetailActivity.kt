package com.huotu.android.mifang.activity

import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.IncomeDetailAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.ProfitContract
import com.huotu.android.mifang.mvp.presenter.ProfitPresenter
import com.huotu.android.mifang.widget.DateDialog
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_income_detail.*
import kotlinx.android.synthetic.main.layout_header_2.*
import java.math.BigDecimal
import java.util.*

class IncomeDetailActivity : BaseActivity<ProfitContract.Presenter>()
        , ProfitContract.View
        , SwipeRefreshLayout.OnRefreshListener
        , DateDialog.OnOperateListener
        ,View.OnClickListener{
    var incomeDetailAdapter :IncomeDetailAdapter?=null
    var data =ArrayList<ProfitItemEntity>()
    var type = 1
    var iPresenter=ProfitPresenter(this)
    var searchYear:Int=0
    var searchMonth= 0
    var isShowProgress=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_detail)

        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,1)
        }


        header_right_image.visibility= View.GONE
        header_left_image.setOnClickListener(this)
        header_right_text.setOnClickListener(this)

        income_detail_refreshview.setOnRefreshListener(this)

        income_detail_recyclerview.layoutManager=LinearLayoutManager(this)
        incomeDetailAdapter = IncomeDetailAdapter(data)


        income_detail_recyclerview.adapter = incomeDetailAdapter

        income_detail_recyclerview.addItemDecoration( RecyclerViewDecoration(this , ContextCompat.getColor(this , R.color.line_color ) ,data) )

        searchYear = Calendar.getInstance().get(Calendar.YEAR)
        searchMonth = Calendar.getInstance().get(Calendar.MONTH)+1

        setYearMonth(searchYear , searchMonth   )

    }

    private fun setYearMonth(year:Int , month:Int){

        searchYear = year
        searchMonth = month

        when(type) {
            0 -> {
                header_title.text = "每日收益"
                header_right_text.text = year.toString() + "年" + month + "月"
            }
            1 -> {
                header_title.text = "每周收益"
                header_right_text.text = year.toString() + "年" + month + "月"
            }
            2 -> {
                header_title.text = "每月收益"
                header_right_text.text = year.toString() + "年"
            }
        }

        iPresenter.getProfitItems(type , searchYear , searchMonth )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_text->{
                filter()
            }
        }
    }

    override fun onRefresh() {
        isShowProgress=false
        iPresenter.getProfitItems(type , searchYear , searchMonth)
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress) {
            income_detail_progress.visibility = View.VISIBLE
        }else{
            income_detail_progress.visibility=View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        income_detail_progress.visibility= View.GONE
        income_detail_refreshview.isRefreshing=false
        isShowProgress=false
    }

    private fun filter(){
        var dialog = DateDialog(this, this)
        dialog.show( searchYear , searchMonth , 1 ,type != 2 , false)
    }

    override fun operate(year: Int, month: Int , day:Int) {
        isShowProgress=true
        setYearMonth(year ,month )
    }

    override fun getProfitIndexCallback(apiResult: ApiResult<ProfitIndexBean>) {
    }

    override fun getProfitItemsCallback(apiResult: ApiResult<ArrayList<ProfitItemBean>>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("数据错误")
            return
        }

        data.clear()
        for( bean in apiResult.data!!) {
            var item = ProfitItemEntity(bean.ProfitTime, "订单数", "收益" , 1 )
            data.add(item)

            var profit =  bean.ProfitIntegral.setScale(2,BigDecimal.ROUND_HALF_UP).divide(BigDecimal(100))

            item = ProfitItemEntity("获取返利", bean.OrderNum.toString(), profit.stripTrailingZeros().toPlainString() , 2 )
            data.add(item)
        }
        incomeDetailAdapter!!.setNewData(data)
    }

    class RecyclerViewDecoration(context: Context, @ColorInt var dividerColor: Int, var data :ArrayList<ProfitItemEntity> )
        : Y_DividerItemDecoration(context){

        override fun getDivider(itemPosition: Int): Y_Divider {
            when ( data[itemPosition].type ) {
                1 -> {
                    return Y_DividerBuilder()
                            .setBottomSideLine(true, dividerColor, 1f, 0f, 0f)
                            .create()
                }else -> {
                    return Y_DividerBuilder()
                            .setBottomSideLine(true, dividerColor , 6f, 0f,0f)
                            .create()
                }
            }
        }

    }
}
