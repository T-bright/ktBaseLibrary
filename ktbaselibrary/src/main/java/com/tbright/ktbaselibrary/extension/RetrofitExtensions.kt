package com.tbright.ktbaselibrary.extension

import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaselibrary.global.GlobalConfig
import kotlinx.coroutines.Deferred
import java.lang.NullPointerException

suspend fun <T> Deferred<BaseResponse<T>>.response(): T? {
    return GlobalConfig.httpConfigProxy?.parseResponseData(this)
}


fun <T> create(clazz: Class<T>) : T{
    if(GlobalConfig.httpConfigProxy == null){
        throw NullPointerException("please init GlobalConfig init")
    }else{
        return GlobalConfig.httpConfigProxy!!.create(clazz)
    }
}