package com.huotu.android.mifang.bean

/**
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017.year. All rights reserved.
 *
 *
 * Created by jinxiangdong on 2017/11/15.
 */
data class KeyValue(var code: Int, var name: String?){
    override fun toString(): String {
        return if(name==null) "" else name!!
    }
}

data class KVEntry(var code:String? ,var name :String?)
