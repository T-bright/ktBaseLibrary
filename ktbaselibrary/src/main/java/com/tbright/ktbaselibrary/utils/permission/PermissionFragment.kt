package com.tbright.ktbaselibrary.utils.permission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment

class PermissionFragment : Fragment() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 299
        fun newInstance(): PermissionFragment = PermissionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var callback: (isGranted: Boolean) -> Unit

    fun setPermissions(permissions: Array<out String>, callback: (isGranted: Boolean) -> Unit) {
        this.callback = callback
        requestPermissions(permissions, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            var isGranted = true
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                    return@forEach
                }
            }
            callback.invoke(isGranted)
        }
    }

}