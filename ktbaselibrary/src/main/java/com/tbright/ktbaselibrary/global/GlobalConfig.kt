package com.tbright.ktbaselibrary.global

import android.app.Application
import android.graphics.Color
import com.tbright.ktbaselibrary.BuildConfig
import com.tbright.ktbaselibrary.proxy.HttpConfigProxy
import com.tbright.ktbaselibrary.proxy.ShowUIProxy
import com.tbright.ktbaselibrary.utils.AppUtils

object GlobalConfig {
    //配置Http相关的类
    var httpConfigProxy: HttpConfigProxy? = null

    //配置UI显示提示弹框
    var showUIProxy: ShowUIProxy? = null

    //是否是debug包
    var isDebug = BuildConfig.DEBUG

    //app的statusBar颜色
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