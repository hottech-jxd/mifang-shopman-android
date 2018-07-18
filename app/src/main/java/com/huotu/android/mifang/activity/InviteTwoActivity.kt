package com.huotu.android.mifang.activity

import android.os.Bundle
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.activity_invite_two.*
import kotlinx.android.synthetic.main.layout_header.*

class InviteTwoActivity : BaseActivity<IPresenter>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_two)

        header_title.text="邀请入驻"
        header_left_image.setOnClickListener(this)
        invite_two_applyAgent.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.invite_two_applyAgent->{
                newIntent<AgentUpgradeActivity>()
            }
        }
    }
}
