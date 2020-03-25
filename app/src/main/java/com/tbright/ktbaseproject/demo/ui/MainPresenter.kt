package com.tbright.ktbaseproject.demo.ui

import com.tbright.ktbaselibrary.extension.create
import com.tbright.ktbaselibrary.extension.response
import com.tbright.ktbaselibrary.mvp.BaseModel
import com.tbright.ktbaseproject.demo.net.ApiServices
import com.tbright.ktbaseproject.demo.GlobalConstants
import kotlinx.coroutines.launch

class MainPresenter : MainContract.MainPresenter() {

    override fun onAttachModel(): BaseModel? {
        return null
    }

    override fun login(username: String, password: String) {
        mView?.showLoading()
        mainScope.launch {
            var token = create(ApiServices::class.java).loginByPassword(username,password).response()
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

}