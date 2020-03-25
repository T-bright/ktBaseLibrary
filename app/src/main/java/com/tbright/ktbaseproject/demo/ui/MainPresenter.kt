package com.tbright.ktbaseproject.demo.ui

import android.os.SystemClock
import android.util.Log
import com.tbright.ktbaselibrary.extension.create
import com.tbright.ktbaselibrary.extension.response
import com.tbright.ktbaselibrary.mvp.BaseModel
import com.tbright.ktbaseproject.demo.net.ApiServices
import com.tbright.ktbaseproject.demo.GlobalConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPresenter : MainContract.MainPresenter() {

    override fun onAttachModel(): BaseModel? {
        return null
    }

    override fun login(username: String, password: String) {
        mView?.showLoading()
        mainScope.launch {
            var token = create(ApiServices::class.java).loginByPassword(username, password).response()
            token?.let {
                GlobalConstants.token = it
                mView?.loginResult("${it.access_token!!}")
            }
            mView?.hideLoading()
        }
    }

    override fun getUserInfo(userId: String) {
        mView?.showLoading()
        mainScope.launch {
            var user = create(ApiServices::class.java).getUserInfo(userId).response()
            user?.let {
                mView?.loginResult("${it.userName}")
                mView?.hideLoading()
            }

        }
    }

    //并行请求。当一个页面有多个请求的时候，可以并行请求
    override fun parallelRequest(username: String, password: String) {
        mView?.showLoading()
        mainScope.launch {
            //先登录，拿到token
            var token = create(ApiServices::class.java).loginByPassword(username, password).response()
            GlobalConstants.token = token

            //使用  async 并行网络 请求
            var user = async { create(ApiServices::class.java).getUserInfo(GlobalConstants.token?.userId ?: "").response() }

            var list = async { create(ApiServices::class.java).getDicList().response() }

            var result = "${list.await().toString()}------${user?.await()?.userName}"

            mView?.loginResult(result)

            mView?.hideLoading()
        }
    }

}