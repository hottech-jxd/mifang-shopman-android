package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView


interface TermContract {
    interface Presenter: IPresenter {
        fun getTermIndex()
        fun getTermMemberList(SearchDayType: Int = -1   /*查询时间类型,默认-1全部,0-今日,1-本月*/,
                              BuyNum: Int = -1 /*	购买次数 默认全部-1,无订单0,以上为传入个数*/,
                             LevelId: Int = -1 /*	用户身份等级 默认全部-1,其他传入用户等级Id*/,
                               Relation: Int = -1 /*	直接、间接团队，默认全部-1，直接0，间接1*/,
                               BindMobile: Int = -1    /*绑定手机号 默认-1 1绑定 0不绑定*/,
                               Activate: Int = -1    /*是否活跃,默认全部-1,活跃1,不活跃0*/,
                               ActivateHour: Int    /*多少时间内活跃*/,
                              OrderByType: Int    /*排序：0注册时间顺序 1注册时间倒序 2积分倒序 3积分顺序 4粉丝数倒序 5粉丝数顺序 6 最后登录时间倒序 7 最后登录时间顺序*/,
                               PageIndex: Int = 1   /*页码，默认1*/,
                              PageSize: Int)

        fun getLevelList()
    }

    interface View: IView<Presenter> {
        fun getTermIndexCallback(apiResult: ApiResult<TermIndexBean>)
        fun getTermMemberListCallback(apiResult: ApiResult<TermBean>)
        fun getLevelListCallback(apiResult: ApiResult<ArrayList<LevelBean>>)
    }
}