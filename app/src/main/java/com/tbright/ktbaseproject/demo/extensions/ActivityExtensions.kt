package com.tbright.ktbaseproject.demo.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.tbright.ktbaselibrary.widget.LoadingDialogFragment

//重新登录
fun Activity.reLogin() {

}


fun Activity.showLoadingDialog() {
    if (this is AppCompatActivity) {
        var dialog = this.supportFragmentManager.findFragmentByTag(LoadingDialogFragment::class.java.name)
        if (dialog?.isAdded == true) {
            return
        }
        if (dialog == null) {
            dialog = LoadingDialogFragment()
            dialog.show(supportFragmentManager, LoadingDialogFragment::class.java.name)
        } else {
            if (dialog is DialogFragment) {
                dialog.show(supportFragmentManager, LoadingDialogFragment::class.java.name)
            }
        }
    }
}

fun Activity.hideLoadingDialog() {
    if (this is AppCompatActivity) {
        val dialog = this.supportFragmentManager.findFragmentByTag(LoadingDialogFragment::class.java.name)
        dialog?.let {
            if (dialog is DialogFragment) {
                dialog.dismiss()
            }
        }
    }
}