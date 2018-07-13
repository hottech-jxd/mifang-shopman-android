package com.huotu.android.mifang.bean

data class MessageBean ( var JpushType:Int,  var NotificationContent:String?, var PushTime: Long)


enum class NoticeSceneEnum(var id:Int , var desc:String?) {
    后台系统消息 ( 0, "后台系统消息"),
    营养师到期通知 ( 1,"营养师到期通知"),
    营养师续费成功通知 ( 2,"营养师续费成功通知"),
    代理商货款不足 ( 3,"代理商货款不足"),
    下线会员注册成功通知 ( 4,"下线会员注册成功通知"),
    用户升级为代理商 ( 5,"用户升级为代理商"),
    成功邀请用户成为营养师 ( 6,"成功邀请用户成为营养师"),
    成功邀请用户成为代理商 ( 7,"成功邀请用户成为代理商"),
    会员订单支付成功通知 ( 8,"会员订单支付成功通知"),
    下线会员订单支付成功通知 ( 9,"下线会员订单支付成功通知"),
    用户商品购买成功通知 ( 10,"用户商品购买成功通知")
}


// <summary>
/// 营养师到期/续费通知
/// </summary>
data class DietitianNoticeViewModel(
    /// <summary>
    /// 通知类型
    /// </summary>
     var NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
     var  NoticeScene :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName :String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var UserHeadImgURL :String?,
    /// <summary>
    /// 到期时间
    /// </summary>
    var  ExpireTime:String?,
    /// <summary>
    /// 剩余天数
    /// </summary>
    var  SurplusDay :String?
)


/// <summary>
/// 代理商到期通知
/// </summary>
class AgentExpireViewModel{}

/// <summary>
/// 货款不足通知
/// </summary>
data class DepositNoticeViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
    var  NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName :String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var  UserHeadImgURL :String?,
    /// <summary>
    /// 冻结收益
    /// </summary>
    var  LockIntegral :String?,
    /// <summary>
    /// 已欠货款
    /// </summary>
    var  OweDeposit :String?
)

/// <summary>
/// 系统通知
/// </summary>
data  class SystemNoticeViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
    var  NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知标题
    /// </summary>
    var  NoticeTitle :String?,
    /// <summary>
    /// 通知图片
    /// </summary>
    var  NoticeImgURL :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 跳转地址
    /// </summary>
    var  JumpURL :String?
)

/// <summary>
/// 新会员注册成功通知
/// </summary>
data class UserRegisterViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
    var  NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene  :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 被通知人微信昵称
    /// </summary>
    var  BuddyWxNickName :String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName :String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var  UserHeadImgURL :String?,
    /// <summary>
    /// 推荐人昵称
    /// </summary>
    var  RefereeNickName :String?
)

/// <summary>
/// 代理商升级通知
/// </summary>
data class AgentUpgradeViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
    var  NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName :String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var  UserHeadImgURL:String?,
    /// <summary>
    /// 剩余货款
    /// </summary>
    var  SurplusDeposit :String?
)

/// <summary>
/// 好友升级营养师/代理商通知   营养师发给上线,代理商发给购买地址推送人
/// </summary>
data class UserUpgradeViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
        var  NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene  :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var   NoticeContent:String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName:String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var  UserHeadImgURL :String?,
    /// <summary>
    /// 升级时间
    /// </summary>
    var  UpgradeTime :String?,
    /// <summary>
    /// 获得收益(营养师-收益,代理商-货款)
    /// </summary>
    var  ProfitAmount: String?
)

/// <summary>
/// 订单支付成功通知
/// </summary>
data class OrderPaySuccessViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
    var  NoticeType :Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene:Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 订单名称
    /// </summary>
    var  OrderName :String?,
    /// <summary>
    /// 订单Id
    /// </summary>
    var  OrderId :String?,
    /// <summary>
    /// 订单金额
    /// </summary>
    var  OrderAmount :String?,
    /// <summary>
    /// 支付时间
    /// </summary>
    var  PayTime:String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName :String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var  UserHeadImgURL :String?,
    /// <summary>
    /// 返利金额
    /// </summary>
    var  ProfitAmount :String?
)
/// <summary>
/// 代理订单支付成功通知
/// </summary>
data class AgentOrderPaySuccessViewModel
(
    /// <summary>
    /// 通知类型
    /// </summary>
        var  NoticeType:Int,
    /// <summary>
    /// 通知场景
    /// </summary>
    var  NoticeScene :Int,
    /// <summary>
    /// 通知时间
    /// </summary>
    var  NoticeTime :String?,
    /// <summary>
    /// 通知内容
    /// </summary>
    var  NoticeContent :String?,
    /// <summary>
    /// 订单名称
    /// </summary>
    var  OrderName :String?,
    /// <summary>
    /// 订单Id
    /// </summary>
    var  OrderId :String?,
    /// <summary>
    /// 订单金额
    /// </summary>
    var  OrderAmount :String?,
    /// <summary>
    /// 支付时间
    /// </summary>
    var  PayTime:String?,
    /// <summary>
    /// 用户微信昵称
    /// </summary>
    var  UserWxNickName :String?,
    /// <summary>
    /// 用户头像
    /// </summary>
    var  UserHeadImgURL:String?,
    /// <summary>
    /// 代理金额
    /// </summary>
    var  AgentPrice:String?,
    /// <summary>
    /// 剩余货款
    /// </summary>
    var  SurplusDeposit :String?
)