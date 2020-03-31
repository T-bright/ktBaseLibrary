package com.tbright.ktbaseproject.demo.customconfig

import android.text.TextUtils
import com.tbright.ktbaseproject.demo.GlobalConstants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorization = ""
        var request = chain.request()
        val newBuilder = request.newBuilder()
        if (!TextUtils.isEmpty(authorization)) {
            newBuilder.header("Authorization", authorization!!)
        }
        val newRequest = newBuilder.method(request.method(), request.body())
            .build()
        return chain.proceed(newRequest)
    }
}