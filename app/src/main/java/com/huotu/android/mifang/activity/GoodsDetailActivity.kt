package com.huotu.android.mifang.activity


import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.GoodsDetailAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.GoodsContract
import com.huotu.android.mifang.mvp.presenter.GoodsPresenter
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.FrescoImageLoader
import kotlinx.android.synthetic.main.activity_goodsdetail.*
import kotlinx.android.synthetic.main.layout_goodsdetail_top.*
import java.math.BigDecimal

class GoodsDetailActivity : BaseActivity<GoodsContract.Presenter>()
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
        setContentView(R.layout.activity_goodsdetail)
        initView()
    }

    private fun initView(){

        if(intent.hasExtra(Constants.INTENT_GOODSID)){
            goodsId = intent.getLongExtra(Constants.INTENT_GOODSID, 0L)
        }


        header_left_image.setOnClickListener(this)
        goodsdetail_share.setOnClickListener(this)

        goodsdetail_recyclerView.layoutManager=LinearLayoutManager(this)
        detailAdapter=GoodsDetailAdapter(images2)

        var top = LayoutInflater.from(this).inflate(R.layout.layout_goodsdetail_top, null)

        detailAdapter!!.removeAllHeaderView()
        detailAdapter!!.addHeaderView(top)


        goodsdetail_recyclerView.adapter = detailAdapter

        iPresenter.getGoodsInfo(goodsId)
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        goodsdetail_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        goodsdetail_progress.visibility=View.GONE
    }

    override fun getGoodsInfoCallback(apiResult: ApiResult<GoodsDetailBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        data = apiResult.data

        if( data ==null) return

        if( !TextUtils.isEmpty( data!!.pictures)) {
            images1 = ArrayList(  data!!.pictures!!.split(",") )
        }

        var sw = DensityUtils.getScreenWidth(this)
        goodsdetail_images.setImageLoader(FrescoImageLoader( goodsdetail_applayout , goodsdetail_images , sw , sw , R.mipmap.avator ))
        goodsdetail_images.setImages(images1)
        if(images1.size>1) {
            goodsdetail_images.isAutoPlay(false)
        }else{
            goodsdetail_images.isAutoPlay(true)
        }
        goodsdetail_images.start()

        goodsdetail_item_title.text= data!!.title
        goodsdetail_item_price.text = "￥"+data!!.price!!.stripTrailingZeros().toPlainString()
        var commission = data!!.commission
        commission!!.setScale(2,BigDecimal.ROUND_HALF_UP)
        commission = commission.divide(BigDecimal(100))

        var cprice:String?
        if(BaseApplication.instance!!.variable!!.userBean!=null && BaseApplication.instance!!.variable.userBean!!.userRoleType == 102){
            cprice = "代理价:<font color='#E41637'>" + data!!.agentPrcie!!.stripTrailingZeros().toPlainString() +"</font>元"
        }else {
            cprice = "佣金:<font color = '#E41637'>"+commission.stripTrailingZeros().toPlainString()+"</font>元"
        }
        goodsdetail_item_price2.text= Html.fromHtml(cprice )

        if(data!!.intro!=null) {
            images2 = data!!.intro!!
        }

        detailAdapter!!.setNewData(images2)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.header_left_image -> {
                finish()
            }
            R.id.goodsdetail_share -> {
                share()
            }
        }
    }

    private fun share(){
        if(data==null)return

//        var intent = Intent(Intent.ACTION_SEND)
//        intent.type = "text/html"
//        //intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getLocalImages( quan.dataId ))
//        intent.putExtra(Intent.EXTRA_SUBJECT, data!!.title )
//        //intent.putExtra(Intent.EXTRA_TEXT, data!!.title + data!!.shareUrl )
//        intent.putExtra(Intent.EXTRA_TITLE, data!!.title)
//        //intent.putExtra(Intent.EXTRA_STREAM , ImageUtils.getUriByFile(this, data!!.shareUrl ))
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//
//        startActivity(Intent.createChooser(intent, "分享") )



        var shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setSubject(data!!.title)
                //.setHtmlText( "<a href='${data!!.shareUrl}'>${data!!.title}</a>")
                .setText( data!!.title + data!!.shareUrl )
                .intent

        if(shareIntent.resolveActivity(this.packageManager) !=null){
            startActivity(shareIntent)
        }

    }

    override fun getStoreIndexCallback(apiResult: ApiResult<StoreIndex>) {

    }

    override fun getShopperAccountInfoCallback(apiResult: ApiResult<ShopperAccountInfo>) {

    }

    override fun agentUpgradeCallback(apiResult: ApiResult<GoodsDetailBean>) {

    }

    override fun getStoreInfoCallback(apiResult: ApiResult<ShopperInfo>) {

    }
}
