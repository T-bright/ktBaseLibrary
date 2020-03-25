package com.tbright.ktbaseproject.demo

import android.app.Application
import com.tbright.ktbaselibrary.global.GlobalConfig

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalConfig.init(this)
    }
}