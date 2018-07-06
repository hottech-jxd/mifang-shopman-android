package com.huotu.android.mifang.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.aigestudio.wheelpicker.WheelPicker
import com.aigestudio.wheelpicker.widgets.WheelDayPicker
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker
import com.aigestudio.wheelpicker.widgets.WheelYearPicker
import com.huotu.android.mifang.R
import java.util.*
import kotlin.collections.ArrayList


/**
 * 操作对话框
 */
class DateDialog(context: Context,
                 var onOperateListener: OnOperateListener? )
    : BaseDialog(context)
        , WheelPicker.OnItemSelectedListener
        , View.OnClickListener {


    var yearPicker:WheelYearPicker
    var monthPicker:WheelMonthPicker
    var dayPicker:WheelDayPicker
    var monthLabel:TextView
    var dayLabel:TextView

    interface OnOperateListener {
        fun operate(year:Int,month:Int, day:Int)
    }

    init {

        if (onOperateListener == null) {
            throw NullPointerException("参数空异常")
        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_date_dialog, null)

        this.addContentView(view)
        yearPicker = view.findViewById(R.id.date_dialog_year)
        monthPicker = view.findViewById(R.id.date_dialog_month)
        dayPicker = view.findViewById(R.id.date_dialog_day)
        monthLabel = view.findViewById(R.id.date_dialog_month_label)
        dayLabel = view.findViewById(R.id.date_dialog_day_label)
        view.findViewById<TextView>(R.id.date_dialog_cancel).setOnClickListener(this)
        view.findViewById<TextView>(R.id.date_dialog_ok).setOnClickListener(this)
        monthPicker.setOnItemSelectedListener(this)

        var year = Calendar.getInstance().get(Calendar.YEAR)
        yearPicker.setYearFrame( year - 5 , year +5 )

    }

    fun show(year:Int, month:Int , day:Int =1, isShowMonth:Boolean=true, isShowDay:Boolean=false){
        yearPicker!!.selectedYear = year

        if(isShowMonth){
            monthPicker.visibility=View.VISIBLE
            monthPicker.visibility = View.VISIBLE
            monthPicker.selectedMonth = month
        }else {
            monthPicker.visibility=View.GONE
            monthLabel.visibility=View.GONE
        }
        if(isShowDay){
            dayPicker.visibility=View.VISIBLE
            dayLabel.visibility=View.VISIBLE
            dayPicker.year = year
            dayPicker.month = month
            dayPicker.selectedDay = day
        }else{
            dayPicker.visibility=View.GONE
            dayLabel.visibility=View.GONE
        }

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
        if(picker is WheelYearPicker){
            dayPicker.year = data.toString().toInt()
        }
        if( picker is WheelMonthPicker){
            dayPicker.month = data.toString().toInt()
//            var days = ArrayList<Int>()
//            when(data.toString().toInt()){
//                1,3,5,7,8,10,12-> {
//                    for (i in 1..31) {
//                        days.add(i)
//                    }
//                }
//               2-> {
//                   for (i in 1..28) {
//                       days.add(i)
//                   }
//               }
//               4,6,9,11->{
//                   for (i in 1..28) {
//                       days.add(i)
//                   }
//               }
//            }
            //(dayPicker as WheelPicker).data = days
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.date_dialog_ok->{
                this.dismiss()

                val year = yearPicker.currentYear
                val month = monthPicker.currentMonth
                val day = dayPicker.currentDay

                if( onOperateListener!= null){
                    onOperateListener!!.operate(year,month, day )
                }
            }
            R.id.date_dialog_cancel->{
                this.dismiss()
            }
        }
    }
}
