package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.WebActivity
import com.huotu.android.mifang.adapter.MessageAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.MessageContract
import com.huotu.android.mifang.mvp.presenter.MessagePresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.GsonUtils
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_cash_record_item.*
import kotlinx.android.synthetic.main.fragment_message.*

const val ARG_TYPE = "type"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MessageFragment : BaseFragment<MessageContract.Presenter>()
        , MessageContract.View
        , SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.OnItemChildClickListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    private var type: Int = MessageTypeEnum.SystemMessage.id
    private var messageAdapter: MessageAdapter?=null
    private var data=ArrayList<MessageBean>()
    private var iPresenter = MessagePresenter(this)
    private var pageIndex=0
    private var isShowProgress=true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE)
        }
    }

    override fun initView() {
        message_refreshview.setOnRefreshListener(this)

        messageAdapter = MessageAdapter(data)

        var emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty , null)
        messageAdapter!!.emptyView = emptyView
        messageAdapter!!.isUseEmpty(false)
        messageAdapter!!.onItemChildClickListener =this
        messageAdapter!!.setOnLoadMoreListener(this, message_recyclerview)
        message_recyclerview.layoutManager = LinearLayoutManager(context)
        message_recyclerview.addItemDecoration(RecyclerViewDivider(context!!, ContextCompat.getColor(context!!, R.color.line_color), 10f))
        message_recyclerview.adapter = messageAdapter
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.message_item_system_1_cash->{

            }
            R.id.message_item_system_3_cash->{

            }
            R.id.message_item_register_5_see->{

            }
            R.id.message_item_layout_system_0->{
                //系统消息-》详情页
                openWeb( adapter!!.getItem(position) as MessageBean )
            }
        }
    }

    private fun openWeb(messageBean: MessageBean){
        if( TextUtils.isEmpty( messageBean.NotificationContent)) return
        var bean = GsonUtils.gson!!.fromJson(messageBean.NotificationContent, SystemNoticeViewModel::class.java)
        if(TextUtils.isEmpty( bean.JumpURL )) return
        newIntent<WebActivity>(Constants.INTENT_URL , bean.JumpURL)
    }

    override fun fetchData() {

        iPresenter.getMessageList(type , pageIndex+1 , Constants.PAGE_SIZE )

    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        message_progress.visibility = if(isShowProgress) View.VISIBLE else View.GONE
    }

    override fun hideProgress() {
        super.hideProgress()
        message_progress.visibility=View.GONE
        messageAdapter!!.isUseEmpty(true)
        message_refreshview.isRefreshing=false
        isShowProgress=false
    }

    override fun getLayoutResourceId(): Int {
       return R.layout.fragment_message
    }

    override fun onRefresh() {
        pageIndex=0
        isShowProgress=false
        fetchData()
    }

    override fun onLoadMoreRequested() {
        fetchData()
    }

    override fun getMessageList(apiResult: ApiResult<ArrayList<MessageBean>>) {
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
                messageAdapter!!.loadMoreEnd(true)
            } else {
                messageAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            messageAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            messageAdapter!!.setNewData(apiResult.data)
            messageAdapter!!.disableLoadMoreIfNotFullPage(message_recyclerview)
        } else {
            messageAdapter!!.addData(apiResult.data!!)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MessageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(type: Int) =
                MessageFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TYPE, type)

                    }
                }
    }
}
