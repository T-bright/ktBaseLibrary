package com.tbright.ktbaseproject.demo.net.interceptor

import com.tbright.ktbaselibrary.global.GlobalConfig
import com.tbright.ktbaseproject.demo.customconfig.BASE_URL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

const val MULTI_URL_HEADER = "Multi-Url-Header: "
private const val MULTI_URL_HEADER_KEY = "Multi-Url-Header"

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
                if (multiUrlHeader != BASE_URL) {
                    url = url.replace("rest/", "")
                }
                val httpUrl = HttpUrl.parse(url)
                builder.url(httpUrl)
                chain.proceed(builder.build())
            }
        } else {
            chain.proceed(request)
        }
    }
}