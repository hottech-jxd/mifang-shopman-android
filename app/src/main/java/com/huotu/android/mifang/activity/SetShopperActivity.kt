package com.huotu.android.mifang.activity


import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.layout_header.*

class SetShopperActivity : BaseActivity<IPresenter>()
, View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_shopper)

        header_left_image.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
        }
    }
}
