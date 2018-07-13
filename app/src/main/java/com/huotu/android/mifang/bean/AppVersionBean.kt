package com.huotu.android.mifang.bean

data class AppVersionBean(
                /**
                 * 内部版本号
                 */
                var versionCode: Int? = null,
        /**
         * 版本号
         */
        var version: String,

        /**
         * 更新类型 0普通更新 1=强制更新
         */
        var updateType: Int = 0,

                /**
                 * 更新连接
                 */
                var updateLink: String? = null,
                        /**
                         * 更新内容
                         */
                        var updateDesc: String? = null,
                                /**
                                 *
                                 */
                                var md5: String ,
                                        /**
                                         * apk文件大小
                                         */
                                        var size: Long = 0
)