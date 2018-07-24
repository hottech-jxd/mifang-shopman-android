package com.huotu.android.mifang.mvp.presenter


import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.SplashContract
import com.huotu.android.mifang.mvp.model.SplashModel
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList


class SplashPresenter(view: SplashContract.View) : SplashContract.Presenter {
    var mView:SplashContract.View?=null
    val mModel : SplashModel by lazy { SplashModel() }
    init {
        mView = view
    }


    override fun initData() {
        val observable : Observable<ApiResult<InitDataBean>>? = mModel.initData()
        observable?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<InitDataBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<InitDataBean>) {
                        mView!!.initDataCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun sendWechatLogin() {

        var observable = Observable.create<Any> { e ->
            e.onNext(sendWechat())
            e.onComplete()
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Any> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: Any) {

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })

    }


    private fun sendWechat(){
        var req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "mifang"
        AppInit.iwxApi!!.sendReq(req)
    }

    override fun getWechatAccessToken(appid: String, appSecret: String, code: String) {
        val observable : Observable<WechatAccessToken>? = mModel.getWechatAccessToken(appid,appSecret , code )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle( mView as LifecycleProvider<Any>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<WechatAccessToken> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: WechatAccessToken ) {
                        if( t.errcode != 0 ){
                            mView!!.error( "登录失败code="+t.errcode )
                            return
                        }
                        mView!!.getWechatAccessTokenCallback( t )
                        getWechatUserInfo( t.openid , t.access_token )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun getWechatUserInfo(openid: String, accessToken: String) {
        val observable : Observable<WechatUser>? = mModel.getWechatUser( openid , accessToken )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle( mView as LifecycleProvider<Any>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<WechatUser> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: WechatUser ) {
                        mView!!.getWechatUserInfo( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun loginByUnionId(openid: String, unionId: String, nickName: String, userHead: String) {
        val observable : Observable<ApiResult<UserBean>>? = mModel.loginByUnionId( openid ,unionId , nickName , userHead  )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle( mView as LifecycleProvider<Any>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<UserBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<UserBean> ) {
                        mView!!.loginByUnionIdCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }


    //    override fun readCityData() {
//        val observable : Observable<ArrayList<Province>>? = mModel.readCityData()
//        observable?.subscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread())
//                ?.subscribe( object : Observer<ArrayList<Province>> {
//                    override fun onComplete() {
//                        mView!!.hideProgress()
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                        mView!!.showProgress(Constants.TIP_LOADING)
//                    }
//
//                    override fun onNext(t: ArrayList<Province>) {
//                        mView!!.readCityDataCallback( t )
//                    }
//
//                    override fun onError(e: Throwable) {
//                        mView!!.hideProgress()
//                        mView!!.error(Constants.MESSAGE_ERROR)
//                    }
//                } )
//    }

    override fun onDestory() {

    }
}