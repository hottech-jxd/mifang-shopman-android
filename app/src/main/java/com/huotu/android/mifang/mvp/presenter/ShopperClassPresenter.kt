package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.QuanContract
import com.huotu.android.mifang.mvp.contract.ScoreContract
import com.huotu.android.mifang.mvp.contract.ShopperClassContract
import com.huotu.android.mifang.mvp.model.QuanModel
import com.huotu.android.mifang.mvp.model.ScoreModel
import com.huotu.android.mifang.mvp.model.ShopperClassModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ShopperClassPresenter(view: ShopperClassContract.View) : ShopperClassContract.Presenter {

    var mView: ShopperClassContract.View? = null
    val mModel: ShopperClassModel by lazy { ShopperClassModel() }

    init {
        mView = view
    }

    override fun getCategorys() {

        val observable: Observable<ApiResult<ArrayList<ShopperClassCategoryBean>>>? = mModel.getShopperClassCategorys()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<ShopperClassCategoryBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<ShopperClassCategoryBean>>) {
                        mView!!.getCategorysCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getList(typeId: Int, pageIndex: Int) {

        val observable: Observable<ApiResult<ArrayList<ShopperClassBean>>>? = mModel.getShopperClassList(typeId, pageIndex)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<ShopperClassBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<ShopperClassBean>>) {
                        mView!!.getListCallback(t)
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