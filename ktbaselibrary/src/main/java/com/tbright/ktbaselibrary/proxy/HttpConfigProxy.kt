package com.tbright.ktbaselibrary.proxy

import com.tbright.ktbaselibrary.base.BaseResponse
import kotlinx.coroutines.Deferred


/**
 * Http的BaseUrl和返回的数据解析的配置
 */
abstract class HttpConfigProxy {

    /**
     * 设置baseUrl。单域名设置。
     * 如果项目中不存在多域名的情况下，可以直接重写baseUrl，而不用设置 baseUrls。
     */
    abstract var baseUrl: String

    /**
     * 设置baseUrls。多域名设置。
     *
     * 如果项目中存在多域名的情况下，baseUrls，而不用设置 baseUrl。
     *
     * 多域名的使用可以参考 MultiUrlInterceptor
     *
     * @see com.tbright.ktbaselibrary.net.interceptor.MultiUrlInterceptor
     *
     */
    abstract var baseUrls: Map<String, String>

    /**解析服务端返回的数据。这里可以统一处理后端返回的异常信息*/
    abstract suspend fun <T> parseResponseData(responseData: Deferred<BaseResponse<T>>): T?

    /**初始化Retrofit*/
    abstract fun initRetrofit()

    abstract fun <T> create(clazz: Class<T>) : T
}

