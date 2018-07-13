package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.OrderContract
import com.huotu.android.mifang.mvp.contract.ProfitContract
import com.huotu.android.mifang.mvp.model.OrderModel
import com.huotu.android.mifang.mvp.model.ProfitModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OrderPresenter(view: OrderContract.View) : OrderContract.Presenter {

    var mView: OrderContract.View? = null
    val mModel: OrderModel by lazy { OrderModel() }

    init {
        mView = view
    }

    override fun getProfitOrderList(SearchTime: Int, ShipStatus: Int, SearchYear: Int,
                                    SearchMonth: Int, SearchDay: Int, WeekNum: Int,OrderSourceType:Int, PageIndex: Int, PageSize: Int) {


        val observable: Observable<ApiResult<ArrayList<OrderBean>>>? = mModel.getProfitOrderList(
                SearchTime,ShipStatus,SearchYear,SearchMonth,SearchDay,WeekNum,OrderSourceType , PageIndex,PageSize )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<OrderBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<OrderBean>>) {
                        mView!!.getProfitOrderListCallback(t)
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