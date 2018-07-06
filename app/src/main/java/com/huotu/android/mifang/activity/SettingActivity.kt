package com.huotu.android.mifang.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.presenter.MyPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.newIntentForResult
import com.huotu.android.mifang.util.CookieUtils
import com.huotu.android.mifang.util.DataCleanManager
import com.huotu.android.mifang.util.MobileUtils
import com.huotu.android.mifang.util.SPUtils
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_header.*

class SettingActivity : BaseActivity<MyContract.Presenter>()
        ,MyContract.View
        ,View.OnClickListener{
    var isOpenPaySecurty=false
    var iPresenter=MyPresenter(this)
    var REQUEST_CODE = 7001
    var REQUEST_CODE_2= 7002
    var data:SettingBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        header_title.text="设置"
        header_left_image.setOnClickListener(this)
        setting_lay_openPayPassword.setOnClickListener(this)
        setting_quit.setOnClickListener(this)
        setting_lay_one.setOnClickListener(this)
        setting_lay_paypassword.setOnClickListener(this)
        setting_lay_three.setOnClickListener(this)

        setting_version.text = BuildConfig.VERSION_NAME

        try {
            setting_cache_size.text =DataCleanManager.getTotalCacheSize(this)
        } catch (ex: Exception) {
            Log.e(SettingActivity::class.java.name, "获得缓存大小失败")
            setting_cache_size.text =""
        }

        iPresenter.mySetting()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
            R.id.setting_lay_openPayPassword->{
                isOpenPaySecurty = !isOpenPaySecurty
                setting_openPayPassword.setImageResource( if(isOpenPaySecurty) R.drawable.shape_button_selected_bg else R.drawable.shape_button_bg )
            }
            R.id.setting_lay_one->{
                newIntentForResult<BindPhoneActivity>( REQUEST_CODE , Constants.INTENT_PHONE , setting_phone.text.toString() )
            }
            R.id.setting_lay_paypassword->{
                payPassword()
            }
            R.id.setting_lay_three->{
                DataCleanManager.clearAllCache(this)
                setting_cache_size.text ="0k"
            }
            R.id.setting_quit->{
                quit()
            }
        }
    }

    private fun payPassword(){
//        if(data==null|| TextUtils.isEmpty( data!!.UserMobile)){
//            toast("请先绑定手机号码")
//            setting_lay_one.performClick()
//            return
//        }

        newIntentForResult<PayPasswordActivity>(REQUEST_CODE_2 , Constants.INTENT_PHONE , setting_phone.text.toString())
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

    override fun getQrcodeCallback(apiResult: ApiResult<String>) {

    }

    override fun mySettingCallback(apiResult: ApiResult<SettingBean>) {

        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        data = apiResult.data

        if(TextUtils.isEmpty( apiResult.data!!.UserMobile)){
            setting_phone.text="绑定手机"
        }else {
            setting_phone.text = MobileUtils.dealPhone( apiResult.data!!.UserMobile)
        }

        setting_password.text = if( apiResult.data!!.PayPassworded==0) "未设置密码" else "已设置密码"
        setting_openPayPassword.setImageResource( if(apiResult.data!!.PayPasswordStatus==0) R.drawable.shape_button_bg else R.drawable.shape_button_selected_bg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            var phone = data!!.getStringExtra(Constants.INTENT_PHONE)
            setting_phone.text= MobileUtils.dealPhone(phone)
        }else if(requestCode == REQUEST_CODE_2 && resultCode == Activity.RESULT_OK){
            setting_password.text = "todo"
        }
    }
}
