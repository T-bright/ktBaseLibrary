package com.tbright.ktbaseproject.demo.ui.fragment

import com.tbright.ktbaselibrary.extension.response
import com.tbright.ktbaselibrary.mvp.BaseModel
import com.tbright.ktbaseproject.demo.net.api.ApiServices
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainFragmentModel : BaseModel() {

    suspend fun singlePoetry(): String? {
        return ApiServices.instance.singlePoetry().response()
    }

    fun parallelRequest(result: (result: String?) -> Unit) {
        mainScope.launch {
            var singlePoetry = async { ApiServices.instance.singlePoetry().response() }

            var musicList = async { ApiServices.instance.getMusicList().response() }

            var mResult = "${singlePoetry.await().toString()}------${musicList?.await()?.first()?.title}"

            result.invoke(mResult)
        }
    }
}