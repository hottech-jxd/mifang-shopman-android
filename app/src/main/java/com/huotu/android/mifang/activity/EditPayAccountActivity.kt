package com.huotu.android.mifang.activity


import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.PayAccount
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_edit_pay_account.*
import kotlinx.android.synthetic.main.layout_header.*

class EditPayAccountActivity : BaseActivity<IPresenter>() ,View.OnClickListener{
    var type :Int = 0
    var payAccount : PayAccount?=null
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pay_account)

        type= intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,0)

        if(intent.hasExtra(Constants.INTENT_BEAN)) {
            payAccount = intent.getSerializableExtra(Constants.INTENT_BEAN) as PayAccount
        }

        header_title.text= if(type==0) "新增提现账号" else "修改提现账号"

        header_left_image.setOnClickListener(this)
        edit_pay_account_alipay.setOnClickListener(this)
        edit_pay_account_wepay.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.edit_pay_account_alipay->{

            }
            R.id.edit_pay_account_wepay->{

            }
        }
    }
}
