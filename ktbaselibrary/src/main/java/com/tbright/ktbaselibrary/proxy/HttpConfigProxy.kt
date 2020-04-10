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

    /**
     * 解析服务端返回的数据。
     *
     * 正常情况下，后端返回的异常信息都是统一处理，给个提示就完事了。
     * 但是也有部分需求可能不是给个提示，而是需要处理相应的业务逻辑，这个时候可以使用这个方法，拿到整个的BaseResponse<T>信息，去做特殊处理。
     *
     * 这里这么设计的原因：
     * 1是因为这种需要特殊处理的后台错误信息场景非常少，这种逻辑基本很少遇到；
     * 2是如果不这么设计，采用回调的方式，在协程里面就会多一层回调，使用这种方式，可以有效的避免多一层回调嵌套，让代码更加的清晰清爽。
     */
    abstract suspend fun <T> parseResponseWrapperData(responseData: Deferred<BaseResponse<T>>,vararg needDisposeError:Any): BaseResponse<T>?

    /**初始化Retrofit*/
    abstract fun initRetrofit()

    abstract fun <T> create(clazz: Class<T>) : T
}

