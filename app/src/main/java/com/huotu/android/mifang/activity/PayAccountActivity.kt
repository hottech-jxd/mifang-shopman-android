package com.huotu.android.mifang.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.PayAccountAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.PayAccountContract
import com.huotu.android.mifang.mvp.presenter.PayAccountPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.newIntentForResult
import com.huotu.android.mifang.widget.OperateDialog
import com.huotu.android.mifang.widget.RecyclerViewDivider
import com.huotu.android.mifang.widget.TipAlertDialog
import kotlinx.android.synthetic.main.activity_pay_account.*
import kotlinx.android.synthetic.main.layout_header.*
import java.io.Serializable

class PayAccountActivity : BaseActivity<PayAccountContract.Presenter>()
        , View.OnClickListener
        , PayAccountContract.View
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemChildClickListener
        , OperateDialog.OnOperateListener {
    var data=ArrayList<PayAccount>()
    var payAccountAdapter:PayAccountAdapter?=null
    var operateDialog :OperateDialog?=null
    var currentPosition=-1
    var iPresenter=PayAccountPresenter(this)
    var accountId =0L
    var REQUEST_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_account)

        if(intent.hasExtra(Constants.INTENT_PAY_ACCOUNT_ID)){
            accountId = intent.getLongExtra(Constants.INTENT_PAY_ACCOUNT_ID, 0L)
        }

        header_left_image.setOnClickListener(this)
        header_title.text="选择提现账号"
        header_right_image.visibility=View.VISIBLE
        header_right_image.setImageResource(R.mipmap.add)
        header_right_image.setOnClickListener(this)


        payAccountAdapter= PayAccountAdapter(data)
        pay_account_recyclerview.layoutManager=LinearLayoutManager(this)
        pay_account_recyclerview.adapter = payAccountAdapter
        pay_account_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this, R.color.bg_line), 1f))

        payAccountAdapter!!.onItemChildClickListener=this
        payAccountAdapter!!.onItemClickListener = this

        iPresenter.getAccountList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode== REQUEST_CODE && resultCode== Activity.RESULT_OK){
            iPresenter.getAccountList()
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        pay_account_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        pay_account_progress.visibility=View.GONE
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_image->{
                newIntent<EditPayAccountActivity>(Constants.INTENT_OPERATE_TYPE, 0)
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var dataIntent = Intent()
        dataIntent.putExtra(Constants.INTENT_PAY_ACCOUNT, data[position] as Serializable)
        this.setResult(android.app.Activity.RESULT_OK, dataIntent)
        finish()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.pay_account_operate->{
                openDialog(position)
            }
            R.id.header_right_image->{
                newIntentForResult<EditPayAccountActivity>( REQUEST_CODE , Constants.INTENT_OPERATE_TYPE , 0 )
            }
//            R.id.pay_account_check,
//            R.id.pay_account_lay_account->{
//                for(i in 0 until data.size ) {
//                    if(i == position) {
//
//                        data[position].checked = !data[position].checked
//                    }else{
//                        data[i].checked=false
//                    }
//
//                }
//                payAccountAdapter!!.notifyDataSetChanged()


//            }
        }
    }

    private fun openDialog( position: Int){
        currentPosition = position
        if(operateDialog==null){
            var list = ArrayList<KeyValue>()
            list.add(KeyValue(1,"修改提现账号"))
            list.add(KeyValue(2,"设为默认"))
            list.add(KeyValue(3,"删除提现账号"))
            operateDialog=OperateDialog(this , this , list , "编辑")
        }

        operateDialog!!.show()
    }

    override fun operate(keyValue: KeyValue) {
        if(keyValue.code==1){
            var bundle=Bundle()
            bundle.putInt(Constants.INTENT_OPERATE_TYPE , 1)
            bundle.putSerializable(Constants.INTENT_BEAN , data[currentPosition] as Serializable)
            newIntentForResult<EditPayAccountActivity>( REQUEST_CODE , bundle )
        }else if(keyValue.code ==2){
            iPresenter.setDefaultAccount( data[currentPosition].AccountId )
        }else if(keyValue.code==3){
            deleteAccount( data[currentPosition].AccountId )
        }
    }

    private fun deleteAccount(accountId:Long){
        var dialog=TipAlertDialog(this,false)
        dialog.show("询问","您确定要删除此账号吗?",object:View.OnClickListener{
            override fun onClick(v: View?) {
                dialog.dismiss()
            }
        }, object :View.OnClickListener{
            override fun onClick(v: View?) {
                dialog.dismiss()
                iPresenter.deleteAccount(accountId)
            }
        })
    }

    override fun getAccountListCallback(apiResult: ApiResult<ArrayList<PayAccount>>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("数据不对")
            return
        }


        data= apiResult.data!!

        for( item in data){
            item.checked = item.AccountId == accountId
        }

        payAccountAdapter!!.setNewData(data)
    }

    override fun setDefaultAccountCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
//        if(apiResult.code==ApiResultCodeEnum.SUCCESS.code){
//            toast(apiResult.msg)
//            return
//        }

        toast(apiResult.msg )

        if(apiResult.code==ApiResultCodeEnum.SUCCESS.code) {
            for(i in 0 until data!!.size) {
                if( i == currentPosition) {
                    data[i].IsDefault = 1
                }else{
                    data[i].IsDefault=0
                }
            }
            payAccountAdapter!!.notifyDataSetChanged()
        }

    }

    override fun editPayAccountCallback(apiResult: ApiResult<Any>) {

    }

    override fun deleteAccountCallback(apiResult: ApiResult<Any>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        iPresenter.getAccountList()
    }
}
