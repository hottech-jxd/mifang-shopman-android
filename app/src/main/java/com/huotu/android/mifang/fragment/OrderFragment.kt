package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.OrderActivity
import com.huotu.android.mifang.adapter.OrderAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.OrderContract
import com.huotu.android.mifang.mvp.presenter.OrderPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_order.*

const val ARG_YEAR = "year"
const val ARG_MONTH = "month"
const val ARG_DAY = "day"
const val ARG_ORDER_SOURCE_TYPE="order_source_type"
/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OrderFragment : BaseFragment<OrderContract.Presenter>()
        ,OrderActivity.OrderFilterListener
        ,SwipeRefreshLayout.OnRefreshListener
        ,OrderContract.View
        ,BaseQuickAdapter.RequestLoadMoreListener{

    var orderAdapter:OrderAdapter?=null
    var data=ArrayList<OrderBean>()
    var iPresenter=OrderPresenter(this)
    var pageIndex=0
    var isShowProgress=true
    var year:Int= -1
    var month:Int = -1
    var day:Int = -1
    var searchTimeType = 2 //查询类型（默认-1全部,0-日,1-周,2-月）
    var weekNum = -1
    var orderSourceType =-1

    private var type: Int = OrderStatusEnum.All.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE , OrderStatusEnum.All.id)
            year = it.getInt(ARG_YEAR )
            month = it.getInt(ARG_MONTH)
            day = it.getInt(ARG_DAY)
            orderSourceType = it.getInt(ARG_ORDER_SOURCE_TYPE , -1 )
        }
    }

    override fun initView() {
        if( orderAdapter==null)
            orderAdapter= OrderAdapter(data)

        var emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null)
        orderAdapter!!.emptyView= emptyView
        //orderAdapter!!.isUseEmpty(false)
        orderAdapter!!.setHeaderAndEmpty(true)

        order_refreshview.setOnRefreshListener(this)
        order_recyclerview.layoutManager=LinearLayoutManager(context)
        order_recyclerview.adapter=orderAdapter
        order_recyclerview.addItemDecoration(RecyclerViewDivider(context!!, ContextCompat.getColor(context!!, R.color.bg_line ) , 10f ))
    }

    override fun onRefresh() {
        pageIndex=0
        isShowProgress=false
        //orderAdapter!!.isUseEmpty(false)
        iPresenter.getProfitOrderList( searchTimeType , type , year , month , day , weekNum ,  orderSourceType ,pageIndex+1 )
    }

    override fun onLoadMoreRequested() {
        //orderAdapter!!.isUseEmpty(false)
        iPresenter.getProfitOrderList( searchTimeType , type , year , month , day , weekNum ,  orderSourceType ,pageIndex+1 )
    }

    override fun fetchData() {
        pageIndex = 0
        isShowProgress=true
        //orderAdapter!!.isUseEmpty(false)
        iPresenter.getProfitOrderList( searchTimeType , type , year , month , day , weekNum , orderSourceType, pageIndex+1 )
    }

    override fun getLayoutResourceId(): Int {
        return  R.layout.fragment_order
    }


    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(order_loading==null) return
        if(isShowProgress) {
            order_loading.visibility = View.VISIBLE
        }else{
            order_loading.visibility = View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        if(order_loading==null)return
        order_loading.visibility=View.GONE
        isShowProgress=false
        order_refreshview.isRefreshing=false
        //orderAdapter!!.isUseEmpty(true)
    }

    override fun filter(year: Int, month: Int, day: Int) {

        this.year = year
        this.month = month
        this.day = day
        pageIndex=0

        if( orderAdapter==null)
            orderAdapter= OrderAdapter(data)

        orderAdapter!!.setNewData(data)

        if(isViewPrepared && isVisibleToUser) {
            //iPresenter.getProfitOrderList()
            isShowProgress=true
            iPresenter.getProfitOrderList( searchTimeType , type , year , month , day , weekNum , orderSourceType, pageIndex+1 )
        }
    }

    override fun getProfitOrderListCallback(apiResult: ApiResult<ArrayList<OrderBean>>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        if (apiResult.data == null) return

        if (  apiResult.data!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                orderAdapter!!.loadMoreEnd(true)
            } else {
                orderAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            orderAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            orderAdapter!!.setNewData(apiResult.data)
            orderAdapter!!.disableLoadMoreIfNotFullPage(order_recyclerview)
        } else {
            orderAdapter!!.addData(apiResult.data!!)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShopperClass1Fragment.
         */
        @JvmStatic
        fun newInstance(type:Int, year: Int , month: Int,day: Int , orderSourceType:Int=-1) =
                OrderFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TYPE, type )
                        putInt(ARG_YEAR, year)
                        putInt(ARG_MONTH , month)
                        putInt(ARG_DAY, day)
                        putInt(ARG_ORDER_SOURCE_TYPE , orderSourceType)
                    }
                }
    }


}
