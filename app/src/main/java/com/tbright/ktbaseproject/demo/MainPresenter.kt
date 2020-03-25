package com.tbright.ktbaseproject.demo

import com.tbright.ktbaselibrary.extension.create
import com.tbright.ktbaselibrary.extension.response
import com.tbright.ktbaselibrary.mvp.BaseModel
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
                mView?.loginResult("${it.access_token!!}")
            }
            mView?.hideLoading()
        }
    }

}