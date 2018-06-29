package com.huotu.android.mifang.http

import com.huotu.android.mifang.bean.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("recycle/sys/init")
    fun init():Observable<ApiResult<InitDataBean>>

    /**
     * 根据手机号、邀请码和验证码注册用户
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(@Field("mobile") mobile:String,
                @Field("inviteCode") inviteCode:String,
                 @Field("verifyCode") verifyCode:String,
                 @Field("safeCode") safeCode:String="" ):Observable<ApiResult<UserBean>>


    /**
     * 根据手机号、验证码 实现用户登录逻辑
     * @param username  手机号
     * @param verifyCode  验证码
     * @param safeCode 安全码
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("mobile") mobile: String,
                          @Field("verifyCode") verifyCode: String,
                          @Field("safeCode") safeCode: String="" ): Observable<ApiResult<UserBean>>


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
    fun materialCategprys():Observable<ApiResult<List<MaterialCategory>>>

    /**
     * 素材库列表
     */
    @FormUrlEncoded
    @POST("Material/list")
    fun materialList(@Field("typeId") typeId:Int , @Field("pageIndex") pageIndex:Int=1 ):Observable<ApiResult<List<Quan>>>


    /**
     * 个人中心
     */
    @GET("user/Index")
    fun myIndex():Observable<ApiResult<MyBean>>

    /**
     * 设置接口
     */
    @GET("user/setting")
    fun setting():Observable<ApiResult<SettingBean>>

    /**
     * 我的钱包
     */
    @GET("user/MyWallet")
    fun myWallet():Observable<ApiResult<MyWalletBean>>

    /**
     * 更改手机号码
     */
    @POST("user/updateMobile")
    fun updateMobile(@Field("mobile") mobile:String,@Field("code")  code:String):Observable<ApiResult<Any>>

    /**
     * 发送验证码
     */
    @POST("user/sendCode")
    fun sendCode(@Field("mobile") mobile:String):Observable<ApiResult<Any>>

    /**
     * 修改支付密码
     */
    @POST("user/updatePayPassword")
    fun updatePayPassword( @Field("payPassword") payPassword :String ):Observable<ApiResult<Any>>

    /**
     * 提现记录
     */
    @POST("user/ApplyList")
    @FormUrlEncoded
    fun applyList(@Field("pageIndex") pageIndex:Int=1 ,@Field("pageSize") pageSize:Int= Constants.PAGE_SIZE):Observable<ApiResult<ArrayList<CashRecord>>>

    /**
     * 提现账户列表
     */
    @GET("user/GetAccountList")
    fun getAccountList():Observable<ApiResult<ArrayList<PayAccount>>>

    /**
     * 提现界面
     */
    @GET("user/ApplyIndex")
    fun applyIndex():Observable<ApiResult<CashIndex>>

    /**
     * 设置默认提现账户
     */
    @POST("user/SetDefaultAccount")
    @FormUrlEncoded
    fun setDefaultAccount(@Field("AccountId") AccountId:Long=0):Observable<ApiResult<Any>>

    /**
     * 编辑提现账户
     */
    @POST("user/EditAccount")
    @FormUrlEncoded
    fun editAccount(@Field("RealName") RealName:String /*真实姓名（支付宝必传，微信钱包可不传）*/,
                    @Field("AccountInfo") AccountInfo:String /*提现账户（支付宝必传，微信钱包可不传）*/,
                    @Field("AccountType") AccountType:Int/*提现账户类型，1支付宝 2 银行卡 4 微信零钱 5 结算通 6 API打款*/,
                    @Field("AccountId") AccountId:Long=0/*默认账号Id*/):Observable<ApiResult<Any>>

    /**
     * 删除提现账户
     */
    @POST("user/DelAccount")
    @FormUrlEncoded
    fun deleteAccount(@Field("AccountId") AccountId:Long):Observable<ApiResult<Any>>

}