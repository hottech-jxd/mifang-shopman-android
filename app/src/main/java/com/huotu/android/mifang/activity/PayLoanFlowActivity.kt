package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.PayloanAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.PayLoanBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.PayLoanContract
import com.huotu.android.mifang.mvp.presenter.PayLoanPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_cash_record_item.*
import kotlinx.android.synthetic.main.activity_pay_loan_flow.*
import kotlinx.android.synthetic.main.layout_header.*

class PayLoanFlowActivity : BaseActivity<PayLoanContract.Presenter>()
    ,PayLoanContract.View
        ,BaseQuickAdapter.RequestLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener
    , View.OnClickListener{
    var iPresenter=PayLoanPresenter(this)
    var pageIndex=0
    var isShowProgress=true
    var payloanAdapter:PayloanAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_loan_flow)

        header_left_image.setOnClickListener(this)

        payloan_flow_refreshview.setOnRefreshListener(this)
        payloanAdapter = PayloanAdapter(ArrayList())
        payloan_flow_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this, R.color.line_color), 1f))
        payloan_flow_recyclerview.layoutManager=LinearLayoutManager(this)
        payloan_flow_recyclerview.adapter = payloanAdapter
        payloanAdapter!!.setOnLoadMoreListener(this, payloan_flow_recyclerview)

        iPresenter.getDepositList(pageIndex+1 , Constants.PAGE_SIZE)
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress) {
            payloan_flow_progress.visibility = View.VISIBLE
        }else{
            payloan_flow_progress.visibility = View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        payloan_flow_progress.visibility=View.GONE
        payloan_flow_refreshview.isRefreshing=false
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }
    }

    override fun onRefresh() {
        isShowProgress=false
        pageIndex=0
        iPresenter.getDepositList(pageIndex+1,Constants.PAGE_SIZE)
    }

    override fun onLoadMoreRequested() {
        isShowProgress=false
        iPresenter.getDepositList(pageIndex+1,Constants.PAGE_SIZE)
    }

    override fun getDepositListCallback(apiResult: ApiResult<ArrayList<PayLoanBean>>) {
        hideProgress()
        isShowProgress=false
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if (apiResult.data == null) return

        if (  apiResult.data!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                payloanAdapter!!.loadMoreEnd(true)
            } else {
                payloanAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            payloanAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            payloanAdapter!!.setNewData(apiResult.data)
            payloanAdapter!!.disableLoadMoreIfNotFullPage( payloan_flow_recyclerview)
        } else {
            payloanAdapter!!.addData(apiResult.data!!)
        }
    }
}
