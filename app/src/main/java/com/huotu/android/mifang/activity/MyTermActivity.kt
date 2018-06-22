package com.huotu.android.mifang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.activity_my_term.*
import kotlinx.android.synthetic.main.layout_header.*

class MyTermActivity : BaseActivity<IPresenter>() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_term)

        myterm_lay_1.setOnClickListener(this)
        myterm_lay_6.setOnClickListener(this)
        header_left_image.setOnClickListener(this)
        header_title.text="我的团队"
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.myterm_lay_1->{
                newIntent<MyTermDetailActivity>(Constants.INTENT_TERM_TYPE , 1)
            }
            R.id.myterm_lay_6->{
                newIntent<MyTermDetailActivity>(Constants.INTENT_TERM_TYPE , 2 )
            }
            R.id.header_left_image->{
                finish()
            }
        }
    }
}
