package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FrozenFlowAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.PayLoanContract
import com.huotu.android.mifang.mvp.presenter.PayLoanPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_frozen_flow.*
import kotlinx.android.synthetic.main.layout_header.*

/**
 * 冻结货款流水
 */
class FrozenFlowActivity : BaseActivity<PayLoanContract.Presenter>()
    ,PayLoanContract.View
        ,BaseQuickAdapter.RequestLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener
    , View.OnClickListener{
    var iPresenter=PayLoanPresenter(this)
    var pageIndex=0
    var isShowProgress=true
    var frozenFlowAdapter:FrozenFlowAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frozen_flow)

        header_left_image.setOnClickListener(this)

        frozen_flow_refreshview.setOnRefreshListener(this)
        frozenFlowAdapter = FrozenFlowAdapter(ArrayList())
        frozen_flow_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this, R.color.line_color), 1f))
        frozen_flow_recyclerview.layoutManager=LinearLayoutManager(this)
        frozen_flow_recyclerview.adapter = frozenFlowAdapter
        frozenFlowAdapter!!.setOnLoadMoreListener(this, frozen_flow_recyclerview)

        iPresenter.getFrozenFlow(pageIndex+1 , Constants.PAGE_SIZE)
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress) {
            frozen_flow_progress.visibility = View.VISIBLE
        }else{
            frozen_flow_progress.visibility = View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        frozen_flow_progress.visibility=View.GONE
        frozen_flow_refreshview.isRefreshing=false
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }
    }

    override fun onRefresh() {
        isShowProgress=false
        pageIndex=0
        iPresenter.getFrozenFlow(pageIndex+1,Constants.PAGE_SIZE)
    }

    override fun onLoadMoreRequested() {
        isShowProgress=false
        iPresenter.getFrozenFlow(pageIndex+1,Constants.PAGE_SIZE)
    }

    override fun getFrozenFlowCallback(apiResult: ApiResult<ArrayList<FrozenFlow>>) {

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
                frozenFlowAdapter!!.loadMoreEnd(true)
            } else {
                frozenFlowAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            frozenFlowAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            frozenFlowAdapter!!.setNewData(apiResult.data)
            frozenFlowAdapter!!.disableLoadMoreIfNotFullPage( frozen_flow_recyclerview)
        } else {
            frozenFlowAdapter!!.addData(apiResult.data!!)
        }
    }

    override fun getDepositListCallback(apiResult: ApiResult<ArrayList<PayLoanBean>>) {

    }
}
