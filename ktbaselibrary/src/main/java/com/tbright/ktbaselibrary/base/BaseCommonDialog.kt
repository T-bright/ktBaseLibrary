package com.tbright.ktbaselibrary.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.tbright.ktbaselibrary.R

abstract class BaseCommonDialog constructor(context: Context, @LayoutRes var layoutId: Int, @StyleRes style: Int = R.style.CommonDialog) : AlertDialog(context, style) {

    var mContentView: View? = null

    private var mResults: ((mContentView: View, dialog: AlertDialog) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContentView = LayoutInflater.from(context).inflate(layoutId, null)
        setContentView(mContentView!!)
        setDialogWidth()
        onCreating(savedInstanceState)
        try {
            mResults?.invoke(mContentView!!, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun onCreating(savedInstanceState: Bundle?)

    open fun setDialogWidth() {
        window?.let {
            var params = it.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = params
        }
    }

    open fun show(result: ((mContentView: View, dialog: AlertDialog) -> Unit)? = null) {
        this.mResults = result
        this.show()
    }
}