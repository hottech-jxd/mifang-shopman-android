package com.huotu.android.mifang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.Filterable
import com.aigestudio.wheelpicker.WheelPicker
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FilterAdapter
import com.huotu.android.mifang.adapter.TermAdpter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.FilterBean
import com.huotu.android.mifang.bean.FilterEntry
import com.huotu.android.mifang.bean.TermBean
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_my_term_detail.*
import kotlinx.android.synthetic.main.layout_header_2.*

class MyTermDetailActivity : BaseActivity<IPresenter>()
        , View.OnClickListener , BaseQuickAdapter.OnItemChildClickListener , WheelPicker.OnItemSelectedListener{
    var type = 1
    var filterData = ArrayList<FilterEntry>()
    var filterAdapter :FilterAdapter?=null
    var termAdpter:TermAdpter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_term_detail)

        if(intent.hasExtra(Constants.INTENT_TERM_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_TERM_TYPE , 1)
        }

        if(type ==1){
            header_title.text="团队详情"
        }else{
            header_title.text="团队新增"
        }

        header_left_image.setOnClickListener(this)
        header_right_image.visibility= View.GONE
        //header_right_image.setOnClickListener(this)
        header_right_text.setOnClickListener(this)
        myterm_detail_select_year.setOnItemSelectedListener(this)
        myterm_detail_select_month.setOnItemSelectedListener(this)
        mytermdetail_filter.setOnClickListener(this)


        var gridLayoutManager = GridLayoutManager(this , 2)

        myterm_detail_lay_filter_recyclerview.layoutManager = gridLayoutManager

        var bean = FilterBean(1,"所属团队" , 0 )
        var item = FilterEntry(bean , 1)
        filterData.add(item)
        bean = FilterBean(11,"一级团队" ,  1 )
        item = FilterEntry(bean , 2)
        filterData.add(item)
        bean = FilterBean(12,"二级团队",1)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        bean = FilterBean(2,"下单次数",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(21,"无订单",2)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(22,"1次及以上",2)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(23,"3次及以上",2)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(24,"5次及以上",2)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(25,"次及以上",2)
        item = FilterEntry(bean , 3)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0), 4)
        filterData.add(item )

        bean = FilterBean(3,"身份",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(31,"粉丝",3)
        item = FilterEntry(bean , 2)
        filterData.add(item)
        bean = FilterBean(32,"店主",3)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        bean = FilterBean(4,"关系",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(41,"直接",4)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(42,"间接",4)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        bean = FilterBean(5,"手机",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(51,"已绑定",5)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(52,"未绑定",5)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item =FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        bean = FilterBean(6,"登录",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(61,"24小时登录",6)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(62,"24小时未登录",6)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

//        bean = FilterBean(7,"公众号",0)
//        item = FilterEntry(bean , 1)
//        filterData.add(item)

//        bean = FilterBean(71,"已关注",7)
//        item = FilterEntry(bean , 2)
//        filterData.add(item)
//
//        bean = FilterBean(72,"未关注",7)
//        item = FilterEntry(bean , 2)
//        filterData.add(item)



        if(filterAdapter==null){
            filterAdapter= FilterAdapter(filterData)
            filterAdapter!!.setSpanSizeLookup(object : BaseQuickAdapter.SpanSizeLookup{
                override fun getSpanSize(gridLayoutManager: GridLayoutManager?, position: Int): Int {
                    if( filterAdapter!!.getItemViewType(position) == 1 ){
                        return 2
                    }else if(filterAdapter!!.getItemViewType(position)==2){
                        return 1
                    }else if(filterAdapter!!.getItemViewType(position) ==3){
                        return 1
                    }else if(filterAdapter!!.getItemViewType(position)==4){
                        return 2
                    }else{
                        return 1
                    }
                }
            })
        }


        filterAdapter!!.setOnItemChildClickListener(this)

        myterm_detail_lay_filter_recyclerview.adapter= filterAdapter


        var data = ArrayList<TermBean>()
        for(i in 0 .. 10){
            data.add(TermBean("",i))
        }
        termAdpter = TermAdpter(data)
        myterm_detail_recyclerview.layoutManager = LinearLayoutManager(this)
        myterm_detail_recyclerview.adapter = termAdpter

    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.mytermdetail_filter->{
                var isOpen =myterm_detail_root.isDrawerOpen(myterm_detail_lay_filter)
                if(isOpen){
                    myterm_detail_root.closeDrawer(myterm_detail_lay_filter)
                }else{
                    myterm_detail_root.openDrawer(myterm_detail_lay_filter)
                }
            }
            R.id.header_left_image->{
                finish()
            }
            R.id.header_right_text->{
                myterm_detail_lay_select.visibility = if( myterm_detail_lay_select.visibility== View.VISIBLE) View.GONE else View.VISIBLE
            }
        }
    }

    private fun setDate(){

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if(view!!.id == R.id.filter_item_container){

            setItemStatus(position)
        }
    }

    private fun setItemStatus( position: Int ){

        var pid = filterData[position].filterBean.pid

        for(i in 0 until filterData.size){
            if( filterData[i].filterBean.pid ==  pid ){
                if(i == position)
                    filterData[i].selected = !filterData[i].selected
                else
                    filterData[i].selected=false
            }
        }

        filterAdapter!!.notifyDataSetChanged()
    }

    override fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int) {
        var year = myterm_detail_select_year.currentYear.toString()
        var month = myterm_detail_select_month.currentMonth.toString()
        header_right_text.text = year+"年"+month
    }
}
