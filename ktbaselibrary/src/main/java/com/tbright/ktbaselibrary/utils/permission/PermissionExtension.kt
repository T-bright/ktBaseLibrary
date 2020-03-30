package com.tbright.ktbaselibrary.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


private const val Fragment_Tag = "com.tbright.ktbaselibrary.utils.permission.PermissionFragment"

fun FragmentActivity.checkPermissions(vararg permissions: String, callback: (isGranted: Boolean) -> Unit) {
    if (!needRequestPermission(callback)) return

    if (hasSelfPermission(this, arrayOf(*permissions))) {
        callback.invoke(true)
    } else {
        val permissionFragment = getPermissionFragment(this.supportFragmentManager)
        permissionFragment?.setPermissions(permissions, callback)
    }
}


fun Fragment.checkPermissions(vararg permissions: String, callback: (isGranted: Boolean) -> Unit) {
    if (!needRequestPermission(callback)) return

    if (hasSelfPermission(this.context, arrayOf(*permissions))) {
        callback.invoke(true)
    } else {
        val permissionFragment = getPermissionFragment(this.childFragmentManager)
        permissionFragment?.setPermissions(permissions, callback)
    }
}


private fun needRequestPermission(callback: (isGranted: Boolean) -> Unit): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        callback.invoke(true)
        return false
    }
    return true
}


private fun getPermissionFragment(fragmentManager: FragmentManager): PermissionFragment? {
    var permissionFragment = fragmentManager.findFragmentByTag(Fragment_Tag) as PermissionFragment?
    if (permissionFragment == null) {
        permissionFragment = PermissionFragment.newInstance()
        fragmentManager.beginTransaction().add(permissionFragment, Fragment_Tag).commit()
        fragmentManager.executePendingTransactions()
    }
    return permissionFragment
}

/**
 * 是否有权限
 * @return
 */
private fun hasSelfPermission(activity: Context?, permissions: Array<String>): Boolean {
    activity?.let {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
    }
    return true
}

