package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.OrderContract
import com.huotu.android.mifang.mvp.contract.ProfitContract
import com.huotu.android.mifang.mvp.contract.TermContract
import com.huotu.android.mifang.mvp.model.OrderModel
import com.huotu.android.mifang.mvp.model.ProfitModel
import com.huotu.android.mifang.mvp.model.TermModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TermPresenter(view: TermContract.View) : TermContract.Presenter {

    var mView: TermContract.View? = null
    val mModel: TermModel by lazy { TermModel() }

    init {
        mView = view
    }

    override fun getTermIndex() {

        val observable: Observable<ApiResult<TermIndexBean>>? = mModel.getTermIndex()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<TermIndexBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<TermIndexBean>) {
                        mView!!.getTermIndexCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getTermMemberList(SearchDayType: Int, BuyNum: Int, LevelId: Int, Relation: Int, BindMobile: Int, Activate: Int, ActivateHour: Int, OrderByType: Int, PageIndex: Int, PageSize: Int) {
        val observable: Observable<ApiResult<TermBean>>? = mModel.getTermMemberList(
                SearchDayType, BuyNum, LevelId, Relation, BindMobile, Activate, ActivateHour, OrderByType, PageIndex, PageSize
        )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<TermBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<TermBean>) {
                        mView!!.getTermMemberListCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getLevelList() {
        val observable: Observable<ApiResult<ArrayList<LevelBean>>>? = mModel.getLevelList()

        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<LevelBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<LevelBean>>) {
                        mView!!.getLevelListCallback(t)
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