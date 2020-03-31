package com.tbright.ktbaseproject.demo.net.api

import com.tbright.ktbaseproject.demo.bean.MusicBean
import com.tbright.ktbaseproject.demo.net.response.BaseMagicResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiServices {


    @GET("singlePoetry/")
    fun singlePoetry(): Deferred<BaseMagicResponse<String>>

    @GET("musicBroadcasting/")
    fun getMusicList(): Deferred<BaseMagicResponse<List<MusicBean>?>>

}