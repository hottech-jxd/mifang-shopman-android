package com.huotu.android.mifang.widget

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.aigestudio.wheelpicker.WheelPicker
import com.aigestudio.wheelpicker.widgets.WheelYearPicker
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.AppVersionBean
import com.huotu.android.mifang.bean.ProvinceCityArea
import com.huotu.android.mifang.mvp.contract.CommonContract
import com.huotu.android.mifang.mvp.presenter.CommonPresenter
import kotlin.collections.ArrayList


/**
 * 省市区选择弹出框
 */
class ProvinceCityAreaDialog(context: Context,
                             var onOperateListener: OnOperateListener? )
    : BaseDialog(context)
        ,CommonContract.View
        , WheelPicker.OnItemSelectedListener
        , View.OnClickListener {


    var provincePicker:WheelPicker?=null
    var cityPicker:WheelPicker?=null
    var areaPicker:WheelPicker?=null
    var progress :ProgressWidget?=null

    interface OnOperateListener {
        fun operate(province:ProvinceCityArea, city:ProvinceCityArea, area:ProvinceCityArea?)
    }

    init {

        if (onOperateListener == null) {
            throw NullPointerException("参数空异常")
        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_province_city_area_dialog, null)

        this.addContentView(view)
        provincePicker = view.findViewById(R.id.province_city_area_dialog_province)
        cityPicker = view.findViewById(R.id.province_city_area_dialog_city)
        areaPicker = view.findViewById(R.id.province_city_area_dialog_area)
        progress=view.findViewById(R.id.province_city_area_dialog_progress)

        view.findViewById<TextView>(R.id.province_city_area_dialog_cancel).setOnClickListener(this)
        view.findViewById<TextView>(R.id.province_city_area_dialog_ok).setOnClickListener(this)
        provincePicker!!.setOnItemSelectedListener(this)
        cityPicker!!.setOnItemSelectedListener(this)


        initProvinceCityArea()
    }

    private fun initProvinceCityArea(){
        if(BaseApplication.instance!!.variable!!.proviceData ==null){
            var iPresenter=CommonPresenter(this)
            provincePicker!!.data=ArrayList<ProvinceCityArea>()
            areaPicker!!.data=ArrayList<ProvinceCityArea>()
            cityPicker!!.data=ArrayList<ProvinceCityArea>()
            iPresenter.getProvinceCityArea()
        }else{
            provincePicker!!.data = BaseApplication.instance!!.variable.proviceData
            cityPicker!!.data = ArrayList<ProvinceCityAreaDialog>()
            areaPicker!!.data=ArrayList<ProvinceCityAreaDialog>()

            if(BaseApplication.instance!!.variable.proviceData!!.size>0){
                provincePicker!!.selectedItemPosition = 0
                var bean =BaseApplication.instance!!.variable.proviceData!![0]
                if(bean.children!=null && bean.children!!.size>0) {
                    cityPicker!!.data = bean.children
                    cityPicker!!.selectedItemPosition = 0
                    var childen = bean.children
                    if(childen!=null && childen.size>0){
                        areaPicker!!.data=childen
                        areaPicker!!.selectedItemPosition=0
                    }
                }
            }
        }
    }


    fun show(province:String, city:String , area:String ){
//        provincePicker!!.currentItemPosition = year
//
//        if(isShowMonth){
//            monthPicker.visibility=View.VISIBLE
//            monthPicker.visibility = View.VISIBLE
//            monthPicker.selectedMonth = month
//        }else {
//            monthPicker.visibility=View.GONE
//            monthLabel.visibility=View.GONE
//        }
//        if(isShowDay){
//            dayPicker.visibility=View.VISIBLE
//            dayLabel.visibility=View.VISIBLE
//            dayPicker.year = year
//            dayPicker.month = month
//            dayPicker.selectedDay = day
//        }else{
//            dayPicker.visibility=View.GONE
//            dayLabel.visibility=View.GONE
//        }

        show()
    }

    override fun show() {
        val window = this.dialog!!.window
        if (window != null) {
            window.setWindowAnimations(R.style.anim_dialog)
        }
        super.show()
    }

    override fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int) {
        if(picker!!.id == R.id.province_city_area_dialog_province){
            var bean = picker.data[position] as ProvinceCityArea
            if(bean.children==null||bean.children!!.size<1){
                cityPicker!!.data=ArrayList<ProvinceCityArea>()
            }else {
                cityPicker!!.data = bean.children
                cityPicker!!.selectedItemPosition=0

                var childen = bean.children!![0].children
                if(childen==null||childen.size<1){
                    areaPicker!!.data = ArrayList<ProvinceCityArea>()
                }else{
                    areaPicker!!.data=childen
                    areaPicker!!.selectedItemPosition=0
                }
            }
        }
        else if( picker!!.id == R.id.province_city_area_dialog_city){
            var bean = picker.data[position] as ProvinceCityArea
            if(bean.children==null||bean.children!!.size<1){
                areaPicker!!.data=  ArrayList<ProvinceCityArea>()
            }else {
                areaPicker!!.data = bean.children
                areaPicker!!.selectedItemPosition=0
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.province_city_area_dialog_ok->{
                this.dismiss()

                val province = provincePicker!!.data[provincePicker!!.currentItemPosition] as ProvinceCityArea
                val city = cityPicker!!.data[ cityPicker!!.currentItemPosition] as ProvinceCityArea
                val area = areaPicker!!.data[ areaPicker!!.currentItemPosition] as ProvinceCityArea

                if( onOperateListener!= null){
                    onOperateListener!!.operate( province,city, area )
                }
            }
            R.id.province_city_area_dialog_cancel->{
                this.dismiss()
            }
        }
    }

    override fun checkAppVersionCallback(apiResult: ApiResult<AppVersionBean>) {

    }

    override fun feedbackCallback(apiResult: ApiResult<Any>) {

    }

    override fun getProvinceCityAreaCallback(result: ArrayList<ProvinceCityArea>) {
        BaseApplication.instance!!.variable.proviceData = result
        initProvinceCityArea()
    }

    override fun showProgress(msg: String) {
        progress!!.visibility=View.VISIBLE
    }

    override fun hideProgress() {
       progress!!.visibility=View.GONE
    }

    override fun toast(msg: String) {
        var toast : Toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}
