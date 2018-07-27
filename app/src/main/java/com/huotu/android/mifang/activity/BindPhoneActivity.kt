package com.huotu.android.mifang.activity


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cn.iwgang.countdownview.CountdownView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.contract.BindPhoneContract
import com.huotu.android.mifang.util.KeybordUtils
import kotlinx.android.synthetic.main.activity_bind_phone.*
import kotlinx.android.synthetic.main.layout_header.*
import android.content.IntentFilter
import android.util.Log
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.mvp.presenter.BindPhonePresenter
import com.huotu.android.mifang.receiver.SmsReceiver
import com.huotu.android.mifang.util.MobileUtils
import org.w3c.dom.Text
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.util.regex.Pattern


@RuntimePermissions
class BindPhoneActivity : BaseActivity<BindPhoneContract.Presenter>()
        ,View.OnClickListener
        ,SmsReceiver.SmsListener
        ,CountdownView.OnCountdownEndListener
    ,BindPhoneContract.View{

    var countdown = 60 * 1000L
    var smsReceiver:SmsReceiver?=null
    var iPresenter = BindPhonePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_phone)

        header_title.text="绑定手机号码"
        if(intent.hasExtra(Constants.INTENT_PHONE)){
            bindPhone_phone.text = intent.getStringExtra(Constants.INTENT_PHONE)
            if( TextUtils.isEmpty( bindPhone_phone.text)){
                bindPhone_phone.text="未绑定手机"
                header_title.text="绑定手机号码"
            }else{
                header_title.text="更换手机号码"
            }
        }

        KeybordUtils.openKeybord(this , bindPhone_newPhone )

        header_left_image.setOnClickListener(this)
        bindPhone_sendCode.setOnClickListener(this)
        bindPhone_ok.setOnClickListener(this)
        bindPhone_countdown.setOnCountdownEndListener(this)
        registerSmsReceiver()
    }

    private fun registerSmsReceiver() {
        smsReceiver = SmsReceiver()
        smsReceiver!!.setSmsListener(this)
        val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        this.registerReceiver(smsReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()

        KeybordUtils.closeKeyboard(this)
        bindPhone_countdown.stop()
        bindPhone_countdown.setOnCountdownEndListener(null)

        if(smsReceiver!=null){
            smsReceiver!!.setSmsListener(null)
            this.unregisterReceiver(smsReceiver)
            smsReceiver=null
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                KeybordUtils.closeKeyboard(this)
                finish()
            }
            R.id.bindPhone_sendCode->{
                sendCodeWithPermissionCheck()
            }
            R.id.bindPhone_ok->{
                bindPhone()
            }
        }
    }

    private fun bindPhone(){
        val phone = bindPhone_newPhone.text.toString().trim()
        if (phone.isEmpty()) {
            bindPhone_newPhone.error ="请输入手机号码"
            bindPhone_newPhone.requestFocus()
            KeybordUtils.openKeybord(this, bindPhone_newPhone)
            return
        }
        if (!MobileUtils.isPhone(phone)) {
            bindPhone_newPhone.error ="请输入正确的手机号码"
            bindPhone_newPhone.requestFocus()
            KeybordUtils.openKeybord(this , bindPhone_newPhone)
            return
        }
        var code = bindPhone_code.text.toString().trim()
        if(TextUtils.isEmpty(code)){
            toast("请输入短信验证码")
            bindPhone_code.requestFocus()
            KeybordUtils.openKeybord(this, bindPhone_code)
            return
        }

        iPresenter.bindPhone(phone , code)
    }

    override fun onEnd(cv: CountdownView?) {
        bindPhone_countdown.stop()
        bindPhone_countdown.visibility= View.GONE
        bindPhone_sendCode.visibility = View.VISIBLE
    }

    override fun smsCallback(message: String) {
        if (TextUtils.isEmpty(message)) return
        val code = getDynamicPwd(message)
        if (TextUtils.isEmpty(code)) return

        runOnUiThread { bindPhone_code.setText(code) }
    }

    /**
     * 从字符串中截取连续4位数字组合 ([0-9]{4})截取4位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
     *
     * @param content 短信内容
     * @return 截取得到的4位动态密码
     */
    fun getDynamicPwd(content: String): String {
        // 此正则表达式验证六位数字的短信验证码
        val pattern = Pattern.compile("(?<![0-9])([0-9]{4})(?![0-9])")
        val matcher = pattern.matcher(content)
        var dynamicPwd = ""
        while (matcher.find()) {
            dynamicPwd = matcher.group()
            Log.i("TAG", "getDynamicPwd: find pwd=$dynamicPwd")
        }
        return dynamicPwd
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        bindPhone_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        bindPhone_progress.visibility=View.GONE
    }

    @NeedsPermission(Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS)
    fun sendCode(){
        val phone = bindPhone_newPhone.text.toString().trim()
        if (phone.isEmpty()) {
            bindPhone_newPhone.error ="请输入手机号码"
            bindPhone_newPhone.requestFocus()
            return
        }
        if (!MobileUtils.isPhone(phone)) {
            bindPhone_newPhone.error ="请输入正确的手机号码"
            bindPhone_newPhone.requestFocus()
            return
        }

        iPresenter.sendCode(phone)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult( requestCode , grantResults )
    }

    override fun sendCodeCallback(apiResult: ApiResult<Any>) {
        hideProgress()
        bindPhone_sendCode.visibility =View.GONE
        bindPhone_countdown.visibility =View.VISIBLE
        bindPhone_countdown.start(countdown)

        bindPhone_code.requestFocus()
    }

    override fun bindPhoneCallback(apiResult: ApiResult<Any>) {
        hideProgress()
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        toast("更换手机完成")
        var intent= Intent()
        intent.putExtra(Constants.INTENT_PHONE, bindPhone_newPhone.text.toString().trim())

        setResult(Activity.RESULT_OK, intent )
        KeybordUtils.closeKeyboard(this)
        finish()
    }

    override fun updatePayPasswordCallback(apiResult: ApiResult<Any>) {

    }

    override fun checkCodeCallback(apiResult: ApiResult<Any>) {

    }
}
