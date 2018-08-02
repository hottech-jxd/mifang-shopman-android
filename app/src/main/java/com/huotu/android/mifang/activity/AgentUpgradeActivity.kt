package com.huotu.android.mifang.activity


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.GoodsDetailAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.GoodsContract
import com.huotu.android.mifang.mvp.presenter.GoodsPresenter
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.FrescoImageLoader
import kotlinx.android.synthetic.main.activity_agentupgrade.*
import kotlinx.android.synthetic.main.layout_agentupgrade_top.*

class AgentUpgradeActivity : BaseActivity<GoodsContract.Presenter>()
        ,GoodsContract.View
        ,View.OnClickListener {
    var detailAdapter: GoodsDetailAdapter?=null
    var data:GoodsDetailBean?=null
    var images1 = ArrayList<String>()
    var images2 = ArrayList<String?>()
    var iPresenter = GoodsPresenter(this)
    var goodsId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agentupgrade)
        initView()
    }

    private fun initView(){

        if(intent.hasExtra(Constants.INTENT_GOODSID)){
            goodsId = intent.getLongExtra(Constants.INTENT_GOODSID, 0L)
        }


        header_left_image.setOnClickListener(this)

        agentupgrade_recyclerView.layoutManager=LinearLayoutManager(this)
        detailAdapter=GoodsDetailAdapter(images2)

        var top = LayoutInflater.from(this).inflate(R.layout.layout_agentupgrade_top, null)

        detailAdapter!!.removeAllHeaderView()
        detailAdapter!!.addHeaderView(top)


        agentupgrade_recyclerView.adapter = detailAdapter

        iPresenter.agentUpgrade(goodsId)
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        agentupgrade_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        agentupgrade_progress.visibility=View.GONE
    }

    override fun agentUpgradeCallback(apiResult: ApiResult<GoodsDetailBean>) {

        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        var data = apiResult.data

        if( data ==null) return

        if( !TextUtils.isEmpty( data.pictures)) {
            images1 = ArrayList( data!!.pictures!!.split(",") )
        }

        var sw = DensityUtils.getScreenWidth(this)
        agentupgrade_images.setImageLoader(FrescoImageLoader( agentupgrade_images , sw , sw ))
        agentupgrade_images.setImages(images1)
        if(images1.size>1) {
            agentupgrade_images.start()
        }

        agentupgrade_item_title.text= data.title
        agentupgrade_item_price.text ="￥"+ data.price
        agentupgrade_item_memo.text= data.memo

        if(data.intro!=null) {
            images2 = data.intro!!
        }

        detailAdapter!!.setNewData(images2)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
        }
    }

    override fun getStoreIndexCallback(apiResult: ApiResult<StoreIndex>) {

    }

    override fun getShopperAccountInfoCallback(apiResult: ApiResult<ShopperAccountInfo>) {

    }

    override fun getGoodsInfoCallback(apiResult: ApiResult<GoodsDetailBean>) {

    }

    override fun getStoreInfoCallback(apiResult: ApiResult<ShopperInfo>) {

    }
}
