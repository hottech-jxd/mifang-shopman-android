package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.CashRecordAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.CashContract
import com.huotu.android.mifang.mvp.presenter.CashPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_cash_record_item.*
import kotlinx.android.synthetic.main.fragment_quan_tab.*
import kotlinx.android.synthetic.main.layout_header.*

class CashRecordActivity : BaseActivity<CashContract.Presenter>()
        ,CashContract.View
        ,BaseQuickAdapter.RequestLoadMoreListener
        ,View.OnClickListener{
    var data=ArrayList<CashRecord>()
    var cashRecordAdapter:CashRecordAdapter?=null
    var emptyView:View?=null
    var iPresenter=CashPresenter(this)
    var pageIndex = 0
    var isShowProgress=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_record_item)

        header_title.text="提现记录"
        header_left_image.setOnClickListener(this)

        cash_record_recyclerview.layoutManager=LinearLayoutManager(this)

        cashRecordAdapter=CashRecordAdapter(data)
        cashRecordAdapter!!.setOnLoadMoreListener(this, cash_record_recyclerview)
        cash_record_recyclerview.adapter = cashRecordAdapter
        cash_record_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this, R.color.bg_line), 1f))

        emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, cash_record_recyclerview.getParent() as ViewGroup , false )
        cashRecordAdapter!!.emptyView = emptyView

        iPresenter.cashList( pageIndex+1 )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress) {
            cash_record_progress.visibility = View.VISIBLE
        }else{
            cash_record_progress.visibility = View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        cash_record_progress.visibility=View.GONE
    }

    override fun cashListCallback(apiResult: ApiResult<ArrayList<CashRecord>>) {
        hideProgress()
        isShowProgress=false
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if (apiResult.data == null) return

        if (  apiResult.data!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                cashRecordAdapter!!.loadMoreEnd(true)
            } else {
                cashRecordAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            cashRecordAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            cashRecordAdapter!!.setNewData(apiResult.data)
            cashRecordAdapter!!.disableLoadMoreIfNotFullPage(cash_record_recyclerview)
        } else {
            cashRecordAdapter!!.addData(apiResult.data!!)
        }

    }

    override fun onLoadMoreRequested() {
        isShowProgress=false
        iPresenter.cashList( pageIndex+1 )
    }

    override fun cashIndexCallback(apiResult: ApiResult<CashIndex>) {

    }
}
