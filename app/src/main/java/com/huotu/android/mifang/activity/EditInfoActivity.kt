package com.huotu.android.mifang.activity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ShopperInfo
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.SettingContract
import com.huotu.android.mifang.mvp.presenter.SettingPresenter
import com.huotu.android.mifang.util.KeybordUtils
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.layout_header_2.*

class EditInfoActivity : BaseActivity<SettingContract.Presenter>()
        ,SettingContract.View
        ,View.OnClickListener{

    private var type = -1
    private var iPresenter=SettingPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        header_right_image.visibility= View.GONE
        header_right_text.visibility=View.VISIBLE
        header_right_text.setCompoundDrawables(null, null,null,null )
        header_right_text.text = "保存"
        header_right_text.setOnClickListener(this)
        header_left_image.setOnClickListener(this)

        if(intent.hasExtra(Constants.INTENT_SETTING_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_SETTING_TYPE,-1)
        }
        if(intent.hasExtra(Constants.INTENT_SETTING_CONTENT)){
            edit_info_content.setText( intent.getStringExtra(Constants.INTENT_SETTING_CONTENT) )
        }
        if(intent.hasExtra(Constants.INTENT_TITLE)){
            header_title.text = intent.getStringExtra(Constants.INTENT_TITLE)
        }

        edit_info_content.requestFocus()
        KeybordUtils.openKeybord(this, edit_info_content)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_text->{
                save()
            }
        }
    }

    fun save(){
        if( TextUtils.isEmpty( edit_info_content.text )){
            toast("请输入内容")
            edit_info_content.requestFocus()
            KeybordUtils.openKeybord(this, edit_info_content)
            return
        }
        var content = edit_info_content.text.trim().toString()
        iPresenter.shopperSetting( type , content )
    }

    override fun shopperSettingCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        var data = Intent()
        data.putExtra(Constants.INTENT_SETTING_TYPE , type)
        data.putExtra(Constants.INTENT_SETTING_CONTENT , edit_info_content.text.trim().toString())
        setResult(Activity.RESULT_OK, data )
        finish()
    }

    override fun uploadLogoCallback(apiResult: ApiResult<Map<String, String>>) {

    }

    override fun getStoreInfo(apiResult: ApiResult<ShopperInfo>) {
    }
}
