package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aigestudio.wheelpicker.WheelPicker
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FilterAdapter
import com.huotu.android.mifang.adapter.TermAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.TermContract
import com.huotu.android.mifang.mvp.presenter.TermPresenter
import com.huotu.android.mifang.widget.OperateDialog
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_my_term_detail.*
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_header_2.*
import java.util.logging.Filter


class MyTermDetailActivity : BaseActivity<TermContract.Presenter>()
        ,TermContract.View
        , View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemChildClickListener
        , OperateDialog.OnOperateListener<KeyValue> {
    var type = 1
    var filterData = ArrayList<FilterEntry>()
    var filterAdapter :FilterAdapter?=null
    var termAdapter:TermAdapter?=null
    var searchDayType = -1
    var buyNum = -1
    var levelId = -1
    var relation = -1
    var bindMobile = -1
    var activate = -1
    var activateHour = 24
    var orderByType = 1
    var pageIndex = 0
    var iPresenter=TermPresenter(this)
    var isShowProgress = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_term_detail)

        if(intent.hasExtra(Constants.INTENT_TERM_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_TERM_TYPE , 1)
        }

        if(type ==1){
            header_title.text="团队详情"
            header_right_text.visibility=View.GONE
            searchDayType = -1
        }else{
            header_title.text="团队新增"
            header_right_text.visibility=View.VISIBLE
            header_right_text.setOnClickListener(this)
            searchDayType = 0
            header_right_text.text = "今日"
        }

        header_left_image.setOnClickListener(this)
        header_right_image.visibility= View.GONE
        mytermdetail_filter.setOnClickListener(this)
        myterm_detail_ok.setOnClickListener(this)
        myterm_detail_reset.setOnClickListener(this)

        myterm_detail_lay_time.setOnClickListener(this)
        myterm_detail_lay_fans.setOnClickListener(this)
        myterm_detail_lay_score.setOnClickListener(this)


        //var draw = ContextCompat.getDrawable(this , R.mipmap.arrow_down)
        //draw!!.setBounds(0,0,38,23)
        myterm_detail_time.setTextColor( ContextCompat.getColor( this , R.color.textcolor2))
        myterm_detail_score.setTextColor( ContextCompat.getColor( this , R.color.black))
        myterm_detail_fans.setTextColor( ContextCompat.getColor( this , R.color.black))
        //myterm_detail_time.setCompoundDrawables(null,null,draw , null)
        //myterm_detail_score.setCompoundDrawables(null,null,null,null)
        //myterm_detail_fans.setCompoundDrawables(null,null,null,null)

        var gridLayoutManager = GridLayoutManager(this , 2)
        myterm_detail_lay_filter_recyclerview.layoutManager = gridLayoutManager


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


        filterAdapter!!.onItemChildClickListener =this
        myterm_detail_lay_filter_recyclerview.adapter= filterAdapter

        var data = ArrayList<TermItemBean>()
        termAdapter = TermAdapter(data)
        termAdapter!!.setOnLoadMoreListener(this, myterm_detail_recyclerview)
        myterm_detail_recyclerview.layoutManager = LinearLayoutManager(this)
        myterm_detail_recyclerview.adapter = termAdapter
        myterm_detail_recyclerview.addItemDecoration(RecyclerViewDivider(this, ContextCompat.getColor(this , R.color.line_color2), 10f))

        myterm_detail_refreshview.setOnRefreshListener(this)

        iPresenter.getLevelList()

        getData()

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
                filter()
            }
            R.id.myterm_detail_ok->{
                setFilterStatus()
            }
            R.id.myterm_detail_reset->{
                resetFilterStatus()
            }
            R.id.myterm_detail_lay_time->{
                orderByType=1
                //orderByType = if( orderByType==1) 0 else if(orderByType ==0) 1 else 1
                //var draw = if(orderByType==1) ContextCompat.getDrawable(this , R.mipmap.arrow_down) else ContextCompat.getDrawable(this , R.mipmap.arrow_up)
                //draw!!.setBounds
                myterm_detail_time.setTextColor(ContextCompat.getColor(this , R.color.textcolor2))
                myterm_detail_score.setTextColor(ContextCompat.getColor(this, R.color.black))
                myterm_detail_fans.setTextColor(ContextCompat.getColor(this,R.color.black))
                //myterm_detail_time.setCompoundDrawables(null,null, draw , null)
                //myterm_detail_score.setCompoundDrawables(null,null,null,null)
                //myterm_detail_fans.setCompoundDrawables(null,null,null,null)
                isShowProgress=true
                pageIndex=0
                getData()
            }
            R.id.myterm_detail_lay_score->{
                orderByType=2
                //orderByType = if(orderByType==2) 3 else if(orderByType== 3) 2 else 2
                //var draw = if(orderByType==2) ContextCompat.getDrawable(this , R.mipmap.arrow_down) else ContextCompat.getDrawable(this , R.mipmap.arrow_up)
                //draw!!.setBounds(0,0,38,23)
                //myterm_detail_time.setCompoundDrawables(null,null, null , null)
                //myterm_detail_score.setCompoundDrawables(null,null,draw , null)
                //myterm_detail_fans.setCompoundDrawables(null,null,null,null)
                myterm_detail_time.setTextColor(ContextCompat.getColor(this , R.color.black))
                myterm_detail_score.setTextColor(ContextCompat.getColor(this, R.color.textcolor2))
                myterm_detail_fans.setTextColor(ContextCompat.getColor(this,R.color.black))
                isShowProgress=true
                pageIndex=0
                getData()
            }
            R.id.myterm_detail_lay_fans->{
                orderByType=4
                //orderByType = if(orderByType==4) 5 else if(orderByType== 5) 4 else 4
                //var draw = if(orderByType==4) ContextCompat.getDrawable(this , R.mipmap.arrow_down) else ContextCompat.getDrawable(this , R.mipmap.arrow_up)
                //draw!!.setBounds(0,0,38,23)
                //myterm_detail_time.setCompoundDrawables(null,null, null , null)
                //myterm_detail_score.setCompoundDrawables(null,null,null , null)
                //myterm_detail_fans.setCompoundDrawables(null,null,draw,null)
                myterm_detail_time.setTextColor(ContextCompat.getColor(this , R.color.black))
                myterm_detail_score.setTextColor(ContextCompat.getColor(this, R.color.black))
                myterm_detail_fans.setTextColor(ContextCompat.getColor(this,R.color.textcolor2))
                isShowProgress=true
                pageIndex=0
                getData()
            }
        }
    }



    private fun setFilterStatus(){
        myterm_detail_root.closeDrawer(myterm_detail_lay_filter)

        relation = -1
        buyNum =-1
        levelId=-1
        bindMobile=-1
        activate=-1
        activateHour=0
        pageIndex = 0
        isShowProgress=true

        for( bean in filterData){
            if( !bean.selected) continue

            if(bean.filterBean.pid== 9000){
                relation = bean.filterBean.fid
            }else if(bean.filterBean.pid == 9001){
                buyNum = bean.filterBean.fid
            }else if(bean.filterBean.pid == 9002){
                levelId = bean.filterBean.fid
            }else if(bean.filterBean.pid==9008){
                bindMobile =bean.filterBean.fid
            }else if(bean.filterBean.pid==9004){
                if(bean.filterBean.fid==24){
                    activate = 1
                    activateHour =24
                }else{
                    activate=0
                    activateHour=0
                }
            }

        }

        getData()
    }

    private fun filter(){
        var kvs = ArrayList<KeyValue>()
        kvs.add( KeyValue(0,"今日"))
        kvs.add(KeyValue(1,"本月"))
        var dialog = OperateDialog<KeyValue>(this, this , kvs ,"选择查询时间")
        dialog.show()
    }

    override fun operate(keyValue: KeyValue) {
        searchDayType = keyValue.code
        header_right_text.text = keyValue.name
        pageIndex=0
        isShowProgress=true
        getData()
    }

    private fun getData(){
        iPresenter.getTermMemberList(searchDayType , buyNum , levelId , relation , bindMobile , activate , activateHour , orderByType , pageIndex+1 , Constants.PAGE_SIZE)

    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)

        if(isShowProgress) {
            myterm_detail_progress.visibility = View.VISIBLE
        }else{
            myterm_detail_progress.visibility = View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        isShowProgress= false
        myterm_detail_progress.visibility = View.GONE
        myterm_detail_refreshview.isRefreshing=false
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

    private fun resetFilterStatus(){

        for(bean in filterData){
            bean.selected=false
        }
        filterAdapter!!.notifyDataSetChanged()
    }

    override fun onRefresh() {
        pageIndex=0
        getData()
    }

    override fun onLoadMoreRequested() {
        getData()
    }

    override fun getTermMemberListCallback(apiResult: ApiResult<TermBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        if (apiResult.data == null || apiResult.data!!.TeamItem==null ) return

        if (  apiResult.data!!.TeamItem!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                termAdapter!!.loadMoreEnd(true)
            } else {
                termAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            termAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {
            termAdapter!!.setNewData(apiResult.data!!.TeamItem!!)
            termAdapter!!.disableLoadMoreIfNotFullPage(myterm_detail_recyclerview)
        } else {
            termAdapter!!.addData(apiResult.data!!.TeamItem!!)
        }
    }

    override fun getTermIndexCallback(apiResult: ApiResult<TermIndexBean>) {

    }

    override fun getLevelListCallback(apiResult: ApiResult<ArrayList<LevelBean>>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }


        filterData.clear()

        var bean = FilterBean(9000,"所属团队" , 0 )
        var item = FilterEntry(bean , 1)
        filterData.add(item)
        bean = FilterBean(0,"一级团队" ,  9000 )
        item = FilterEntry(bean , 2)
        filterData.add(item)
        bean = FilterBean(1,"二级团队",9000)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        bean = FilterBean(9001,"下单次数",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(0,"无订单",9001)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(1,"1次及以上",9001)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(3,"3次及以上",9001)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(5,"5次及以上",9001)
        item = FilterEntry(bean , 2)
        filterData.add(item)

//        bean = FilterBean(7,"次及以上",9001)
//        item = FilterEntry(bean , 3)
//        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0), 4)
        filterData.add(item )


        if(apiResult.data!=null) {

            bean = FilterBean(9002, "身份", 0)
            item = FilterEntry(bean, 1)
            filterData.add(item)

            for( k in apiResult.data!!){
                bean = FilterBean( k.levelId , k.levelName , 9002 )
                item = FilterEntry(bean, 2)
                filterData.add(item)
            }

            item = FilterEntry(FilterBean(0,"",0),4)
            filterData.add(item)
        }


        bean = FilterBean(9008,"手机",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(1,"已绑定",9008)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(0,"未绑定",9008)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item =FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        bean = FilterBean(9004,"登录",0)
        item = FilterEntry(bean , 1)
        filterData.add(item)

        bean = FilterBean(24,"24小时登录",9004)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        bean = FilterBean(0,"24小时未登录",9004)
        item = FilterEntry(bean , 2)
        filterData.add(item)

        item = FilterEntry(FilterBean(0,"",0),4)
        filterData.add(item)

        filterAdapter!!.notifyDataSetChanged()

    }
}
