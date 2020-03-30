package com.tbright.ktbaseproject.demo.extensions

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.tbright.ktbaselibrary.utils.AppUtils
import com.tbright.ktbaseproject.demo.R

//重新登录
fun Activity.reLogin() {

}

private val processView = LayoutInflater.from(AppUtils.mApplication).inflate(R.layout.loading_dialog_view, null, false)

fun Activity.showLoadingDialog() {
    if (this is AppCompatActivity) {
        var decorView = this.window.decorView as FrameLayout
        var lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        lp.gravity = Gravity.CENTER
        if (decorView.indexOfChild(processView) != -1) {
            decorView.removeView(processView)
        }
        decorView.addView(processView, lp)
    }
}

fun Activity.hideLoadingDialog() {
    if (this is AppCompatActivity) {
        var decorView = this.window.decorView as FrameLayout
        if (decorView.indexOfChild(processView) != -1) {
            decorView.removeView(processView)
        }
    }
}