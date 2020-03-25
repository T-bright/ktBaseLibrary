package com.tbright.ktbaseproject.demo

import com.tbright.ktbaselibrary.base.BaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("auth/login/v1/app")
    fun loginByPassword(@Field("loginName") loginName: String,
                        @Field("password") password: String): Deferred<BaseResponse<Token>>

}