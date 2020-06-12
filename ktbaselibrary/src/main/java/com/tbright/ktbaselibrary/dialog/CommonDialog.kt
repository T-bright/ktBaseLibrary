package com.tbright.ktbaselibrary.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.tbright.ktbaselibrary.R
import com.tbright.ktbaselibrary.base.BaseCommonDialog
import com.tbright.ktbaselibrary.extension.isGone
import com.tbright.ktbaselibrary.extension.isVisiable
import kotlinx.android.synthetic.main.dialog_common.*
import java.lang.NullPointerException

open class CommonDialog(context: Context, @LayoutRes var layoutIds: Int = R.layout.dialog_common, @StyleRes style: Int = R.style.CommonDialog) :
    BaseCommonDialog(context, layoutIds, style) {

    override fun onCreating(savedInstanceState: Bundle?) {
        try {
            tvCancel?.setOnClickListener { dismiss() }
            howMuchButton = 2//默认两个按钮
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置dialog宽度,这里设置是全屏。至于说，dialog距离屏幕两边有多少距离，这个在style中设置即可。
     * 一般来说，每一个app都有一套设计标准，如：通用的提示dialog距离屏幕两边有多少距离，这个都是全项目通用，直接在style中设置即可。
     * 但是如果不一样，可以参考如下写法：
     *
     *    CommonDialog(this).show {
     *          window?.let {
     *                  var params = it.attributes
     *                  it.decorView?.setPadding(SizeUtils.dp2px(55f),0,SizeUtils.dp2px(55f),0)
     *                  params.width = WindowManager.LayoutParams.MATCH_PARENT
     *                  params.height = WindowManager.LayoutParams.WRAP_CONTENT
     *                  it.attributes = params
     *          }
     *    }
     *
     */
    override fun setDialogWidth() {
        super.setDialogWidth()
    }

    open fun show(result: CommonDialog.() -> Unit){
        this.show()
        this.result()
    }

    //dialog标题
    open var title: String = ""
        set(value) {
            field = value
            try {
                tvTitle.text = value
                tvTitle.isGone = value.isEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    //dialog标题
    open var message: String = ""
        set(value) {
            field = value
            try {
                tvMessage.text = value
                tvMessage.isVisiable = value.isNotEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    //有几个按钮
    open var howMuchButton = 2
        set(value) {
            field = value
            try {
                tvIKnow.isGone = value == 2
                haveTwoButton.isGone = value == 1
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    //确定按钮点击
    open fun positiveClickListener(isDismissDialog: Boolean = true, positive: () -> Unit) {
        try {
            tvConfirm.setOnClickListener {
                positive.invoke()
                if (isDismissDialog) dismiss()
            }
            tvIKnow.setOnClickListener {
                positive.invoke()
                if (isDismissDialog) dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //取消按钮点击
    open fun negativeClickListener(negative: () -> Unit) {
        try {
            tvCancel.setOnClickListener {
                negative.invoke()
                dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}