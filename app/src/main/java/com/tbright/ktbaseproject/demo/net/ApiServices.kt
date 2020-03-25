package com.tbright.ktbaseproject.demo.net

import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaseproject.demo.bean.Token
import com.tbright.ktbaseproject.demo.bean.User
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("auth/login/v1/app/")
    fun loginByPassword(@Field("loginName") loginName: String,
                        @Field("password") password: String): Deferred<BaseResponse<Token>>

    //获取用户信息
    @GET("main/magic/user/v1/")
    fun getUserInfo(@Query("userId") userId: String): Deferred<BaseResponse<User>>
}