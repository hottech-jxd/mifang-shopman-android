package com.huotu.android.mifang.util


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ShareBean
import com.huotu.android.mifang.utils.AppUtil
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.*

class WechatShareUtil : FileDownloadListener() {

    /**
     * 分享小程序
     */
    fun shareMiniProgram( resources: Resources , title:String , desc:String, webUrl:String ,
                          miniProgramUserName:String
                         , miniProgramPath:String ){
        var wxMiniProgramObject = WXMiniProgramObject()
        wxMiniProgramObject.webpageUrl = webUrl

        if(BuildConfig.DEBUG) {
            wxMiniProgramObject.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW
        }else {
            wxMiniProgramObject.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE
        }
        wxMiniProgramObject.userName = miniProgramUserName
        wxMiniProgramObject.path = miniProgramPath
        var wxMediaMessage = WXMediaMessage(wxMiniProgramObject)
        wxMediaMessage.title = title
        wxMediaMessage.description = desc
        wxMediaMessage.setThumbImage( BitmapFactory.decodeResource( resources ,  R.mipmap.ic_launcher))

        var req = SendMessageToWX.Req()
        req.transaction = "webpage"
        req.message = wxMediaMessage
        req.scene =SendMessageToWX.Req.WXSceneSession
        AppInit.iwxApi!!.sendReq( req)
    }

    /**
     * app打开小程序
     */
    fun runMinProgram( path:String){
        var req = WXLaunchMiniProgram.Req()
        req.userName = BuildConfig.wechat_miniprogram_id
        req.path= path
        if(BuildConfig.DEBUG){
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW
        }else {
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
        }
        AppInit.iwxApi!!.sendReq(req)
    }

    fun shareWechat( bean:ShareBean ){

        var fileName = AppUtil.getFileName( bean.logoUrl )
        var path = Constants.ImageDirPath + fileName
        bean.localPath = path
        bean.shareType = SendMessageToWX.Req.WXSceneSession

        FileDownloader.getImpl()
                .create(bean.logoUrl)
                .setPath(path)
                .setListener(this)
                .setTag( bean )
                .setAutoRetryTimes(1)
                .start()
    }

    fun shareWechatOfOneImage( thumbBitmap: Bitmap , bitmap: Bitmap, shareType:Int){
        var wxImageObject = WXImageObject(bitmap)
        var wxMediaMessage = WXMediaMessage()
        wxMediaMessage.mediaObject = wxImageObject
        //设置缩略图
        //wxMediaMessage.thumbData = ImageUtils.bitmap2Bytes( ImageUtils.compressByQuality(thumbBitmap, 32*1024L) , Bitmap.CompressFormat.JPEG)

        var req= SendMessageToWX.Req()
        req.transaction="img"
        req.message=wxMediaMessage
        req.scene= shareType //SendMessageToWX.Req.WXSceneSession

        AppInit.iwxApi!!.sendReq( req)
    }



    fun shareWechatMoments(bean: ShareBean){
        var fileName = AppUtil.getFileName( bean.logoUrl )
        var path = Constants.ImageDirPath + fileName
        bean.localPath = path
        bean.shareType = SendMessageToWX.Req.WXSceneTimeline

        FileDownloader.getImpl()
                .create(bean.logoUrl)
                .setPath(path)
                .setListener(this)
                .setTag( bean )
                .setAutoRetryTimes(1)
                .start()
    }

    fun shareWechatFavorite(bean: ShareBean){
        var fileName = AppUtil.getFileName( bean.logoUrl )
        var path = Constants.ImageDirPath + fileName
        bean.localPath = path
        bean.shareType = SendMessageToWX.Req.WXSceneFavorite

        FileDownloader.getImpl()
                .create(bean.logoUrl)
                .setPath(path)
                .setListener(this)
                .setTag( bean )
                .setAutoRetryTimes(1)
                .start()
    }

     private fun share(  bean:ShareBean){
        var wxWebpageObject = WXWebpageObject()
        wxWebpageObject.webpageUrl = bean.webUrl

        var wxMediaMessage=WXMediaMessage(wxWebpageObject)
        wxMediaMessage.title = bean.title
        wxMediaMessage.description = bean.description
        wxMediaMessage.thumbData =bean.buffer

         var req = SendMessageToWX.Req()
         req.transaction="webpage"
         req.message=wxMediaMessage
         req.scene = bean.shareType

         AppInit.iwxApi!!.sendReq(req)
    }

    override fun warn(task: BaseDownloadTask?) {

    }

    override fun completed(task: BaseDownloadTask?) {
        var bean = task!!.tag as ShareBean
        if(bean==null)return

        var path =bean.localPath
        var buffer = ImageUtils.fileToByte(path)
        bean.buffer = buffer
        share(bean)
    }

    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

    }

    override fun error(task: BaseDownloadTask?, e: Throwable?) {
        e!!.printStackTrace()
        ToastUtils.single.showToast( "下载图片失败" )
    }

    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

    }

    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

    }
}