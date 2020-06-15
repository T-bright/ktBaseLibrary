package com.tbright.ktbaseproject.demo.net.api

import com.tbright.ktbaselibrary.extension.create
import com.tbright.ktbaselibrary.net.interceptor.MULTI_URL_HEADER
import com.tbright.ktbaseproject.demo.bean.Gank
import com.tbright.ktbaseproject.demo.customconfig.GANK_URL
import com.tbright.ktbaseproject.demo.net.response.BaseGankResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface GankServices {

    companion object {
        val instance: GankServices by lazy {
            create(GankServices::class.java)
        }
    }

    @GET("api/v2/categories/Girl")
    @Headers(MULTI_URL_HEADER + GANK_URL) //多域名header设置
    fun getGanHuo(): Deferred<BaseGankResponse<List<Gank>>>
}