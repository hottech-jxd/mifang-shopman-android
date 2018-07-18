package com.huotu.android.mifang.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ShopperInfo
import com.huotu.android.mifang.mvp.contract.SettingContract
import com.huotu.android.mifang.mvp.presenter.SettingPresenter
import com.huotu.android.mifang.newIntentForResult
import com.huotu.android.mifang.util.ImageUtils
import com.huotu.android.mifang.util.WechatShareUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_set_shopper.*
import kotlinx.android.synthetic.main.layout_header.*
import kotlin.math.log

class SetShopperActivity : BaseActivity<SettingContract.Presenter>()
        ,SettingContract.View
        ,View.OnClickListener{

    var REQUEST_CODE = 4001
    var iPresenter=SettingPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_shopper)

        header_title.text="店铺设置"

        header_left_image.setOnClickListener(this)
        setshopper_lay_1.setOnClickListener(this)
        setshopper_lay_2.setOnClickListener(this)
        setshopper_lay_3.setOnClickListener(this)
        setshopper_lay_4.setOnClickListener(this)
        setshopper_preview.setOnClickListener(this)

        iPresenter.getStoreInfo()
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        setshopper_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        setshopper_progress.visibility=View.GONE
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.header_left_image -> {
                finish()
            }
            R.id.setshopper_lay_1 -> {
                uploadAvator()
            }
            R.id.setshopper_lay_2 -> {
                var bundle=Bundle()
                bundle.putString(Constants.INTENT_TITLE,"更改店铺名称")
                bundle.putInt(Constants.INTENT_SETTING_TYPE, 1)
                bundle.putString(Constants.INTENT_SETTING_CONTENT , setshopper_name.text.toString())
                newIntentForResult<EditInfoActivity>(REQUEST_CODE, bundle)
            }
            R.id.setshopper_lay_3 -> {
                var bundle=Bundle()
                bundle.putString(Constants.INTENT_TITLE,"更改分享标题")
                bundle.putInt(Constants.INTENT_SETTING_TYPE, 2)
                bundle.putString(Constants.INTENT_SETTING_CONTENT , setshopper_share_title.text.toString())
                newIntentForResult<EditInfoActivity>(REQUEST_CODE, bundle)
            }
            R.id.setshopper_lay_4 -> {
                var bundle=Bundle()
                bundle.putString(Constants.INTENT_TITLE,"更改分享内容")
                bundle.putInt(Constants.INTENT_SETTING_TYPE, 3)
                bundle.putString(Constants.INTENT_SETTING_CONTENT , setshopper_share_content.text.toString())
                newIntentForResult<EditInfoActivity>(REQUEST_CODE, bundle )
            }
            R.id.setshopper_preview->{
                //预览小店小程序
                WechatShareUtil().runMinProgram("pages/index/index")
            }
        }
    }

    private fun uploadAvator(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .minSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.SINGLE)
                .previewImage(true)
                .isCamera(true)
                .imageFormat(PictureMimeType.JPEG)
                .isZoomAnim(true)
                .enableCrop(false)
                .compress(true)
                .compressSavePath("")
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == REQUEST_CODE && resultCode== Activity.RESULT_OK){
            var type = data!!.getIntExtra(Constants.INTENT_SETTING_TYPE,-1)
            var content = data!!.getStringExtra(Constants.INTENT_SETTING_CONTENT)
            when(type  ) {
                1 -> setshopper_name.text = content
                2 -> setshopper_share_title.text = content
                3 -> setshopper_share_content.text = content
            }
        }else if(requestCode== PictureConfig.CHOOSE_REQUEST && resultCode== Activity.RESULT_OK){
            var result = PictureSelector.obtainMultipleResult(data)
            var imagePath = "file://"+ result[0].compressPath
            uploadLogo(imagePath)
        }
    }

    fun uploadLogo( path :String){
        var buffer = ImageUtils.fileToByte(path)
        iPresenter.uploadLogo(0, buffer)
    }

    override fun shopperSettingCallback(apiResult: ApiResult<Any>) {
    }

    override fun uploadLogoCallback(apiResult: ApiResult<Map<String, String>>) {
        toast("上传店铺logo成功")
        //todo
        var logoUrl =""
        setshopper_logo.setImageURI(logoUrl)
    }

    override fun getStoreInfo(apiResult: ApiResult<ShopperInfo>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            return
        }

        setshopper_logo.setImageURI( apiResult.data!!.logo )
        setshopper_name.text =apiResult.data!!.name
        setshopper_share_title.text=apiResult.data!!.shareTitle
        setshopper_share_content.text=apiResult.data!!.shareContent
    }
}
