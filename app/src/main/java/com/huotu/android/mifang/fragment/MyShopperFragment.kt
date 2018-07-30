package com.huotu.android.mifang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.*
import com.huotu.android.mifang.adapter.ShopGoodsAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.GoodsContract
import com.huotu.android.mifang.mvp.presenter.GoodsPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.newIntentForLogin
import com.huotu.android.mifang.util.SPUtils
import com.huotu.android.mifang.util.WechatShareUtil
import com.huotu.android.mifang.widget.RecyclerViewDivider4
import com.huotu.android.mifang.widget.ShareDialog
import kotlinx.android.synthetic.main.fragment_my_shopper.*
import kotlinx.android.synthetic.main.layout_header_3.*


/**
 * A simple [Fragment] subclass.
 * Use the [MyShopperFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyShopperFragment : (BaseFragment<GoodsContract.Presenter>)()
        ,GoodsContract.View
        , ShareDialog.OnOperateListener
        ,SwipeRefreshLayout.OnRefreshListener
        ,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemClickListener
        , View.OnClickListener{

    var shopGoodsAdapter:ShopGoodsAdapter?=null
    var data=ArrayList<GoodsInfoBean>()
    var iPresenter = GoodsPresenter(this)
    var pageIndex= 0
    var isShowProgress = true
    var isFirstOpen=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun initView() {
        header_title.text = "我的小店"
        header_left_image.setImageResource(R.mipmap.myshop)
        header_left_image.setOnClickListener(this)
        header_right_image.setImageResource(R.mipmap.three)
        header_right_image.setOnClickListener(this)

        isFirstOpen = SPUtils.getInstance(context!! , Constants.PREF_FILENAME)
                .readBoolean(Constants.INTENT_FIRST_OPEN_SHOPPER,true)
        header_right_circle.visibility= if(isFirstOpen) View.VISIBLE else View.GONE

        myshopper_menu_cash.setOnClickListener(this)
        myshopper_menu_share.setOnClickListener(this)
        myshopper_menu_flow.setOnClickListener(this)
        myshopper_menu_lay.setOnClickListener(this)
        myshopper_preview.setOnClickListener(this)
        myshopper_freeze.setOnClickListener(this)


        myshopper_refreshview.setOnRefreshListener(this)
        myshopper_recyclerview.layoutManager = GridLayoutManager(context ,2)
        shopGoodsAdapter = ShopGoodsAdapter(data)
        var emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty,null)
        shopGoodsAdapter!!.isUseEmpty(false)
        shopGoodsAdapter!!.emptyView=emptyView
        shopGoodsAdapter!!.onItemClickListener=this
        shopGoodsAdapter!!.setOnLoadMoreListener(this, myshopper_recyclerview)
        myshopper_recyclerview.adapter = shopGoodsAdapter

        myshopper_recyclerview.addItemDecoration(RecyclerViewDivider4(context!! , ContextCompat.getColor(context!! , R.color.white)
                ,10f ))
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var goodsId = (adapter!!.data[position] as GoodsInfoBean).GoodsId
        newIntent<GoodsDetailActivity>(Constants.INTENT_GOODSID , goodsId)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                newIntent<SetShopperActivity>()
            }
            R.id.myshopper_menu_lay,
            R.id.header_right_image->{
                if(isFirstOpen) {
                    isFirstOpen=false
                    header_right_circle.visibility=View.GONE
                    SPUtils.getInstance(context!!, Constants.PREF_FILENAME).writeBoolean(Constants.INTENT_FIRST_OPEN_SHOPPER, isFirstOpen)
                }

                myshopper_menu_lay.visibility = if( myshopper_menu_lay.visibility==View.GONE) View.VISIBLE else View.GONE
            }
            R.id.myshopper_menu_cash->{
                myshopper_menu_lay.visibility=View.GONE
                newIntent<PayLoanActivity>()
            }
            R.id.myshopper_menu_share->{
                myshopper_menu_lay.visibility=View.GONE
                share()
            }
            R.id.myshopper_menu_flow->{//货款流水
                myshopper_menu_lay.visibility=View.GONE
                newIntent<PayLoanFlowActivity>()
            }
            R.id.myshopper_preview->{
                //预览小店
                WechatShareUtil().runMinProgram("pages/index/index")
            }
            R.id.myshopper_freeze->{
                //冻结详情
                newIntentForLogin<FrozenFlowActivity>()
            }
        }
    }

    fun share(){
        var list = ArrayList<KeyValue>()
        list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechat,"微信好友"))
        //list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatmoments ,"微信朋友圈"))
        //list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatfavorite,"微信收藏夹"))
        var shareDialog = ShareDialog(context!! , this , list )
        shareDialog.show()
    }

    override fun operate(keyValue: KeyValue) {

        iPresenter.getStoreInfo()

//        WechatShareUtil().shareMiniProgram( resources ,getString(R.string.app_name) ,"小程序描述",
//                "http://www.baidu.com",
//                BuildConfig.wechat_miniprogram_id ,
//                "pages/index/index" )
    }

    override fun onRefresh() {
        pageIndex=0
        data.clear()
        fetchData()
    }

    override fun onLoadMoreRequested() {
        isShowProgress=false
        iPresenter.getStoreIndex(pageIndex+1, Constants.PAGE_SIZE)
    }

    override fun fetchData() {

        iPresenter.getShopperAccountInfo()

        iPresenter.getStoreIndex(pageIndex+1, Constants.PAGE_SIZE)
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress){
            myshopper_progress.visibility=View.VISIBLE
        }else{
            myshopper_progress.visibility=View.GONE
        }
        shopGoodsAdapter!!.isUseEmpty(false)
    }

    override fun hideProgress() {
        super.hideProgress()
        myshopper_progress.visibility=View.GONE
        myshopper_refreshview.isRefreshing=false
        isShowProgress=false
        shopGoodsAdapter!!.isUseEmpty(true)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_my_shopper
    }

    override fun getGoodsInfoCallback(apiResult: ApiResult<GoodsDetailBean>) {

    }

    override fun getStoreIndexCallback(apiResult: ApiResult<StoreIndex>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if (apiResult.data == null) return

        var data = apiResult.data!!.Rows
        if(data==null) return

        if (  data!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                shopGoodsAdapter!!.loadMoreEnd(true)
            } else {
                shopGoodsAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            shopGoodsAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            shopGoodsAdapter!!.setNewData(data)
            shopGoodsAdapter!!.disableLoadMoreIfNotFullPage(myshopper_recyclerview)
        } else {
            shopGoodsAdapter!!.addData(data)
        }
    }

    override fun getShopperAccountInfoCallback(apiResult: ApiResult<ShopperAccountInfo>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        myshopper_hk.text = apiResult.data!!.GoodsDeposit
    }

    override fun agentUpgradeCallback(apiResult: ApiResult<GoodsDetailBean>) {

    }

    override fun getStoreInfoCallback(apiResult: ApiResult<ShopperInfo>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            return
        }

        var title = apiResult.data!!.ShareTitle
        var desc = apiResult.data!!.ShareContent
        WechatShareUtil().shareMiniProgram( resources , title , desc ,
                "",
                BuildConfig.wechat_miniprogram_id ,
                "pages/index/index" )

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyShopperFragment.
         */
        @JvmStatic
        fun newInstance() =
                MyShopperFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
