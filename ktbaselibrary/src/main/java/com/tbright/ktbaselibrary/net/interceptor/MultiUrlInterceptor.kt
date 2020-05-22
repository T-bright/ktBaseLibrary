package com.tbright.ktbaselibrary.net.interceptor

import com.tbright.ktbaselibrary.global.GlobalConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


private const val MULTI_URL_HEADER_KEY = "Multi-Url-Header"

const val MULTI_URL_HEADER = "${MULTI_URL_HEADER_KEY}: "

/**
 * 多baseUrl的拦截器，当项目中用到多域名的时候，可以参考使用
 *
 * 使用时需要在Retrofit的接口方法上添加 headers。这个headers是 httpConfigProxy?.baseUrls的key，通过key拿到需要替换的url。
 *
 * 之所以通过key来获取需要替换的url，因为release包和debug包的域名是不一样的，但是 httpConfigProxy?.baseUrls 的键是一样的。
 *
 */
class MultiUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val baseUrls = GlobalConfig.httpConfigProxy?.baseUrls

        return if (baseUrls != null && baseUrls.size > 1) {//不为空，而且长度>1,表示有多域名
            val multiUrlHeader = request.header(MULTI_URL_HEADER_KEY)
            if (multiUrlHeader.isNullOrEmpty()) {
                chain.proceed(request)
            } else {
                val builder = request.newBuilder()

                var oldUrl = request.url()
                builder.removeHeader(MULTI_URL_HEADER_KEY)

                var url = baseUrls[multiUrlHeader]
                url = url?.substring(0, url!!.length - 1) + oldUrl.url().file

                val httpUrl = HttpUrl.parse(url)
                builder.url(httpUrl)
                chain.proceed(builder.build())
            }
        } else {
            chain.proceed(request)
        }
    }
}