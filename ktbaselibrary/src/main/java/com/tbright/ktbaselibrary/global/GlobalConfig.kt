package com.tbright.ktbaselibrary.global

import android.app.Application
import android.app.DownloadManager
import com.tbright.ktbaselibrary.proxy.HttpConfigProxy
import com.tbright.ktbaselibrary.proxy.HttpConfigProxyDefault
import com.tbright.ktbaselibrary.proxy.ShowUIProxy
import com.tbright.ktbaselibrary.proxy.ShowUIProxyDefault
import com.tbright.ktbaselibrary.utils.AppUtils

object GlobalConfig {
    var httpConfigProxy: HttpConfigProxy? = null
    var showUIProxy : ShowUIProxy? = null
    fun init(application: Application,
             httpConfigProxy: HttpConfigProxy? = null,
             showUIProxy : ShowUIProxy? = null) {
        if (httpConfigProxy == null) this.httpConfigProxy = HttpConfigProxyDefault() else this.httpConfigProxy = httpConfigProxy
        if (showUIProxy == null) this.showUIProxy = ShowUIProxyDefault() else this.showUIProxy = showUIProxy
        AppUtils.init(application)
    }

}