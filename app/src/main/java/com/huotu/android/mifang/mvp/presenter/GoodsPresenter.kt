package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.BuyContract
import com.huotu.android.mifang.mvp.contract.GoodsContract
import com.huotu.android.mifang.mvp.contract.InviteContract
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.model.*
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GoodsPresenter(view: GoodsContract.View):GoodsContract.Presenter {

    var mView: GoodsContract.View?=null
    private val mModel: GoodsModel by lazy { GoodsModel() }

    init {
        mView=view
    }

    override fun getStoreIndex(pageIndex: Int, pageSize: Int) {
        val observable = mModel.getStoreIndex(pageIndex, pageSize)

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<ArrayList<GoodsInfoBean>>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<GoodsInfoBean>>) {

                        mView!!.getStoreIndexCallback(t )

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getGoodsInfo(goodsId: Long) {

        val observable = mModel.getGoodsInfo(goodsId)

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<GoodsDetailBean>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<GoodsDetailBean>) {

                            mView!!.getGoodsInfoCallback(t )

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getShopperAccountInfo() {
        val observable = mModel.getShopperAccountInfo()

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<ShopperAccountInfo>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ShopperAccountInfo>) {

                        mView!!.getShopperAccountInfoCallback( t )

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun agentUpgrade(goodsId: Long) {
        val observable = mModel.agentUpgrade(goodsId)

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<GoodsDetailBean>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<GoodsDetailBean>) {

                        mView!!.agentUpgradeCallback( t )

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun onDestory() {

    }
}