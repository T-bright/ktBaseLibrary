package com.tbright.ktbaseproject.demo

import android.app.Application
import com.tbright.ktbaselibrary.global.GlobalConfig
import com.tbright.ktbaseproject.demo.customconfig.HttpConfig
import com.tbright.ktbaseproject.demo.customconfig.ShowUIConfig

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalConfig.init(this,httpConfigProxy = HttpConfig(),showUIProxy = ShowUIConfig())
    }
}