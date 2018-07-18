package com.huotu.android.mifang.update

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.text.format.Formatter
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.bean.*
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

/***
 * app升级检测类
 */
class UpdateManager (var mContext:Context ): CommonContract.View ,DialogInterface.OnClickListener {
    // 非wifi网络不检查更新
    private var mIsWifiOnly = true

    private var mNotifyId = 998

    private var mIsManual: Boolean = false

    //private var mContext: Context?=null

    private var mLastTime: Long = 0

    private var mAppVersionBean:AppVersionBean?=null

    private var commonPresenter=CommonPresenter( this )

    private var notificationDownloadListener:DefaultNotificationDownloadListener?=null

    private var mTempFile:String?=null

    fun check(){
        try {
            val now = System.currentTimeMillis()
            if (now - mLastTime < 3000) {
                return
            }
            mLastTime = now

            if (mIsWifiOnly) {
                if (UpdateUtil.checkWifi(mContext!!)) {
                    checkVersion()
                } else {
                }
            } else {
                if (UpdateUtil.checkNetwork(mContext!!)) {
                    checkVersion()
                } else {
                }
            }
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }


    private fun checkVersion(){
        commonPresenter.checkAppVersion( Constants.OS_TYPE.toString() , BuildConfig.VERSION_NAME )
    }

    override fun showProgress(msg: String) {

    }

    override fun hideProgress() {

    }

    override fun toast(msg: String) {
        hideProgress()
        ToastUtils.single.showLongToast( mContext!! , msg )
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun feedbackCallback(apiResult: ApiResult<Any>) {

    }

    override fun getProvinceCityAreaCallback(result: ArrayList<ProvinceCityArea>) {

    }

    override fun checkAppVersionCallback(apiResult: ApiResult<AppVersionBean>) {
        if (apiResult.code != ApiResultCodeEnum.SUCCESS.code) {
            toast(apiResult.msg)
            return
        }
        if (apiResult.data == null) {
            toast("检测更新发生错误")
            return
        }

        mAppVersionBean = apiResult.data
        UpdateUtil.ensureExternalCacheDir(mContext!!)
        var apkFile = File(mContext!!.externalCacheDir, mAppVersionBean!!.md5 + ".apk")


        if (UpdateUtil.verify( apkFile, mAppVersionBean!!.md5)) {
            doInstall()
        } else {
            tip()
        }

    }

    private fun tip(){

        if ( mContext==null || mContext is Activity && (mContext as Activity).isFinishing) {
            return
        }

        val info = mAppVersionBean!!
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

        if (info.updateType==1) {
            tv.text = "您需要更新应用才能继续使用\n\n$content"
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", this)
        } else {
            tv.text = content
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "立即更新", this)
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "以后再说", this)
        }
        dialog.show()
    }


    private fun update() {
        var apkFile = File(mContext!!.externalCacheDir, mAppVersionBean!!.md5 + ".apk")
        if (UpdateUtil.verify(apkFile, mAppVersionBean!!.md5)) {
            doInstall()
        } else {
            download()
        }
    }

    private fun download(){

        if(notificationDownloadListener==null){
            notificationDownloadListener= DefaultNotificationDownloadListener(mContext!!, mNotifyId)
        }

        mTempFile = mContext!!.externalCacheDir.path + "\\" + mAppVersionBean!!.md5
        FileDownloader.getImpl().create( mAppVersionBean!!.updateLink)
                .setPath(mTempFile)
                .setListener(object :FileDownloadListener(){
                    override fun warn(task: BaseDownloadTask?) {

                    }

                    override fun completed(task: BaseDownloadTask?) {
                        onDownloadFinish(null)
                    }

                    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                    override fun error(task: BaseDownloadTask?, e: Throwable?) {
                        onDownloadFinish( "app下载失败" )
                    }

                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        notificationDownloadListener!!.onProgress( soFarBytes * 100 / totalBytes  )
                    }

                    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }
                }).start()

        notificationDownloadListener!!.onStart()

    }

    override fun onClick(dialog: DialogInterface , which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> update()
            DialogInterface.BUTTON_NEGATIVE -> {
            }
        }
        dialog.dismiss()
    }


    private fun onDownloadFinish(  error :String? ) {
        notificationDownloadListener!!.onFinish()

        if (error != null) {
            toast(error)
        } else {

            var apkFile = File(mContext!!.externalCacheDir, mAppVersionBean!!.md5 + ".apk")
            File(mTempFile).renameTo(apkFile)

            doInstall()
        }
    }

    private fun doInstall(){
        var apkFile =File(mContext!!.externalCacheDir , mAppVersionBean!!.md5+".apk")

        UpdateUtil.install(mContext!!, apkFile, mAppVersionBean!!.updateType == 1)
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


    fun checkWifi(context: Context): Boolean {
        var connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val info = connectivity.activeNetworkInfo
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
    }

    fun checkNetwork(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val info = connectivity.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun ensureExternalCacheDir(context: Context) {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            var file = context.externalCacheDir
            if (file == null) {
                file = context.getExternalFilesDir(null)
                if (file != null) {
                    file = File(file.parentFile, "cache")
                }
            }
            file?.mkdirs()
        }
    }
}


private class DefaultNotificationDownloadListener(private val mContext: Context, private val mNotifyId: Int)  {
    private var mBuilder: NotificationCompat.Builder? = null

    fun onStart() {
        if (mBuilder == null) {
            val title = "下载中 - " + mContext.getString(mContext.applicationInfo.labelRes)
            mBuilder = NotificationCompat.Builder(mContext)
            mBuilder!!.setOngoing(true)
                    .setAutoCancel(false)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(mContext.applicationInfo.icon)
                    .setTicker(title)
                    .setContentTitle(title)
        }
        onProgress(0)
    }

    fun onProgress(progress: Int) {
        if (mBuilder != null) {
            if (progress > 0) {
                mBuilder!!.priority =Notification.PRIORITY_DEFAULT
                mBuilder!!.setDefaults(Notification.DEFAULT_ALL)
            }
            mBuilder!!.setProgress(100, progress, false)
            mBuilder!!.setContentText("下载中 - "+ progress +"%")

            val nm = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(mNotifyId, mBuilder!!.build())
        }
    }

    fun onFinish() {
        val nm = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(mNotifyId)
    }
}