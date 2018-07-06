package com.huotu.android.mifang.activity

import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.TermIndexAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.TermContract
import com.huotu.android.mifang.mvp.presenter.TermPresenter
import com.huotu.android.mifang.newIntent
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_my_term.*
import kotlinx.android.synthetic.main.layout_header.*

class MyTermActivity : BaseActivity<TermContract.Presenter>()
        , TermContract.View
        ,BaseQuickAdapter.OnItemClickListener
        , View.OnClickListener {
    var termIndexAdapter:TermIndexAdapter?=null
    var data = ArrayList<MultiItemEntity>()
    var iPresenter=TermPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_term)

        header_left_image.setOnClickListener(this)
        header_title.text="我的团队"

        termIndexAdapter = TermIndexAdapter(data)
        myterm_recyclerview.layoutManager=LinearLayoutManager(this)
        myterm_recyclerview.adapter = termIndexAdapter
        termIndexAdapter!!.onItemClickListener = this
        myterm_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this , R.color.line_color2 ) , termIndexAdapter!! ))

        iPresenter.getTermIndex()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(adapter!!.getItemViewType(position) ){
            0->{
                var flag =  (adapter.data[position] as TermIndex0Entity).flag
                newIntent<MyTermDetailActivity>(Constants.INTENT_TERM_TYPE , flag)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        myterm_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        myterm_progress.visibility = View.GONE
    }

    override fun getTermMemberListCallback(apiResult: ApiResult<TermBean>) {

    }

    override fun getLevelListCallback(apiResult: ApiResult<ArrayList<LevelBean>>) {

    }

    override fun getTermIndexCallback(apiResult: ApiResult<TermIndexBean>) {
        hideProgress()
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            return
        }

        data.clear()
        var item = TermIndex0Entity("团队详情" , 1 )
        data.add(item)
        var item1 = TermIndex1Entity("用户等级","一级团队(人)","二级团队(人)")
        data.add(item1)
        if( apiResult.data!!.TeamInfo!=null) {
            for (bean in apiResult.data!!.TeamInfo!!) {
                var item2 = TermIndex2Entity(bean)
                data.add(item2)
            }
        }
        item = TermIndex0Entity("团队新增", 2)
        data.add(item)
        item1 = TermIndex1Entity("团队类型","今日(人)","本月(人)")
        data.add(item1)

        var tinfo = TeamInfoBean("一级团队", apiResult.data!!.BelongOneNumByToday , apiResult.data!!.BelongOneNumByMonth, 0)
        var itemn = TermIndex2Entity(tinfo)
        data.add(itemn)

        tinfo = TeamInfoBean("二级团队", apiResult.data!!.BelongTwoNumByToday, apiResult.data!!.BelongTwoNumByMonth, 0)
        itemn = TermIndex2Entity(tinfo)
        data.add(itemn)


        termIndexAdapter!!.notifyDataSetChanged()

    }



    class RecyclerViewDivider(context: Context, @ColorInt var dividerColor: Int , var adapter:TermIndexAdapter ): Y_DividerItemDecoration(context){

        override fun getDivider(itemPosition: Int): Y_Divider {

            var bean = adapter.getItem(itemPosition)

            when( bean!!.itemType){
                0->{
                    return Y_DividerBuilder().create()
                }
                1,2->{
                    return Y_DividerBuilder()
                            .setBottomSideLine(true , dividerColor , 1f,0f,0f)
                            .create()
                }
                else-> {
                    return Y_DividerBuilder().create()
                }
            }


        }
    }

}
