package com.huotu.android.mifang.widget

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.CashIndex
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.mvp.contract.CashContract
import com.huotu.android.mifang.mvp.presenter.CashPresenter
import com.huotu.android.mifang.showToast
import com.huotu.android.mifang.util.DigestUtils
import com.huotu.android.mifang.util.KeybordUtils
import com.huotu.android.mifang.util.ToastUtils


/**
 * 支付对话框
 */
class PayDialog(context: Context,
                var onOperateListener: OnOperateListener? )
    : BaseDialog(context)
        ,CashContract.View
        , PayPsdInputView.onPasswordListener
        , View.OnClickListener {

    private var iPresenter= CashPresenter(this)

    private var pay:PayPsdInputView?=null
    private var progress :ProgressWidget?=null

    interface OnOperateListener {
        fun operate()
    }

    init {

        if (onOperateListener == null) {
            throw NullPointerException("参数空异常")
        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_pay_dialog, null)

        this.addContentView(view)
        var headerTitle = view.findViewById<TextView>(R.id.header_title)
        headerTitle.text="请输入提现密码"
        var headerLeft = view.findViewById<ImageView>(R.id.header_left_image)
        headerLeft.setOnClickListener(this)
        pay = view.findViewById(R.id.pay_dialog_password)
        pay!!.setComparePassword(this)

        progress = view.findViewById(R.id.pay_dialog_progress)
    }



    override fun show() {
        val window = this.dialog!!.window
        if (window != null) {
            window.setWindowAnimations(R.style.anim_dialog)
        }
        super.show()

        pay!!.requestFocus()
        KeybordUtils.openKeybord(context , pay!!)
    }

    override fun onDifference(oldPsd: String?, newPsd: String?) {

    }

    override fun onEqual(psd: String?) {

    }

    override fun inputFinished(inputPsd: String?) {
        if (TextUtils.isEmpty(inputPsd)) {
            ToastUtils.single.showToast("请输入提现密码")
            return
        }
        if(inputPsd!!.length!=6){
            ToastUtils.single.showToast("密码输入有误")
            return
        }

        var md5  = DigestUtils.getInstance().md5(inputPsd)!!.toLowerCase()
        iPresenter.judgePassword(md5)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{

                KeybordUtils.closeKeyboard(context!! , pay!!)

                this.dismiss()
            }
        }
    }




    override fun cashListCallback(apiResult: ApiResult<ArrayList<CashRecord>>) {

    }

    override fun cashIndexCallback(apiResult: ApiResult<CashIndex>) {

    }

    override fun submitApplyCallback(apiResult: ApiResult<Any>) {

    }

    override fun judgePasswordCallback(apiResult: ApiResult<Any>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        KeybordUtils.closeKeyboard(context!! , pay!!)
        this.dismiss()
        if(onOperateListener!=null){
            onOperateListener!!.operate()
        }
    }

    override fun showProgress(msg: String) {
        if(progress==null)return
        progress!!.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        if(progress==null)return
       progress!!.visibility=View.GONE
    }

    override fun toast(msg: String) {
        hideProgress()
        ToastUtils.single.showToast(msg)
    }

    override fun error(err: String) {
         toast(err)
    }
}
