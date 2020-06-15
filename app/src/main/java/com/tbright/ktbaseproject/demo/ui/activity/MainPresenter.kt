package com.tbright.ktbaseproject.demo.ui.activity

import com.tbright.ktbaselibrary.extension.create
import com.tbright.ktbaselibrary.extension.response
import com.tbright.ktbaseproject.demo.net.api.ApiServices
import com.tbright.ktbaseproject.demo.net.api.GankServices
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPresenter : MainContract.MainPresenter() {


    override fun singlePoetry() {
        mView?.showLoading()
        mainScope.launch {
            var singlePoetry = ApiServices.instance.singlePoetry().response()
            singlePoetry?.let {
                mView?.loginResult(it)
            }
            mView?.hideLoading()
        }
    }

    //并行请求。当一个页面有多个请求的时候，可以并行请求
    override fun parallelRequest() {
        mView?.showLoading()
        mainScope.launch {

            //使用  async 并行网络 请求
            var singlePoetry = async { ApiServices.instance.singlePoetry().response() }

            var musicList = async { ApiServices.instance.getMusicList().response() }

            var result = "${singlePoetry.await().toString()}------${musicList?.await()?.first()?.title}"

            mView?.loginResult(result)

            mView?.hideLoading()
        }
    }

    override fun changeBaseUrl() {
        mView?.showLoading()
        mainScope.launch {
            var gank = GankServices.instance.getGanHuo().response()
            gank?.let {
                mView?.loginResult(it.first().title!!)

                mView?.hideLoading()
            }
        }
    }

}