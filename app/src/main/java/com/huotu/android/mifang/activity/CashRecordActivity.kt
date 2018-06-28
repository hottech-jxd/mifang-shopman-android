package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.CashRecordAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_cash_record_item.*
import kotlinx.android.synthetic.main.layout_header.*

class CashRecordActivity : BaseActivity<IPresenter>() ,View.OnClickListener{
    var data=ArrayList<CashRecord>()
    var cashRecordAdapter:CashRecordAdapter?=null
    var emptyView:View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_record_item)

        header_title.text="提现记录"
        header_left_image.setOnClickListener(this)

        cash_record_recyclerview.layoutManager=LinearLayoutManager(this)
//        data.add(CashRecord(""))
//        data.add(CashRecord(""))
//        data.add(CashRecord(""))
//        data.add(CashRecord(""))
//        data.add(CashRecord(""))
        cashRecordAdapter=CashRecordAdapter(data)
        cash_record_recyclerview.adapter = cashRecordAdapter
        cash_record_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this, R.color.bg_line), 1f))

        emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, cash_record_recyclerview.getParent() as ViewGroup , false )
        cashRecordAdapter!!.emptyView = emptyView

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
    }

    override fun hideProgress() {
        super.hideProgress()
    }
}
