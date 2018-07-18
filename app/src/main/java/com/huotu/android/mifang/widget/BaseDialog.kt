package com.huotu.android.mifang.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.KeyEvent

import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout

import com.huotu.android.mifang.util.DensityUtils
import kotlin.math.max


/**
 * Created by jinxiangdong on 2017/12/24.
 */

open class BaseDialog(internal var context: Context) : DialogInterface.OnKeyListener, DialogInterface.OnDismissListener {

    var dialog: Dialog?=null
    protected var screenWidthPixels: Int = 0
    protected var screenHeightPixels: Int = 0
    private var contentLayout: FrameLayout? = null

    init {
        //DisplayMetrics metrics = DensityUtils.INSTANCE. ScreenUtils.displayMetrics(context);
        screenWidthPixels = DensityUtils.getScreenWidth(context)// metrics.widthPixels;
        screenHeightPixels = DensityUtils.getScreenHeight(context)// metrics.heightPixels;

        initDialog()

    }


    private fun initDialog() {
        //        LayoutInflater inflater = LayoutInflater.from(context);
        //        View view = inflater.inflate(R.layout.layout_operate_dialog , null);
        //        view.setFocusable(true);
        //        view.setFocusableInTouchMode(true);
        //        this.contentLayout = view;
        contentLayout = FrameLayout(context)
        contentLayout!!.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        contentLayout!!.isFocusable = true
        contentLayout!!.isFocusableInTouchMode = true
        dialog = Dialog(context)
        dialog!!.setCanceledOnTouchOutside(true)//触摸屏幕取消窗体
        dialog!!.setCancelable(true)//按返回键取消窗体
        dialog!!.setOnKeyListener(this)
        dialog!!.setOnDismissListener(this)
        val window = dialog!!.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //AndroidRuntimeException: requestFeature() must be called before adding content
            window.requestFeature(Window.FEATURE_NO_TITLE)
            window.setContentView(contentLayout)
        }

        setSize(screenWidthPixels, WRAP_CONTENT)


        //        RecyclerView recyclerView=view.findViewById(R.id.operate_dialog_list);
        //        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //        operateAdapter=new OperateDialog.OperateAdapter(list);
        //        operateAdapter.setOnItemClickListener(this);
        //        recyclerView.setAdapter(operateAdapter);
    }


    open fun show() {
        dialog!!.show()
    }

    protected fun addContentView(view: View) {
        this.contentLayout!!.addView(view)
    }


    /**
     * 设置弹窗的宽和高
     *
     * @param width  宽
     * @param height 高
     */
    fun setSize(width: Int, height: Int ) {
        var width = width
        var height = height

        if (width == MATCH_PARENT) {
            //360奇酷等手机对话框MATCH_PARENT时两边还会有边距，故强制填充屏幕宽
            width = screenWidthPixels
        }
        if (width == 0 && height == 0) {
            width = screenWidthPixels
            height = WRAP_CONTENT
        } else if (width == 0) {
            width = screenWidthPixels
        } else if (height == 0) {
            height = WRAP_CONTENT
        }


        //LogUtils.verbose(this, String.format("will set popup width/height to: %s/%s", width, height));
        var params: ViewGroup.LayoutParams? = contentLayout!!.layoutParams
        if (params == null) {
            params = ViewGroup.LayoutParams(width, height)

        } else {
            params.width = width
            params.height = height
        }
        contentLayout!!.layoutParams = params

    }


    override fun onDismiss(dialog: DialogInterface) {
        dismiss()
    }

    override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPress()
        }
        return false
    }

    fun onBackPress(): Boolean {
        dismiss()
        return false
    }

    fun dismiss() {
        dismissImmediately()
    }

    protected fun dismissImmediately() {
        dialog!!.dismiss()
        //Log.d(this, "popup dismiss");
    }

    companion object {
        val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
        val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}
