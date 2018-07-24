package com.huotu.android.mifang.activity

import android.os.Bundle
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.mvp.contract.MyContract

class QrCodeActivity : BaseActivity<MyContract.Presenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)
    }
}
