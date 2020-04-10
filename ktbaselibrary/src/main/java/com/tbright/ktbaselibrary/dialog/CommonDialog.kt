package com.tbright.ktbaselibrary.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.tbright.ktbaselibrary.R

class CommonDialog(context: Context, @LayoutRes var layoutId: Int, @StyleRes style: Int = R.style.CommonDialog) : AlertDialog(context, style) {

    var mContentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContentView = LayoutInflater.from(context).inflate(layoutId, null)
        setContentView(mContentView!!)
    }

    inline fun show(result: (mContentView: View, dialog: AlertDialog) -> Unit) {
        this.show()

    }

    inline fun show(result: CommonDialog.() -> Unit) {
        this.show()
        this.result()
    }

}