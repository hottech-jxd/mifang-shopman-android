package com.huotu.android.mifang.util

import android.content.Context
import android.content.pm.PackageManager
import com.huotu.android.mifang.base.BaseApplication
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


/**
* 读取assets文件工具类
*/
object AssetsUtils {

        fun readAddress(fileName: String): String? {
            var stream : InputStream? = null
            try {
                val resContext = BaseApplication.instance!!.createPackageContext(BaseApplication.instance!!.packageName , Context.CONTEXT_IGNORE_SECURITY)
                val s = resContext.getAssets()
                stream = s.open(fileName)
                val buffer = ByteArray(stream!!.available())
                stream!!.read(buffer)
                stream.close()
                return String(buffer, Charset.forName("utf-8"))
            } catch (nex: PackageManager.NameNotFoundException) {
                return null
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                return null
            } finally {
                if ( stream != null) {
                    stream = null
                }
            }
        }
    }

