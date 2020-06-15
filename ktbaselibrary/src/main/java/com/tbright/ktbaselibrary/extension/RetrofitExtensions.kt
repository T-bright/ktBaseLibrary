package com.tbright.ktbaselibrary.extension

import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaselibrary.global.GlobalConfig
import kotlinx.coroutines.Deferred
import java.lang.NullPointerException

//返回网络请求的数据
suspend fun <T> Deferred<BaseResponse<T>>.response(): T? {
    return GlobalConfig.httpConfigProxy?.parseResponseData(this)
}

//返回网络请求包装类
suspend fun <T> Deferred<BaseResponse<T>>.responseWrapper(vararg needDisposeError:Any): BaseResponse<T>? {
    return GlobalConfig.httpConfigProxy?.parseResponseWrapperData(this,needDisposeError)
}


fun <T> create(clazz: Class<T>) : T{
    if(GlobalConfig.httpConfigProxy == null){
        throw NullPointerException("please init GlobalConfig init")
    }else{
        return GlobalConfig.httpConfigProxy!!.create(clazz)
    }
}