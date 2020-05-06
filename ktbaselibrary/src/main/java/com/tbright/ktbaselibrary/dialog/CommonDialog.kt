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

    /**
     * 是否使用的是 dialog_common 布局:
     * 如果是：则最好使用 CommonDialog.showCommon(result: CommonDialog.() -> Unit) 方法，因为 kotlin-android-extensions 目前不支持 获取library中viewId。
     * 如果不是：使用CommonDialog.show(result: (mContentView: View, dialog: AlertDialog) -> Unit)，此时可以直接通过 mContentView.ViewId来处理相应的逻辑
     *
     * @see CommonDialog.showCommon(result: CommonDialog.() -> Unit)
     * @see CommonDialog.show(result: (mContentView: View, dialog: AlertDialog) -> Unit)
     *
     */
    private var isUseDialogCommon = layoutId == R.layout.dialog_common


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
     * 一般来说，每一个app都有一套设计标准，如：dialog距离屏幕两边有多少距离，这个都是全项目通用，直接在style中设置即可。
     * 但是如果不一样，可以参考如下写法：
     *
     *    CommonDialog(this).showCommon {
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
        window?.let {
            var params = it.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = params
        }
    }

    /**
     * 因为 kotlin-android-extensions 目前不支持 获取library中viewId。所以，dialog_common 布局的viewId 在 外部是获取不到的，会报一个NullPointerException。
     * 所以，如果使用的是 dialog_common 布局，需要使用 showCommon 方法来显示 dialog
     */
    override fun show(result: ((mContentView: View, dialog: AlertDialog) -> Unit)?) {
        if (isUseDialogCommon) throw NullPointerException("please use function showCommon()")
        super.show(result)
    }

    fun showCommon(result: CommonDialog.() -> Unit) {
        if (!isUseDialogCommon) throw NullPointerException("please use function show()")
        this.show()
        this.result()
    }

    var title: String = ""
        set(value) {
            field = value
            try {
                tvTitle.text = value
                tvTitle.isGone = value.isEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    var message: String = ""
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
    var howMuchButton = 2
        set(value) {
            field = value
            try {
                tvIKnow.isGone = value == 2
                haveTwoButton.isGone = value == 1
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun positiveClickListener(isDismissDialog: Boolean = true, positive: () -> Unit) {
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

    fun negativeClickListener(negative: () -> Unit) {
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