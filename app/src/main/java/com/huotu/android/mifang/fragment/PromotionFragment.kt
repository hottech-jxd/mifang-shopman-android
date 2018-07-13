package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.InviteOneActivity
import com.huotu.android.mifang.activity.InviteTwoActivity
import com.huotu.android.mifang.activity.OrderActivity
import com.huotu.android.mifang.adapter.FragmentAdapter
import com.huotu.android.mifang.adapter.PromotionAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.InviteContract
import com.huotu.android.mifang.mvp.presenter.InvitePresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.ShareDialog
import kotlinx.android.synthetic.main.fragment_promotion.*


/**
 * A simple [Fragment] subclass.
 * Use the [PromotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PromotionFragment : BaseFragment<InviteContract.Presenter>()
        ,ShareDialog.OnOperateListener
        ,InviteContract.View
        ,View.OnClickListener{
    var iPresenter = InvitePresenter(this)
    var shareDialog:ShareDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun initView() {

        promotion_operate_1.setOnClickListener(this)
        promotion_operate_2.setOnClickListener(this)
        promotion_lay_shop_order.setOnClickListener(this)
        promotion_invite.setOnClickListener(this)

    }


    override fun inviteCallback(apiResult: ApiResult<InviteBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("数据格式不对")
            return
        }

        promotion_avator.setImageURI( apiResult.data!!.UserHeadImgURL )
        promotion_username.text = apiResult.data!!.UserNickName
        promotion_user_type.text = apiResult.data!!.UserLevelName
        promotion_invite_count.text = apiResult.data!!.InvitationMemberNum.toString()
        promotion_sy_count.text = apiResult.data!!.SurplusNum.toString()
        promotion_profit.text = apiResult.data!!.ProfitAmount

        promotion_operate_2.visibility = if(apiResult.data!!.IsAgent) View.GONE else View.VISIBLE
        promotion_operate_3.visibility = if(apiResult.data!!.IsAgent) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.promotion_operate_1 -> {
                newIntent<InviteOneActivity>()
            }
            R.id.promotion_operate_2 -> {
                newIntent<InviteTwoActivity>()
            }
            R.id.promotion_lay_shop_order -> {
                newIntent<OrderActivity>(Constants.INTENT_ORDER_SOURCE, 100 )
            }
            R.id.promotion_invite->{
                openShareDialog()
            }
        }
    }

    private fun openShareDialog(){
        if( shareDialog==null){
            var list = ArrayList<KeyValue>()
            list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechat, "微信好友"))
            list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatmoments,"微信朋友圈"))
            list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatfavorite,"微信收藏"))
            shareDialog=ShareDialog(context!! , this, list)
        }
    }

    override fun operate(keyValue: KeyValue) {

    }

    override fun fetchData() {
        iPresenter.invite()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_promotion
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        promotion_progress.visibility= View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        promotion_progress.visibility=View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PromotionFragment.
         */
        @JvmStatic
        fun newInstance() =
                PromotionFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
