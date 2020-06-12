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
import com.tbright.ktbaselibrary.dialog.CommonDialog

abstract class BaseCommonDialog constructor(context: Context, @LayoutRes var layoutId: Int, @StyleRes style: Int = R.style.CommonDialog) : AlertDialog(context, style) {

    var mContentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContentView = LayoutInflater.from(context).inflate(layoutId, null)
        setContentView(mContentView!!)
        setDialogWidth()
        onCreating(savedInstanceState)
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

}