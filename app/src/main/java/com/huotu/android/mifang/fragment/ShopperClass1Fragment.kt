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
import com.huotu.android.mifang.activity.WebActivity
import com.huotu.android.mifang.adapter.ShopperClassAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.ShopperClassContract
import com.huotu.android.mifang.mvp.presenter.ShopperClassPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.widget.RecyclerViewDivider
import com.huotu.android.mifang.widget.WebView
import kotlinx.android.synthetic.main.activity_my_term_detail.*
import kotlinx.android.synthetic.main.fragment_shopper_class1.*
/**
 * A simple [Fragment] subclass.
 * Use the [ShopperClass1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShopperClass1Fragment : BaseFragment<ShopperClassContract.Presenter>()
        ,BaseQuickAdapter.RequestLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener
        ,BaseQuickAdapter.OnItemClickListener
        ,ShopperClassContract.View{

    var shopperClassAdapter:ShopperClassAdapter?=null
    private var data=ArrayList<ShopperClassBean>()
    private var iPresenter=ShopperClassPresenter(this)

    private var type: Int = 0
    private var pageIndex=0
    private var isShowProgress= true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE , 0)

        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress) {
            shopperclass_loading.visibility = View.VISIBLE
        }else{
            shopperclass_loading.visibility=View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()

        isShowProgress=false
        shopperclass_refreshview.isRefreshing=false
        shopperclass_loading.visibility = View.GONE
        shopperClassAdapter!!.isUseEmpty(true)
    }

    override fun initView() {
        shopperclass_refreshview.setOnRefreshListener(this)

        if( shopperClassAdapter==null)
            shopperClassAdapter= ShopperClassAdapter(data)

        var emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null)
        shopperClassAdapter!!.setOnLoadMoreListener(this, shopperclass_recyclerview)
        shopperClassAdapter!!.onItemClickListener=this
        shopperClassAdapter!!.emptyView = emptyView
        shopperClassAdapter!!.isUseEmpty(false)

        shopperclass_recyclerview.layoutManager=LinearLayoutManager(context)
        shopperclass_recyclerview.addItemDecoration(RecyclerViewDivider(context!!, ContextCompat.getColor(context!!, R.color.bg_line ) , 1f ))
        shopperclass_recyclerview.adapter=shopperClassAdapter
    }

    override fun fetchData() {
        pageIndex = 0
        isShowProgress=true
        shopperClassAdapter!!.isUseEmpty(false)
        iPresenter.getList(type , pageIndex +1)
    }

    override fun getLayoutResourceId(): Int {
        return  R.layout.fragment_shopper_class1
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var link = (adapter!!.getItem(position) as ShopperClassBean ).outerLink
        newIntent<WebActivity>(Constants.INTENT_URL , link)
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun onLoadMoreRequested() {

        iPresenter.getList(type , pageIndex +1)

    }

    override fun getCategorysCallback(apiResult: ApiResult<ArrayList<ShopperClassCategoryBean>>) {

    }

    override fun getListCallback(apiResult: ApiResult<ArrayList<ShopperClassBean>>) {

        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        if ( apiResult.data ==null || apiResult.data!!.size < 1) {
            //没有数据了
            if (pageIndex == 0) {
                shopperClassAdapter!!.loadMoreEnd(true)
            } else {
                shopperClassAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            shopperClassAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            shopperClassAdapter!!.setNewData(apiResult.data!!)
            shopperClassAdapter!!.disableLoadMoreIfNotFullPage( shopperclass_recyclerview )
        } else {
            shopperClassAdapter!!.addData(apiResult.data!!)
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(type:Int) =
                ShopperClass1Fragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TYPE, type )

                    }
                }
    }
}
