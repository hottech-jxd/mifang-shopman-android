package com.huotu.android.mifang.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.ScoreAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ScoreBean
import com.huotu.android.mifang.bean.ScoreTypeEnum
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_wait_accounts.*
import kotlinx.android.synthetic.main.layout_header.*

/**
 * 待结算 积分
 */
class WaitAccountsActivity : BaseActivity<IPresenter>()
        ,View.OnClickListener , BaseQuickAdapter.RequestLoadMoreListener {
    var data=ArrayList<ScoreBean>()
    var scoreAdapter:ScoreAdapter?=null
    var type = ScoreTypeEnum.WaitAccounts.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_accounts)

        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,ScoreTypeEnum.WaitAccounts.id )
        }

        if(type == ScoreTypeEnum.WaitAccounts.id ){
            header_title.text= ScoreTypeEnum.WaitAccounts.desc
            wait_accounts_summary.visibility=View.VISIBLE
        }else if(type ==ScoreTypeEnum.MiBean.id){
            header_title.text=ScoreTypeEnum.MiBean.desc
            wait_accounts_summary.visibility=View.GONE
        }else if(type ==ScoreTypeEnum.Balance.id){
            header_title.text=ScoreTypeEnum.Balance.desc
            wait_accounts_summary.visibility=View.GONE
        }
        header_left_image.setOnClickListener(this)


        wait_accounts_recyclerview.layoutManager=LinearLayoutManager(this)
        wait_accounts_recyclerview.addItemDecoration(RecyclerViewDivider(this , ContextCompat.getColor(this, R.color.bg_line), 10f))

        mockData()
    }

    private fun mockData(){
        for(i in 0..10){
            data.add(ScoreBean(i,"sdfs撒旦法大赛","2018.06.22 15:11:11 ",1))
        }
        scoreAdapter = ScoreAdapter(data)
        scoreAdapter!!.setOnLoadMoreListener(this, wait_accounts_recyclerview)
        wait_accounts_recyclerview.adapter=scoreAdapter
    }

    override fun onLoadMoreRequested() {

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }

    }
}
