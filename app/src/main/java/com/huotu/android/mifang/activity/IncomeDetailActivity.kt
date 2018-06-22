package com.huotu.android.mifang.activity

import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.IncomeDetailAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.IncomeDetailBean
import com.huotu.android.mifang.bean.IncomeDetailEntity
import com.huotu.android.mifang.mvp.IPresenter
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration
import kotlinx.android.synthetic.main.activity_income_detail.*
import kotlinx.android.synthetic.main.layout_header_2.*

class IncomeDetailActivity : BaseActivity<IPresenter>() ,View.OnClickListener{
    var incomeDetailAdapter :IncomeDetailAdapter?=null
    var data =ArrayList<IncomeDetailEntity>()
    var type = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_detail)

        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,1)
        }

        if(type==1) {
            header_title.text = "每日收益"
        }else if(type ==2){
            header_title.text="每周收益"
        }else if(type==3){
            header_title.text="每月收益"
        }

        header_right_image.visibility= View.GONE
        header_left_image.setOnClickListener(this)
        header_right_text.setOnClickListener(this)

        income_detail_recyclerview.layoutManager=LinearLayoutManager(this)


        data.add(IncomeDetailEntity( IncomeDetailBean( "a" , "a","a") ,1))
        data.add(IncomeDetailEntity( IncomeDetailBean( "1" , "1","1") ,2))
        data.add(IncomeDetailEntity( IncomeDetailBean( "2" , "2","2") ,2))
        data.add(IncomeDetailEntity( IncomeDetailBean( "3" , "3","3") ,3))

        data.add(IncomeDetailEntity( IncomeDetailBean( "b" , "b","b") ,1))
        data.add(IncomeDetailEntity( IncomeDetailBean( "1" , "1","1") ,2))
        data.add(IncomeDetailEntity( IncomeDetailBean( "2" , "2","2") ,2))
        data.add(IncomeDetailEntity( IncomeDetailBean( "3" , "3","3") ,3))

        data.add(IncomeDetailEntity( IncomeDetailBean( "c" , "c","c") ,1))
        data.add(IncomeDetailEntity( IncomeDetailBean( "1" , "1","1") ,2))
        data.add(IncomeDetailEntity( IncomeDetailBean( "2" , "2","2") ,2))
        data.add(IncomeDetailEntity( IncomeDetailBean( "3" , "3","3") ,3))


        incomeDetailAdapter = IncomeDetailAdapter(data)


        income_detail_recyclerview.adapter = incomeDetailAdapter

        income_detail_recyclerview.addItemDecoration( RecyclerViewDecoration(this , ContextCompat.getColor(this , R.color.line_color ) ,data) )

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_text->{
                filter()
            }
        }
    }


    private fun filter(){
        income_detail_lay_select.visibility = if( income_detail_lay_select.visibility==View.VISIBLE) View.GONE else View.VISIBLE

    }

    class RecyclerViewDecoration(context: Context, @ColorInt var dividerColor: Int, var data :ArrayList<IncomeDetailEntity> )
        : Y_DividerItemDecoration(context){

        override fun getDivider(itemPosition: Int): Y_Divider {
            when ( data[itemPosition].type ) {
                1,2 -> {
                    return Y_DividerBuilder()
                            .setBottomSideLine(true, dividerColor, 1f, 0f, 0f)
                            .create()
                }else -> {
                    return Y_DividerBuilder()
                            .setBottomSideLine(true, dividerColor , 6f, 0f,0f)
                            .create()
                }
            }
        }

    }
}
