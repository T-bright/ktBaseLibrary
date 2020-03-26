package com.tbright.ktbaseproject.demo.net.api

import com.tbright.ktbaseproject.demo.bean.Gank
import com.tbright.ktbaseproject.demo.customconfig.GANK_URL
import com.tbright.ktbaseproject.demo.net.BaseGankResponse
import com.tbright.ktbaseproject.demo.net.interceptor.MULTI_URL_HEADER
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface GankServices {

    @GET("api/v2/categories/Girl")
    @Headers(MULTI_URL_HEADER + GANK_URL) //多域名header设置
    fun getGanHuo(): Deferred<BaseGankResponse<List<Gank>>>
}