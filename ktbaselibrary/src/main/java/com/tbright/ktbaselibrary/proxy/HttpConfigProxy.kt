package com.tbright.ktbaselibrary.proxy

import com.tbright.ktbaselibrary.base.BaseResponse
import kotlinx.coroutines.Deferred

//Http的BaseUrl和返回的数据解析的配置
interface HttpConfigProxy {

    //设置baseUrl
    fun getBaseUrl(): Map<String, String>

    //解析数据
    suspend fun <T> parseResponseData(responseData: Deferred<BaseResponse<T>>): T?

}