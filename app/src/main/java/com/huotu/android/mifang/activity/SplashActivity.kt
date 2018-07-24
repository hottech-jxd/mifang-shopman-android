package com.huotu.android.mifang.activity


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.huotu.android.library.libpush.PushHelper
import com.huotu.android.mifang.*
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.SplashContract
import com.huotu.android.mifang.mvp.presenter.SplashPresenter
import com.huotu.android.mifang.util.CookieUtils
import com.huotu.android.mifang.util.GsonUtils
import com.huotu.android.mifang.util.SPUtils
import permissions.dispatcher.*
import com.huotu.android.mifang.receiver.WechatLoginReceiver
import com.tencent.mm.opensdk.modelmsg.SendAuth
import kotlinx.android.synthetic.main.activity_splash.*
import permissions.dispatcher.BuildConfig
import com.huotu.android.mifang.bean.UserBean


@RuntimePermissions
class SplashActivity : BaseActivity<SplashContract.Presenter>() ,
        SplashContract.View , View.OnClickListener, WechatLoginReceiver.LoginListener{
    var presenter : SplashPresenter?=null
    var wechatLoginReceiver : WechatLoginReceiver?=null
    val REQUEST_CODE=1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        register()

        checkPermissionWithPermissionCheck()

    }

    override fun setStatusBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun init() {
        layError.setOnClickListener(this)
        splash_wechat_login.setOnClickListener(this)

        tvVersion.text = getString(R.string.app_name) + " v" + BuildConfig.VERSION_NAME

        val json = SPUtils.getInstance(this,Constants.PREF_FILENAME).readString( Constants.PREF_USER, "")
        if (!json.isEmpty()) {
            BaseApplication.instance!!.variable.userBean = GsonUtils.gson!!.fromJson(json, UserBean::class.java)
        }

        presenter = SplashPresenter(this)
        presenter!!.initData()

    }


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun checkPermission() {
        init()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult(requestCode , grantResults)
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog( "使用app，需要相关权限" , request)
    }

    private fun showRationaleDialog( message : String , request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton("允许") { _, _ -> request.proceed() }
                .setNegativeButton("拒绝") { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(message)
                .show()
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraDenied() {
        showToast("申请权限被拒绝，无法使用")
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraNeverAskAgain() {
        showToast( "不再询问权限" )
    }

    private fun gotoHome() {

        //获得推送信息
        var bundlePush: Bundle? = null
        if (null != intent && intent.hasExtra(Constants.INTENT_PUSH_KEY)) {
            bundlePush = intent.getBundleExtra(Constants.INTENT_PUSH_KEY)
        }

        skipIntent<MainActivity>( Constants.INTENT_PUSH_KEY , bundlePush )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.layError,R.id.splash_wechat_login->{
                weChatLogin()
            }
        }
    }

    private fun isLoginStatus():Boolean{
        var json = SPUtils.getInstance(this , Constants.PREF_FILENAME).readString(Constants.PREF_USER,"")
        if(!TextUtils.isEmpty(json)){
            BaseApplication.instance!!.variable.userBean = GsonUtils.gson!!.fromJson(json,UserBean::class.java)
            return true
        }
        return false
    }

    private fun weChatLogin(){

        if( !isLoginStatus()) {
            if( isGetWechatUser() ){
                var wechatUser = BaseApplication.instance!!.variable.wechatUser

                var unionid=wechatUser!!.unionid

                if(com.huotu.android.mifang.BuildConfig.DEBUG) {//ttttesetstts
                    unionid="ovFVjw7BF5u9VQBDdecRRdqKEGHA"
                }


                presenter!!.loginByUnionId( wechatUser!!.openid , unionid , wechatUser.nickname, wechatUser.headimgurl )
            }else {
//                var req = SendAuth.Req()
//                req.scope = "snsapi_userinfo"
//                req.state = "mifang"
//                AppInit.iwxApi!!.sendReq(req)
                presenter!!.sendWechatLogin()
            }
        }else if(bindPhone()){

        }
    }

    /**
     * 检测是否已经获得了微信用户信息
     */
    private fun isGetWechatUser():Boolean{
        var json = SPUtils.getInstance(this, Constants.PREF_FILENAME).readString(Constants.PREF_WECHAT_USER,"")
        if(TextUtils.isEmpty( json)) return false

        BaseApplication.instance!!.variable.wechatUser = GsonUtils.gson!!.fromJson(json, WechatUser::class.java)
        return true
    }


    override fun showProgress( msg:String){
        layError.visibility =View.GONE
        splash_progress.visibility=View.VISIBLE
    }

    override fun hideProgress(){
        splash_progress.visibility=View.GONE
    }

    override fun error(err:String){
        //gotoHome()
        hideProgress()
        toast(err)
    }

    override fun initDataCallback(result: ApiResult<InitDataBean>) {
        hideProgress()

        if (processCommonErrorCode(result)) {
            return
        }
        if (result.code != ApiResultCodeEnum.SUCCESS.code) {
            toast(result.msg)
            return
        }
        if (result.data == null ) {
            BaseApplication.instance!!.variable.userBean = null
            SPUtils.getInstance(this, Constants.PREF_FILENAME).writeString(Constants.PREF_USER, "")
            return
        }

        BaseApplication.instance!!.variable.initDataBean = result.data

        if( TextUtils.isEmpty( result.data!!.token )){
            BaseApplication.instance!!.variable.userBean=null
            SPUtils.getInstance(this,Constants.PREF_FILENAME).writeString(Constants.PREF_USER,"")
            return
        }


        var userId = result.data!!.userId
        var loginname = ""
        var nickname = result.data!!.nickName
        var wxNickName = nickname
        var userHead = result.data!!.userHead
        var token = result.data!!.token
        var bindedMobile = result.data!!.bindedMobile
        var wxHeadImg = userHead
        var mobile=result.data!!.mobile
        var userRole=result.data!!.userRoleType
        var userRolename = result.data!!.userRoleName

        BaseApplication.instance!!.variable.userBean = UserBean(userId, loginname,
                wxNickName, nickname, userHead ,token , bindedMobile,  mobile,userRole , userRolename ,wxHeadImg )
        SPUtils.getInstance(this, Constants.PREF_FILENAME)
                .writeString(Constants.PREF_USER, GsonUtils.gson!!.toJson(BaseApplication.instance!!.variable.userBean))

        //CookieUtils.setWebViewCookie()
        gotoHome()
    }

    override fun onDestroy() {
        super.onDestroy()

        unRegister()
    }

    private fun register(){
        wechatLoginReceiver= WechatLoginReceiver(this)
        val intentFilter = IntentFilter( Constants.ACTION_WECHAT_LOGIN )
        this.registerReceiver(wechatLoginReceiver , intentFilter)
    }

    private fun unRegister(){
        if(wechatLoginReceiver!=null){
            wechatLoginReceiver!!.loginListener=null
            this.unregisterReceiver(wechatLoginReceiver)
            wechatLoginReceiver=null
        }
    }

    override fun weChatCallback(code: String) {
        presenter!!.getWechatAccessToken( com.huotu.android.mifang.BuildConfig.wechat_app_id ,
                com.huotu.android.mifang.BuildConfig.wechat_app_secret , code )
    }

    override fun getWechatAccessTokenCallback(result: WechatAccessToken) {
        SPUtils.getInstance(this , Constants.PREF_FILENAME )
                .writeString(Constants.PREF_WECHAT_ACCESSTOKEN , GsonUtils.gson!!.toJson(result))
    }

    override fun getWechatUserInfo(result: WechatUser) {
         if( result.errcode!=0){
             error("微信授权登录失败")
             return
         }

        SPUtils.getInstance(this, Constants.PREF_FILENAME)
                .writeString(Constants.PREF_WECHAT_USER , GsonUtils.gson!!.toJson( result ) )
        BaseApplication.instance!!.variable.wechatUser = result

        presenter!!.loginByUnionId( result.openid , result.unionid , result.nickname, result.headimgurl )
    }

    override fun loginByUnionIdCallback(apiResult: ApiResult<UserBean>) {
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("发生错误,请重新登录")
            return
        }

        SPUtils.getInstance(this , Constants.PREF_FILENAME).writeString(Constants.PREF_USER , GsonUtils.gson!!.toJson(apiResult.data))
        BaseApplication.instance!!.variable.userBean = apiResult.data
        //setJpushAlias( apiResult.data!!  )

        bindPhone()
    }

    private fun bindPhone():Boolean{

        if(!BaseApplication.instance!!.variable.userBean!!.bindedMobile) {
            newIntentForResult<BindPhoneActivity>(REQUEST_CODE)
            return false
        }else{
            setJpushAlias( BaseApplication.instance!!.variable.userBean!!  )
            gotoHome()
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == REQUEST_CODE && resultCode== Activity.RESULT_OK){
            BaseApplication.instance!!.variable.userBean!!.bindedMobile=true
            SPUtils.getInstance(this, Constants.PREF_FILENAME).writeString(Constants.PREF_USER , GsonUtils.gson!!.toJson(BaseApplication.instance!!.variable.userBean))
            gotoHome()
        }else{
            toast("绑定手机号码才能使用app")
        }
    }

    /**
     * 登录成功，设置极光推送的别名为 手机号
     * @param userBean
     */
    private fun setJpushAlias( user: UserBean ) {
       PushHelper.bindingUserId( user.userId.toString() , user.mobile , "", "", "")
    }

}
