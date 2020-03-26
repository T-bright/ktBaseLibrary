package com.tbright.ktbaselibrary.extension

import android.app.Activity
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.tbright.ktbaselibrary.widget.LoadingDialogFragment

fun FragmentActivity.addFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(id, fragment).commit()
}

fun FragmentActivity.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(id, fragment).commit()
}

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? =
    supportFragmentManager.findFragmentByTag(tag)

fun FragmentActivity.findFragmentById(@IdRes id: Int): Fragment? =
    supportFragmentManager.findFragmentById(id)

fun FragmentActivity.showFragment(@IdRes id: Int, currentFragment: Fragment, needShowFragment: Fragment) {
    var transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    transaction.hide(currentFragment)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(id, needShowFragment).show(needShowFragment).commit()
    }
}

fun FragmentActivity.showFragment(@IdRes id: Int, needShowFragment: Fragment) {
    var transaction = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    if (needShowFragment.isAdded) {
        transaction.show(needShowFragment).commit()
    } else {
        transaction.add(id, needShowFragment).show(needShowFragment).commit()
    }
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