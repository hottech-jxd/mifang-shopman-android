package com.huotu.android.mifang.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.presenter.MyPresenter
import com.huotu.android.mifang.util.CookieUtils
import com.huotu.android.mifang.util.SPUtils
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_header.*

class SettingActivity : BaseActivity<MyContract.Presenter>()
        ,MyContract.View
        ,View.OnClickListener{
    var isOpenPaySecurty=false
    var iPresenter=MyPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        header_title.text="设置"
        header_left_image.setOnClickListener(this)
        setting_lay_openPayPassword.setOnClickListener(this)
        setting_quit.setOnClickListener(this)


        setting_version.text = BuildConfig.VERSION_NAME

        iPresenter.mySetting()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.setting_lay_openPayPassword->{
                //todo
                isOpenPaySecurty = !isOpenPaySecurty
                setting_openPayPassword.setImageResource( if(isOpenPaySecurty) R.drawable.shape_button_selected_bg else R.drawable.shape_button_bg )
            }
            R.id.setting_quit->{
                quit()
            }
        }
    }

    private fun quit(){

        SPUtils.getInstance(this,Constants.PREF_FILENAME)
                .writeString(Constants.PREF_USER, "")
        BaseApplication.instance!!.variable.wechatUser=null
        BaseApplication.instance!!.variable.userBean=null
        CookieUtils.clearWebViewCookie()
    }

    override fun myIndexCallback(apiResult: ApiResult<MyBean>) {

    }

    override fun mySettingCallback(apiResult: ApiResult<SettingBean>) {

        if(processCommonErrorCode(apiResult.code , apiResult.msg)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        if(TextUtils.isEmpty( apiResult.data!!.UserMobile)){
            setting_phone.text="绑定手机"
        }else {
            setting_phone.text = apiResult.data!!.UserMobile
        }

        setting_password.text = if( apiResult.data!!.PayPassworded==0) "未设置密码" else "已设置密码"
        setting_openPayPassword.setImageResource( if(apiResult.data!!.PayPasswordStatus==0) R.drawable.shape_button_bg else R.drawable.shape_button_selected_bg)
    }
}
