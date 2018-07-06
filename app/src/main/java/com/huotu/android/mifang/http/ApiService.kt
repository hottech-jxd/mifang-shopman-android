package com.huotu.android.mifang.http

import com.huotu.android.mifang.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @POST("recycle/sys/init")
    fun init(): Observable<ApiResult<InitDataBean>>

    /**
     * 根据手机号、邀请码和验证码注册用户
     */
//    @POST("user/register")
//    @FormUrlEncoded
//    fun register(@Field("mobile") mobile: String,
//                 @Field("inviteCode") inviteCode: String,
//                 @Field("verifyCode") verifyCode: String,
//                 @Field("safeCode") safeCode: String = ""): Observable<ApiResult<UserBean>>


    /**
     * 根据手机号、验证码 实现用户登录逻辑
     * @param username  手机号
     * @param verifyCode  验证码
     * @param safeCode 安全码
     * @return
     */
//    @POST("user/login")
//    @FormUrlEncoded
//    fun login(@Field("mobile") mobile: String,
//              @Field("verifyCode") verifyCode: String,
//              @Field("safeCode") safeCode: String = ""): Observable<ApiResult<UserBean>>


    /**
     * 发送短信验证码
     */
    @FormUrlEncoded
    @POST("user/sendVerifyCode")
    fun sendVerifyCode(@Field("mobile") mobile: String): Observable<ApiResult<Any>>


    /**
     * 素材库类目
     */
    @POST("Material/categorys")
    fun materialCategprys(): Observable<ApiResult<List<MaterialCategory>>>

    /**
     * 素材库列表
     */
    @FormUrlEncoded
    @POST("Material/list")
    fun materialList(@Field("typeId") typeId: Int, @Field("pageIndex") pageIndex: Int = 1): Observable<ApiResult<List<Quan>>>


    /**
     * 个人中心
     */
    @GET("user/Index")
    fun myIndex(): Observable<ApiResult<MyBean>>

    /**
     * 设置接口
     */
    @GET("user/setting")
    fun setting(): Observable<ApiResult<SettingBean>>

    /**
     * 我的钱包
     */
    @GET("user/MyWallet")
    fun myWallet(): Observable<ApiResult<MyWalletBean>>

    /**
     * 更改手机号码
     */
    @POST("user/updateMobile")
    fun updateMobile(@Field("mobile") mobile: String, @Field("code") code: String): Observable<ApiResult<Any>>

    /**
     * 发送验证码
     */
    @POST("user/sendCode")
    fun sendCode(@Field("mobile") mobile: String): Observable<ApiResult<Any>>

    /**
     * 修改支付密码
     */
    @POST("user/updatePayPassword")
    fun updatePayPassword(@Field("payPassword") payPassword: String): Observable<ApiResult<Any>>

    /**
     * 提现记录
     */
    @POST("user/ApplyList")
    @FormUrlEncoded
    fun applyList(@Field("pageIndex") pageIndex: Int = 1, @Field("pageSize") pageSize: Int = Constants.PAGE_SIZE): Observable<ApiResult<ArrayList<CashRecord>>>

    /**
     * 提现账户列表
     */
    @GET("user/GetAccountList")
    fun getAccountList(): Observable<ApiResult<ArrayList<PayAccount>>>

    /**
     * 提现界面
     */
    @GET("user/ApplyIndex")
    fun applyIndex(): Observable<ApiResult<CashIndex>>

    /**
     * 设置默认提现账户
     */
    @POST("user/SetDefaultAccount")
    @FormUrlEncoded
    fun setDefaultAccount(@Field("AccountId") AccountId: Long = 0): Observable<ApiResult<Any>>

    /**
     * 编辑提现账户
     */
    @POST("user/EditAccount")
    @FormUrlEncoded
    fun editAccount(@Field("RealName") RealName: String /*真实姓名（支付宝必传，微信钱包可不传）*/,
                    @Field("AccountInfo") AccountInfo: String /*提现账户（支付宝必传，微信钱包可不传）*/,
                    @Field("AccountType") AccountType: Int/*提现账户类型，1支付宝 2 银行卡 4 微信零钱 5 结算通 6 API打款*/,
                    @Field("AccountId") AccountId: Long = 0/*默认账号Id*/): Observable<ApiResult<Any>>

    /**
     * 删除提现账户
     */
    @POST("user/DelAccount")
    @FormUrlEncoded
    fun deleteAccount(@Field("AccountId") AccountId: Long): Observable<ApiResult<Any>>

    /***
     *
     * 可/待用积分
     */
    @GET("user/GetIntegralList")
    fun getIntegralList(@Query("SearchType") SearchType: Int /*积分类型(0-可用,1-待定)*/, @Query("pageIndex") pageIndex: Int, @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE): Observable<ApiResult<ScoreBean>>

    /**
     * 获取个性化二维码页面
     */
    @GET("user/getQrcode")
    fun getQrcode(): Observable<ApiResult<String>>

    /**
     * 我的收益
     */
    @GET("Profit/GetProfitIndex")
    fun getProfitIndex(): Observable<ApiResult<ProfitIndexBean>>

    /**
     * 收益列表
     */
    @GET("Profit/GetProfitItems")
    fun getProfitItems(@Query("CountType") CountType: Int, @Query("SearchYear") SearchYear: Int, @Query("SearchMonth") SearchMonth: Int): Observable<ApiResult<ArrayList<ProfitItemBean>>>

    /**
     * 我的推广订单
     */
    @GET("Order/GetProfitOrderList")
    fun getProfitOrderList(@Query("SearchTime") SearchTime: Int = -1 /*int	查询类型（默认-1全部,0-日,1-周,2-月）*/,
                           @Query("ShipStatus") ShipStatus: Int = -1    /*发货状态(默认-1全部,0-待发货,1-待收货,4-已退货,5-确认收货)*/,
                           @Query("SearchYear") SearchYear: Int = -1    /*查询年，默认-1*/,
                           @Query("SearchMonth") SearchMonth: Int = -1 /*	查询月，默认-1 */,
                           @Query("SearchDay") SearchDay: Int = -1    /*查询天,默认-1*/,
                           @Query("WeekNum") WeekNum: Int = -1 /*	查询周,默认-1*/,
                           @Query("PageIndex") PageIndex: Int = 1    /*页码，默认1*/,
                           @Query("PageSize") PageSize: Int = Constants.PAGE_SIZE    /*每页显示数量，默认10)*/): Observable<ApiResult<ArrayList<OrderBean>>>

    /**
     * 我的团队
     */
    @GET("User/TeamIndex")
    fun getTermIndex(): Observable<ApiResult<TermIndexBean>>

    /**
     * 我的团队列表
     */
    @GET("User/GetMemberList")
    fun getMemberList(@Query("SearchDayType") SearchDayType: Int = -1   /*查询时间类型,默认-1全部,0-今日,1-本月*/,
                      @Query("BuyNum") BuyNum: Int = -1 /*	购买次数 默认全部-1,无订单0,以上为传入个数*/,
                      @Query("LevelId") LevelId: Int = -1 /*	用户身份等级 默认全部-1,其他传入用户等级Id*/,
                      @Query("Relation") Relation: Int = -1 /*	直接、间接团队，默认全部-1，直接0，间接1*/,
                      @Query("BindMobile") BindMobile: Int = -1    /*绑定手机号 默认-1 1绑定 0不绑定*/,
                      @Query("Activate") Activate: Int = -1    /*是否活跃,默认全部-1,活跃1,不活跃0*/,
                      @Query("ActivateHour") ActivateHour: Int    /*多少时间内活跃*/,
                      @Query("OrderByType") OrderByType: Int    /*排序：0注册时间顺序 1注册时间倒序 2积分倒序 3积分顺序 4粉丝数倒序 5粉丝数顺序 6 最后登录时间倒序 7 最后登录时间顺序*/,
                      @Query("PageIndex") PageIndex: Int = 1   /*页码，默认1*/,
                      @Query("PageSize") PageSize: Int)
            : Observable<ApiResult<TermBean>>

    /**
     * 获取商家所有等级
     */
    @GET("User/GetLevelList")
    fun getLevelList():Observable<ApiResult<ArrayList<LevelBean>>>

    /**
     * 登录
     */
    @POST("user/loginByUnionId")
    @FormUrlEncoded
    fun loginByUnionId(@Field("openId") openId:String, @Field("unionId") unionId:String,@Field("nickName")nickName:String,@Field("userHead") userHead:String):Observable<ApiResult<UserBean>>


    /**
     * 检测app版本更新
     */
    @POST("sys/checkAppVersion")
    @FormUrlEncoded
    fun checkAppVersion(@Field("appType") appType:String,@Field("version") version:String):Observable<ApiResult<AppVersionBean>>

}