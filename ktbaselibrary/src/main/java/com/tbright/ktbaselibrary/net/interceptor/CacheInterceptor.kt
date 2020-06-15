package com.tbright.ktbaselibrary.net.interceptor

import com.blankj.utilcode.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) : Response  {
        var request = chain.request()
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        var response = chain.proceed(request)
        if (NetworkUtils.isConnected()) {//有网络
            return executeCache(0,response) //将maxAge设置成0，表示有网络直接走网络
        } else {
            var maxAge = 60 * 60 * 24 //一天
            return executeCache(maxAge,response)
        }
    }

    private fun executeCache(maxAge: Int, response: Response): Response {
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=${maxAge}")
            .removeHeader("Pragma")
            .build();
    }
}