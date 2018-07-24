package com.huotu.android.mifang.http

import com.huotu.android.mifang.bean.*
import io.reactivex.Observable
import okhttp3.Address
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("user/appInit")
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
    @POST("user/Index")
    fun myIndex(): Observable<ApiResult<MyBean>>

    /**
     * 设置接口
     */
    @POST("user/setting")
    fun setting(): Observable<ApiResult<SettingBean>>

    /**
     * 我的钱包
     */
    @POST("user/MyWallet")
    fun myWallet(): Observable<ApiResult<MyWalletBean>>

    /**
     * 更改手机号码
     */
    @POST("user/updateMobile")
    @FormUrlEncoded
    fun updateMobile(@Field("mobile") mobile: String, @Field("code") code: String): Observable<ApiResult<Any>>

    /**
     * 发送验证码
     */
    @POST("user/sendCode")
    @FormUrlEncoded
    fun sendCode(@Field("mobile") mobile: String): Observable<ApiResult<Any>>

    /**
     * 修改支付密码
     */
    @POST("user/updatePayPassword")
    @FormUrlEncoded
    fun updatePayPassword(@Field("payPassword") payPassword: String): Observable<ApiResult<Any>>

    /**
     * 提现记录
     */
    @POST("user/ApplyList")
    @FormUrlEncoded
    fun applyList(@Field("pageIndex") pageIndex: Int = 1,
                  @Field("pageSize") pageSize: Int = Constants.PAGE_SIZE): Observable<ApiResult<ArrayList<CashRecord>>>

    /**
     * 提现账户列表
     */
    @POST("user/GetAccountList")
    fun getAccountList(): Observable<ApiResult<ArrayList<PayAccount>>>

    /**
     * 提现界面
     */
    @POST("user/ApplyIndex")
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
    @POST("user/GetIntegralList")
    @FormUrlEncoded
    fun getIntegralList(@Field("SearchType") SearchType: Int /*积分类型(0-可用,1-待定)*/
                        , @Field("pageIndex") pageIndex: Int
                        , @Field("pageSize") pageSize: Int = Constants.PAGE_SIZE): Observable<ApiResult<ScoreBean>>

    /**
     * 获取个性化二维码页面
     */
    @POST("user/getQrcode")
    fun getQrcode(): Observable<ApiResult<String>>

    /**
     * 我的收益
     */
    @POST("Profit/GetProfitIndex")
    fun getProfitIndex(): Observable<ApiResult<ProfitIndexBean>>

    /**
     * 收益列表
     */
    @POST("Profit/GetProfitItems")
    @FormUrlEncoded
    fun getProfitItems(@Field("CountType") CountType: Int,
                       @Field("SearchYear") SearchYear: Int,
                       @Field("SearchMonth") SearchMonth: Int): Observable<ApiResult<ArrayList<ProfitItemBean>>>

    /**
     * 我的推广订单
     */
    @POST("Order/GetProfitOrderList")
    @FormUrlEncoded
    fun getProfitOrderList(@Field("SearchTime") SearchTime: Int = -1 /*int	查询类型（默认-1全部,0-日,1-周,2-月）*/,
                           @Field("ShipStatus") ShipStatus: Int = -1    /*发货状态(默认-1全部,0-待发货,1-待收货,4-已退货,5-确认收货)*/,
                           @Field("SearchYear") SearchYear: Int = -1    /*查询年，默认-1*/,
                           @Field("SearchMonth") SearchMonth: Int = -1 /*	查询月，默认-1 */,
                           @Field("SearchDay") SearchDay: Int = -1    /*查询天,默认-1*/,
                           @Field("WeekNum") WeekNum: Int = -1 /*	查询周,默认-1*/,
                           @Field("OrderSourceType") OrderSourceType:Int =-1 /*订单来源类型,默认-1，主要用于查看邀请的营养师订单，传入100 —新增条件*/,
                           @Field("PageIndex") PageIndex: Int = 1    /*页码，默认1*/,
                           @Field("PageSize") PageSize: Int = Constants.PAGE_SIZE    /*每页显示数量，默认10)*/): Observable<ApiResult<ArrayList<OrderBean>>>

    /**
     * 我的团队
     */
    @POST("User/TeamIndex")
    fun getTermIndex(): Observable<ApiResult<TermIndexBean>>

    /**
     * 我的团队列表
     */
    @POST("User/GetMemberList")
    @FormUrlEncoded
    fun getMemberList(@Field("SearchDayType") SearchDayType: Int = -1   /*查询时间类型,默认-1全部,0-今日,1-本月*/,
                      @Field("BuyNum") BuyNum: Int = -1 /*	购买次数 默认全部-1,无订单0,以上为传入个数*/,
                      @Field("LevelId") LevelId: Int = -1 /*	用户身份等级 默认全部-1,其他传入用户等级Id*/,
                      @Field("Relation") Relation: Int = -1 /*	直接、间接团队，默认全部-1，直接0，间接1*/,
                      @Field("BindMobile") BindMobile: Int = -1    /*绑定手机号 默认-1 1绑定 0不绑定*/,
                      @Field("Activate") Activate: Int = -1    /*是否活跃,默认全部-1,活跃1,不活跃0*/,
                      @Field("ActivateHour") ActivateHour: Int    /*多少时间内活跃*/,
                      @Field("OrderByType") OrderByType: Int    /*排序：0注册时间顺序 1注册时间倒序 2积分倒序 3积分顺序 4粉丝数倒序 5粉丝数顺序 6 最后登录时间倒序 7 最后登录时间顺序*/,
                      @Field("PageIndex") PageIndex: Int = 1   /*页码，默认1*/,
                      @Field("PageSize") PageSize: Int)
            : Observable<ApiResult<TermBean>>

    /**
     * 获取商家所有等级
     */
    @POST("User/GetLevelList")
    fun getLevelList():Observable<ApiResult<ArrayList<LevelBean>>>

    /**
     * 登录
     */
    @POST("user/loginByUnionId")
    @FormUrlEncoded
    fun loginByUnionId(@Field("openId") openId:String,
                       @Field("unionId") unionId:String,
                       @Field("nickName")nickName:String,
                       @Field("userHead") userHead:String):Observable<ApiResult<UserBean>>


    /**
     * 检测app版本更新
     */
    @POST("sys/checkAppVersion")
    @FormUrlEncoded
    fun checkAppVersion(@Field("appType") appType:String,
                        @Field("version") version:String):Observable<ApiResult<AppVersionBean>>

    /**
     * 邀请入驻页面
     */
    @POST("Profit/Invitations")
    fun invitations():Observable<ApiResult<InviteBean>>

    /**
     * 觅豆列表
     */
    @POST("User/GetMiBeanList")
    @FormUrlEncoded
    fun getMiBeanList(@Field("PageIndex") PageIndex:Int,
                      @Field("PageSize") PageSize:Int=Constants.PAGE_SIZE):Observable<ApiResult<MiBean>>

    /**
     * 获取支付方式
     */
    @POST("Order/paytypelist")
    fun getPaymentItem():Observable<ApiResult<ArrayList<PaymentItem>>>

    /**
     * 获取采购账号商品
     */
    @POST("Order/GetRenewGoods")
    fun getRenewGoods():Observable<ApiResult<GoodsBean>>

    /**
     * 采购账号提交订单
     */
    @POST("Order/SubmitInvitationPurchseOrder")
    @FormUrlEncoded
    fun submotInviteOrder(@Field("quantity") quantity:Int , @Field("payType") payType:Int):Observable<ApiResult<InviteOrderBean>>

    /**
     * 店主课堂分类
     */
    @POST("ShopClassRoom/categorys")
    fun getShopClassCategorys():Observable<ApiResult<ArrayList<ShopperClassCategoryBean>>>

    /**
     * 店主课堂列表
     */
    @POST("ShopClassRoom/list")
    @FormUrlEncoded
    fun getShopClassList(@Field("typeId") typeId:Int , @Field("pageIndex") pageIndex:Int):Observable<ApiResult<ArrayList<ShopperClassBean>>>

    /**
     * 反馈与建议
     */
    @POST("User/FeedbackSuggestion")
    @FormUrlEncoded
    fun feedback(@Field("SubmitType") SubmitType:Int /*反馈or建议， 0-反馈 1-建议*/, @Field("Memo") Memo:String, @Field("UserMobile") UserMobile:String):Observable<ApiResult<Any>>


    /**
     * 货款充值页
     */
    @POST("Order/GetDepositGoods")
    fun getDepositGoods():Observable<ApiResult<DepositBean>>

    /**
     * 货款充值订单提交
     */
    @POST("Order/SubmitGoodsDepositOrder")
    @FormUrlEncoded
    fun submitGoodsDepositOrder(@Field("goodsId")	goodsId :Long,	//商品Id
                                @Field("productId") 	productId:Long,//	货品Id
                                @Field("payType") payType:Int //	支付方式
                                ):Observable<ApiResult<DepositOrderBean>>

    /**
     * 货款列表
     */
    @POST("User/GetDepositList")
    @FormUrlEncoded
    fun getDepositList(@Field("PageIndex") PageIndex:Int, @Field("PageSize") PageSize:Int):Observable<ApiResult<ArrayList<PayLoanBean>>>


    /**
     * 消息列表
     */
    @POST("User/GetJPushList")
    @FormUrlEncoded
    fun getJPushList(@Field("Type")Type:Int,
                     @Field("PageIndex") PageIndex:Int,
                     @Field("PageSize") PageSize:Int):Observable<ApiResult<ArrayList<MessageBean>>>

    /**
     * 小店首页
     */
    @POST("store/index")
    @FormUrlEncoded
    fun getStoreIndex(@Field("page") page:Int , @Field("pageSize")pageSize:Int):Observable<ApiResult<StoreIndex>>

    /**
     * 商品详情
     */
    @POST("store/goodsInfo")
    @FormUrlEncoded
    fun getGoodsInfo(@Field("goodsId") goodsId:Long):Observable<ApiResult<GoodsDetailBean>>

    /**
     * 小店货款信息
     */
    //@POST("user/userAccountInfo")
    @POST("user/walletaccount")
    fun getShopperAccountInfo():Observable<ApiResult<ShopperAccountInfo>>

    /***
     * 获得小店设置信息
     */
    @POST("store/info")
    fun getStoreInfo():Observable<ApiResult<ShopperInfo>>

    /**
     * 店铺设置
     * 类型 0店铺头像1店铺名称2分享标题3分享内容
     */
    @POST("store/setting")
    @FormUrlEncoded
    fun shopperSetting(@Field("type") type:Int , @Field("content") content:String):Observable<ApiResult<Any>>

    /**
     * 上传图片
     */
    @POST("other/uploadPicture")
    @Multipart
    fun uploadImage(@PartMap  params :  Map<String, @JvmSuppressWildcards RequestBody> ):Observable<ApiResult<UploadImageBean>>

    /**
     * 升级商品详情
     */
    @POST("/store/upgradeGoodsInfo")
    @FormUrlEncoded
    fun agentUpgrade(@Field("goodsId") goodsId:Long):Observable<ApiResult<GoodsDetailBean>>

    /**
     * 冻结货款流水
     */
    @POST("store/frozenFlow")
    @FormUrlEncoded
    fun getFrozenFlow(@Field("page") page:Int , @Field("pageSize") pageSize:Int ):Observable<ApiResult<ArrayList<FrozenFlow>>>


    /**
     * 获取成为代理商商品
     */
    @POST("Order/GetAgentUpgradeGoods")
    fun getAgentUpgradeGoods():Observable<ApiResult<AgentUpgradeGoodsBean>>

    /**
     * 收货地址管理
     */
    @POST("user/addressList")
    fun getAddressList():Observable<ApiResult<ArrayList<AddressBean>>>

    /**
     * 提交代理商申请订单
     */
    @POST("order/SubmitAgentUpgradeOrder")
    @FormUrlEncoded
    fun submitAgentUpgradeOrder(@Field("shipName") shipName:String
                                ,@Field("shipMobile") shipMobile:String
                                ,@Field("shipAddress") shipAddress:String
                                ,@Field("shipArea") shipArea:String
                                ,@Field("shipAreaCode") shipAreaCode:String
                                ,@Field("payType")payType:Int):Observable<ApiResult<InviteOrderBean>>

    /**
     * 素材转发成功
     */
    @POST("Material/success")
    @FormUrlEncoded
    fun shareSuccess(@Field("dataId") dataId:Long):Observable<ApiResult<Any>>


    /**
     * 首页弹框广告
     */
    @POST("other/pop")
    fun popAd():Observable<ApiResult<AdBean>>

}