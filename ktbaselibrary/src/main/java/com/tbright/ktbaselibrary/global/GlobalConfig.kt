package com.tbright.ktbaselibrary.global

import android.app.Application
import android.graphics.Color
import com.tbright.ktbaselibrary.BuildConfig
import com.tbright.ktbaselibrary.proxy.HttpConfigProxy
import com.tbright.ktbaselibrary.proxy.ShowUIProxy
import com.tbright.ktbaselibrary.utils.AppUtils

object GlobalConfig {
    var httpConfigProxy: HttpConfigProxy? = null
    var showUIProxy: ShowUIProxy? = null
    var isDebug = BuildConfig.DEBUG
    var statusBarColor = Color.WHITE
    fun init(application: Application,
             httpConfigProxy: HttpConfigProxy,
             showUIProxy: ShowUIProxy,
             statusBarColor : Int = Color.WHITE,
             callback: Application.ActivityLifecycleCallbacks? = null) {
        this.httpConfigProxy = httpConfigProxy
        this.showUIProxy = showUIProxy
        AppUtils.init(application, callback)
        this.httpConfigProxy?.initRetrofit()
        this.statusBarColor = statusBarColor
    }
}