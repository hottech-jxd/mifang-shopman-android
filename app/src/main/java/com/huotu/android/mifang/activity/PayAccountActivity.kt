package com.huotu.android.mifang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.PayAccountAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.KeyValue
import com.huotu.android.mifang.bean.PayAccount
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.widget.OperateDialog
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_pay_account.*
import kotlinx.android.synthetic.main.fragment_promotion.*
import kotlinx.android.synthetic.main.layout_header.*
import java.io.Serializable

class PayAccountActivity : BaseActivity<IPresenter>()
        , View.OnClickListener
        , BaseQuickAdapter.OnItemChildClickListener
        , OperateDialog.OnOperateListener {
    var data=ArrayList<PayAccount>()
    var payAccountAdapter:PayAccountAdapter?=null
    var operateDialog :OperateDialog?=null
    var currentPosition=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_account)

        header_left_image.setOnClickListener(this)
        header_title.text="选择提现账号"
        header_right_image.visibility=View.VISIBLE
        header_right_image.setImageResource(R.mipmap.add)
        header_right_image.setOnClickListener(this)

        for(i in 0.. 10){
            data.add(PayAccount(""))
        }
        payAccountAdapter= PayAccountAdapter(data)
        pay_account_recyclerview.layoutManager=LinearLayoutManager(this)
        pay_account_recyclerview.adapter = payAccountAdapter
        pay_account_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this, R.color.bg_line), 1f))

        payAccountAdapter!!.onItemChildClickListener=this
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.header_right_image->{
                newIntent<EditPayAccountActivity>(Constants.INTENT_OPERATE_TYPE, 0)
            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when(view!!.id){
            R.id.pay_account_operate->{
                openDialog(position)
            }
            R.id.header_right_image->{
                newIntent<EditPayAccountActivity>(Constants.INTENT_OPERATE_TYPE , 0 )
            }
            R.id.pay_account_check,
            R.id.pay_account_lay_account->{
                for(i in 0 until data.size ) {
                    if(i == position) {

                        data[position].checked = !data[position].checked
                    }else{
                        data[i].checked=false
                    }

                }
                payAccountAdapter!!.notifyDataSetChanged()
            }
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
            var intent=Intent()
            intent.putExtra(Constants.INTENT_OPERATE_TYPE , 1)
            intent.putExtra(Constants.INTENT_BEAN , data[currentPosition] as Serializable)
            newIntent<EditPayAccountActivity>(Constants.INTENT_OPERATE_TYPE,1)
        }else if(keyValue.code ==2){
            //todo
        }else if(keyValue.code==3){
            //todo
        }
    }
}
