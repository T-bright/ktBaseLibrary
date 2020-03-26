package com.tbright.ktbaselibrary.proxy

import com.tbright.ktbaselibrary.base.BaseResponse
import kotlinx.coroutines.Deferred


//Http的BaseUrl和返回的数据解析的配置
abstract class HttpConfigProxy {

    //设置baseUrl
    abstract var baseUrl: String

    //设置baseUrls
    abstract var baseUrls: Map<String, String>

    //解析数据
    abstract suspend fun <T> parseResponseData(responseData: Deferred<BaseResponse<T>>): T?

    //初始化http
    abstract fun initRetrofit()

    abstract fun <T> create(clazz: Class<T>) : T
}

