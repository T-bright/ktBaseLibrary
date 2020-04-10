package com.tbright.ktbaselibrary.global

import android.app.Application
import com.tbright.ktbaselibrary.BuildConfig
import com.tbright.ktbaselibrary.proxy.HttpConfigProxy
import com.tbright.ktbaselibrary.proxy.ShowUIProxy
import com.tbright.ktbaselibrary.utils.AppUtils

object GlobalConfig {
    var httpConfigProxy: HttpConfigProxy? = null
    var showUIProxy: ShowUIProxy? = null
    var isDebug = BuildConfig.DEBUG
    fun init(application: Application, httpConfigProxy: HttpConfigProxy, showUIProxy: ShowUIProxy, callback: Application.ActivityLifecycleCallbacks? = null) {
        this.httpConfigProxy = httpConfigProxy
        this.showUIProxy = showUIProxy
        AppUtils.init(application, callback)
        this.httpConfigProxy?.initRetrofit()
    }
}