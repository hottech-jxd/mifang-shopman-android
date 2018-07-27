package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface GoodsContract {
    interface Presenter: IPresenter {
        fun getGoodsInfo( goodsId:Long)
        fun getStoreIndex(pageIndex:Int, pageSize:Int)
        fun getShopperAccountInfo()
        fun agentUpgrade(goodsId:Long)
        fun getStoreInfo()
    }

    interface View: IView<Presenter> {
        fun getGoodsInfoCallback(apiResult: ApiResult<GoodsDetailBean>)
        fun getStoreIndexCallback(apiResult: ApiResult<StoreIndex>)
        fun getShopperAccountInfoCallback(apiResult: ApiResult<ShopperAccountInfo>)
        fun agentUpgradeCallback(apiResult: ApiResult<GoodsDetailBean>)
        fun getStoreInfoCallback(apiResult: ApiResult<ShopperInfo>)
    }
}