package com.tbright.ktbaselibrary.mvp

import com.tbright.ktbaselibrary.global.GlobalConfig

class BaseModel : IModel {

    fun <T> create(clazz: Class<T>) {
        GlobalConfig.httpConfigProxy?.create(clazz)
    }

    override fun onDestroy() {

    }

}