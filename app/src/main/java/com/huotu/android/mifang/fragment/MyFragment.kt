package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.*
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.base.GlideApp
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.presenter.MyPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.FrescoImageLoader
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyFragment : BaseFragment<MyContract.Presenter>()
        ,MyContract.View
        ,OnBannerListener
        ,View.OnClickListener {

    var myPresenter=MyPresenter(this)
    var myBean :MyBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun initView() {

        my_setting.setOnClickListener(this)
        my_qrcode.setOnClickListener(this)
        my_repay.setOnClickListener(this)
        my_lay_message.setOnClickListener(this)
        my_lay_feedback.setOnClickListener(this)
        my_lay_set.setOnClickListener(this)
        my_lay_shopper.setOnClickListener(this)
        my_lay_aboutme.setOnClickListener(this)
        my_lay_wallet.setOnClickListener(this)
        my_lay_income.setOnClickListener(this)
        my_lay_order.setOnClickListener(this)
        my_lay_myterm.setOnClickListener(this)
        my_lay_waitaccounts.setOnClickListener(this)
        my_lay_balance.setOnClickListener(this)
        my_lay_mibean.setOnClickListener(this)
        my_banner.setOnBannerListener(this)
    }

    override fun fetchData() {
        myPresenter.myIndex()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_my
    }

    override fun OnBannerClick(position: Int) {
        if(myBean==null)return
        if(myBean!!.ADList==null) return

        var url  =myBean!!.ADList!![position].LinkURL
        newIntent<WebActivity>(Constants.INTENT_URL , url)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.my_lay_message->{
                newIntent<MessageActivity>()
            }
            R.id.my_lay_feedback->{
                newIntent<FeedbackActivity>()
            }
            R.id.my_setting,
            R.id.my_lay_set->{
                newIntent<SettingActivity>()
            }
            R.id.my_lay_shopper->{
                newIntent<ShopperClassActivity>()
            }
            R.id.my_lay_aboutme->{
                newIntent<WebActivity>( Constants.INTENT_URL , "http://www.baidu.com")
            }
            R.id.my_lay_wallet->{
                newIntent<MyWalletActivity>()
            }
            R.id.my_lay_income->{
                newIntent<IncomeActivity>()
            }
            R.id.my_lay_order->{
                newIntent<OrderActivity>()
            }
            R.id.my_lay_myterm->{
                newIntent<MyTermActivity>()
            }
            R.id.my_qrcode->{
                newIntent<WebActivity>(Constants.INTENT_URL , "http://www.baidu.com")
            }
            R.id.my_repay->{
                newIntent<BuyActivity>(Constants.INTENT_OPERATE_TYPE,1)
            }
            R.id.my_lay_waitaccounts->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE, ScoreTypeEnum.WaitAccounts.id)
            }
            R.id.my_lay_balance->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE, ScoreTypeEnum.Balance.id)
            }
            R.id.my_lay_mibean->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE,ScoreTypeEnum.MiBean.id)
            }
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        my_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        my_progress.visibility=View.GONE
    }

    override fun myIndexCallback(apiResult: ApiResult<MyBean>) {
        my_progress.visibility=View.GONE
        if(processCommonErrorCode(apiResult.code , apiResult.msg)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        myBean = apiResult.data
        if(myBean == null)return

        my_name.text =myBean!!.WxNickName
        //GlideApp.with(context!!).load(myBean.WxHeadImg).into(my_avator)
        my_avator.setImageURI(myBean!!.WxHeadImg)

        my_account_type.text = myBean!!.LevelName
        my_ExpireTime.text="到期时间:"+ myBean!!.ExpireTime +"(剩"+ myBean!!.SurplusDays +"天)"
        my_waitaccounts.text = myBean!!.UserTempIntegral.toString()
        my_balance.text = (myBean!!.UserIntegral/100).toString()
        my_mibean.text = myBean!!.UserMBean.toString()

        setAdBanner(myBean!!.ADList)
    }


    private fun setAdBanner( ads :ArrayList<MyBean.ADBean>?){
        if(ads==null||ads.size<1){
            my_banner.stopAutoPlay()
            my_banner.visibility=View.GONE
            return
        }else{
            my_banner.visibility=View.VISIBLE
        }

        my_banner.setDelayTime(3500)
        my_banner.setImageLoader(FrescoImageLoader( my_banner , DensityUtils.getScreenWidth(context!!)))
        my_banner.setImages(ads)
        my_banner.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        my_banner.stopAutoPlay()
    }

    override fun mySettingCallback(apiResult: ApiResult<SettingBean>) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                MyFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
