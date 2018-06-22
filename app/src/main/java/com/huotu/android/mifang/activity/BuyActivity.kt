package com.huotu.android.mifang.activity

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.layout_header.*

class BuyActivity : BaseActivity<IPresenter>() ,View.OnClickListener{
    var type = 0 //0:购买店主账号，1：续费


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)


        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,0)
        }

        header_title.text="购买开店账号/续费"
        header_left_image.setOnClickListener(this)
        buy_add.setOnClickListener(this)
        buy_jian.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.buy_add->{
                add()
            }
            R.id.buy_jian->{
                minus()
            }
        }
    }

    private fun add(){
        var size = buy_size.text.toString().toInt()
        size +=1
        buy_size.setText(size.toString())
    }
    private fun minus(){
        var size = buy_size.text.toString().toInt()
        size -=1
        buy_size.setText(size.toString())
    }
}
