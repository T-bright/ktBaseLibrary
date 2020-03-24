package com.tbright.ktbaselibrary.utils

import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtils {

    private var toast: Toast? = null

    fun showShort(text: String) {
        show(text, Toast.LENGTH_SHORT)
    }

    fun showShort(@StringRes ids: Int) {
        show(ids, Toast.LENGTH_SHORT)
    }

    fun showLong(text: String) {
        show(text, Toast.LENGTH_LONG)
    }

    fun showLong(@StringRes ids: Int) {
        show(ids, Toast.LENGTH_LONG)
    }

    private fun show(text: String, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText(AppUtils.mApplication?.applicationContext, text, duration)
        }
        toast?.show()
    }

    private fun show(@StringRes ids: Int, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText(AppUtils.mApplication?.applicationContext, ids, duration)
        }
        toast?.show()
    }
}