package com.huotu.android.mifang.update

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.text.format.Formatter
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.AppVersionBean
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.contract.CommonContract
import com.huotu.android.mifang.mvp.presenter.CommonPresenter
import com.huotu.android.mifang.util.ToastUtils
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest

class UpdateManager : CommonContract.View ,DialogInterface.OnClickListener {
    var mUrl: String? = null

    // 非wifi网络不检查更新
    var mIsWifiOnly = true

    var mNotifyId = 0

    var mIsManual: Boolean = false

    var mContext: Context?=null

    private var sLastTime: Long = 0

    var appVersionBean:AppVersionBean?=null

    var commonPresenter=CommonPresenter(this)

    fun check(){
        val now = System.currentTimeMillis()
        if (now - sLastTime < 3000) {
            return
        }
        sLastTime = now
        if (TextUtils.isEmpty(mUrl)) {
            throw Exception("url参数值空!")
        }



    }

    fun checkVersion(){
        commonPresenter.checkAppVersion( Constants.OS_TYPE.toString() , BuildConfig.VERSION_NAME )
    }


    override fun showProgress(msg: String) {

    }

    override fun hideProgress() {

    }

    override fun toast(msg: String) {
        ToastUtils.single.showLongToast( mContext!! , msg )
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun checkAppVersionCallback(apiResult: ApiResult<AppVersionBean>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("检测更新发生错误")
            return
        }

        appVersionBean = apiResult.data
    }

    fun tip(appVersionBean: AppVersionBean){

        if (mContext is Activity && (mContext as Activity).isFinishing) {
            return
        }

        val info = appVersionBean
        val size = Formatter.formatShortFileSize(mContext, info.size)
        val content = String.format("最新版本：%1\$s\n新版本大小：%2\$s\n\n更新内容\n%3\$s", info.version, size, info.updateDesc)

        val dialog = AlertDialog.Builder(mContext!!).create()

        dialog.setTitle("应用更新")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)


        val density = mContext!!.resources.displayMetrics.density
        val tv = TextView(mContext)
        tv.movementMethod = ScrollingMovementMethod()
        tv.isVerticalScrollBarEnabled = true
        tv.textSize = 14f
        tv.maxHeight = (250 * density).toInt()

        dialog.setView(tv, (25 * density).toInt(), (15 * density).toInt(), (25 * density).toInt(), 0)


        //val listener = DefaultPromptClickListener(agent, true)

        if (info.updateType==1) {
            tv.text = "您需要更新应用才能继续使用\n\n$content"
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", this)
        } else {
            tv.text = content
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "立即更新", this)
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "以后再说", this)
//            if (info.isIgnorable) {
//                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "忽略该版", listener)
//            }
        }
        dialog.show()
    }


    fun update( appVersionBean: AppVersionBean ){
        var mApkFile = File(mContext!!.externalCacheDir , appVersionBean.md5 + ".apk")
        if (UpdateUtil.verify(mApkFile, appVersionBean.md5!!)) {
            UpdateUtil.install(mContext!! , mApkFile , true )
        } else {
            download()
        }
    }

    fun download( appVersionBean: AppVersionBean){
        var tempFile = mContext!!.externalCacheDir.path + appVersionBean.md5
        FileDownloader.getImpl().create( appVersionBean.updateLink)
                .setPath(tempFile)
                .setListener(object :FileDownloadListener(){
                    override fun warn(task: BaseDownloadTask?) {

                    }

                    override fun completed(task: BaseDownloadTask?) {

                    }

                    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                    override fun error(task: BaseDownloadTask?, e: Throwable?) {

                    }

                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }
                }).start()
    }

    fun ignore(){

    }

    override fun onClick(dialog: DialogInterface , which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> update()
            DialogInterface.BUTTON_NEUTRAL -> ignore()
            DialogInterface.BUTTON_NEGATIVE -> {
            }
        }
        dialog.dismiss()
    }
}


object UpdateUtil{

    fun install(context: Context, file: File, force: Boolean) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        } else {
            val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (force) {
            System.exit(0)
        }
    }


    fun verify(apk: File, md5: String): Boolean {
        if (!apk.exists()) {
            return false
        }
        val _md5 = md5(apk)
        if (TextUtils.isEmpty(_md5)) {
            return false
        }
        val result = _md5 != null && _md5!!.equals(md5, ignoreCase = true)
        if (!result) {
            apk.delete()
        }
        return result
    }

    fun md5(file: File): String? {
        var digest: MessageDigest? = null
        var fis: FileInputStream? = null
        val buffer = ByteArray(1024)

        try {
            if (!file.isFile) {
                return ""
            }

            digest = MessageDigest.getInstance("MD5")
            fis = FileInputStream(file)

            while (true) {
                val len: Int = fis.read(buffer, 0,1024)
                if ( len == -1) {
                    fis.close()
                    break
                }

                digest!!.update(buffer, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        val var5 = BigInteger(1, digest!!.digest())
        return String.format("%1$032x", *arrayOf<Any>(var5))
    }


}
