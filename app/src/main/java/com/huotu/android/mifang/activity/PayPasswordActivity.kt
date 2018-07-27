package com.huotu.android.mifang.activity

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cn.iwgang.countdownview.CountdownView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.contract.BindPhoneContract
import com.huotu.android.mifang.mvp.presenter.BindPhonePresenter
import com.huotu.android.mifang.util.DigestUtils
import com.huotu.android.mifang.util.ImageCodeUtils
import com.huotu.android.mifang.util.KeybordUtils
import com.huotu.android.mifang.util.MobileUtils
import kotlinx.android.synthetic.main.activity_pay_password.*
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.android.synthetic.main.layout_pay_one.*
import kotlinx.android.synthetic.main.layout_pay_three.*
import kotlinx.android.synthetic.main.layout_pay_two.*

class PayPasswordActivity : BaseActivity<BindPhoneContract.Presenter>()
        ,CountdownView.OnCountdownEndListener
    ,BindPhoneContract.View,View.OnClickListener{

    var phone =""
    var iPresenter=BindPhonePresenter(this)
    var countdown=60*1000L
    var imageCode = ""
    var isSetPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_password)

        if (intent.hasExtra(Constants.INTENT_PHONE)) {
            phone = intent.getStringExtra(Constants.INTENT_PHONE)
        }

        header_title.text = "设置支付密码"
        header_right_image.setOnClickListener(this)
        header_left_image.setOnClickListener(this)

        payPassword_1.visibility = View.VISIBLE
        payPassword_2.visibility = View.GONE
        payPassword_3.visibility = View.GONE


        payPassword_lay_one_phone.text = MobileUtils.dealPhone( phone)
        payPassword_lay_one_sendCode.setOnClickListener(this)
        payPassword_lay_one_getImageCode.setOnClickListener(this)
        payPassword_lay_one_next.setOnClickListener(this)
        payPassword_lay_one_countdown.setOnCountdownEndListener(this)

        imageCode = ImageCodeUtils.instance.createCode()
        payPassword_lay_one_getImageCode.setImageBitmap( ImageCodeUtils.instance.createBitmap(imageCode) )

        paypassword_lay_two_next.setOnClickListener(this)

        payPassword_lay_three_back.setOnClickListener(this)

        KeybordUtils.openKeybord(this, payPassword_lay_one_code)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                KeybordUtils.closeKeyboard(this)
                if(isSetPassword){
                    setResult(Activity.RESULT_OK)
                }
                finish()
            }
            R.id.payPassword_lay_one_sendCode->{
                sendCode()
            }
            R.id.payPassword_lay_one_getImageCode->{
                getImageCode()
            }
            R.id.payPassword_lay_one_next->{
                next1()
            }
            R.id.paypassword_lay_two_next->{
                next2()
            }
            R.id.payPassword_lay_three_back->{
                KeybordUtils.closeKeyboard(this)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun sendCode(){
        iPresenter.sendCode( phone)
    }

    private fun next2(){

        var pwd1 = payPassword_pwd1.text.toString().trim()
        var pwd2 = payPassword_pwd2.text.toString().trim()
        if(TextUtils.isEmpty(pwd1)){
            toast("请输入支付密码")
            payPassword_pwd1.requestFocus()
            KeybordUtils.openKeybord(this,payPassword_pwd1)
            return
        }
        if(TextUtils.isEmpty(pwd2)){
            toast("请确认支付密码")
            payPassword_pwd2.requestFocus()
            KeybordUtils.openKeybord(this,payPassword_pwd2)
            return
        }
        if( pwd1 != pwd2){
            toast("两次输入密码不一致，请重新输入")
            return
        }
        if(pwd1.length!=6){
            toast("密码长度必须是6位，请重新输入")
            payPassword_pwd1.requestFocus()
            KeybordUtils.openKeybord(this,payPassword_pwd1)
            return
        }

        KeybordUtils.closeKeyboard(this)
        var md5 = DigestUtils.getInstance().md5(pwd1)!!.toLowerCase()
        iPresenter.updatePayPassword(md5)
    }

    private fun getImageCode(){
        imageCode = ImageCodeUtils.instance.createCode()
        var bmp =ImageCodeUtils.instance.createBitmap( imageCode )
        payPassword_lay_one_getImageCode.setImageBitmap(bmp)
    }

    private fun next1(){
        var code = payPassword_lay_one_code.text.trim().toString()
        var imageCode = payPassword_lay_one_imgcode.text.trim().toString()
        if(TextUtils.isEmpty(code)){
            toast("请输入短信验证码")
            payPassword_lay_one_code.requestFocus()
            KeybordUtils.openKeybord(this, payPassword_lay_one_code)
            return
        }
        if(TextUtils.isEmpty(imageCode)){
            toast("请输入图形码")
            payPassword_lay_one_imgcode.requestFocus()
            KeybordUtils.openKeybord(this, payPassword_lay_one_imgcode)
            return
        }

        if(!imageCode.equals( imageCode )){
            toast("图形码不正确,请重新输入")
            payPassword_lay_one_imgcode.requestFocus()
            KeybordUtils.openKeybord(this,payPassword_lay_one_imgcode)
            return
        }

        KeybordUtils.closeKeyboard(this)
        iPresenter.checkCode( phone , code)

    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        payPassword_Progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        payPassword_Progress.visibility = View.GONE
    }

    override fun bindPhoneCallback(apiResult: ApiResult<Any>) {

    }


    override fun sendCodeCallback(apiResult: ApiResult<Any>) {
        hideProgress()
        payPassword_lay_one_sendCode.visibility =View.GONE
        payPassword_lay_one_countdown.visibility =View.VISIBLE
        payPassword_lay_one_countdown.start(countdown)
        payPassword_lay_one_code.requestFocus()
    }

    override fun updatePayPasswordCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        payPassword_1.visibility=View.GONE
        payPassword_2.visibility=View.GONE
        payPassword_3.visibility = View.VISIBLE
        isSetPassword= true
    }

    override fun checkCodeCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        payPassword_1.visibility=View.GONE
        payPassword_2.visibility=View.VISIBLE
        payPassword_3.visibility = View.GONE

        payPassword_pwd1.requestFocus()
        KeybordUtils.openKeybord(this, payPassword_pwd1)
    }

    override fun onEnd(cv: CountdownView?) {
        payPassword_lay_one_countdown.stop()
        payPassword_lay_one_countdown.visibility= View.GONE
        payPassword_lay_one_sendCode.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()

        KeybordUtils.closeKeyboard(this)
        payPassword_lay_one_countdown.stop()
        payPassword_lay_one_countdown.setOnCountdownEndListener(null)

    }
}
