package com.tbright.ktbaseproject.demo.ui.fragment

import com.tbright.ktbaselibrary.extension.response
import com.tbright.ktbaseproject.demo.net.api.GankServices
import kotlinx.coroutines.launch

class MainFragmentPresenter : MainFragmentContract.MainPresenter() {

    override var mModel: MainFragmentModel? = MainFragmentModel()

    override fun singlePoetry() {
        mView?.showLoading()
        mainScope.launch {
            var singlePoetry = mModel?.singlePoetry()
            singlePoetry?.let {
                mView?.showResult(it)
            }
            mView?.hideLoading()
        }
    }

    //并行请求。当一个页面有多个请求的时候，可以并行请求
    override fun parallelRequest() {
        mView?.showLoading()
        mModel?.parallelRequest { result ->
            if (result != null) {
                mView?.showResult(result)
                mView?.hideLoading()
            }
        }
    }

    override fun changeBaseUrl() {
        mView?.showLoading()
        mainScope.launch {
            var gank = GankServices.instance.getGanHuo().response()
            gank?.let {
                mView?.showResult(it.first().title!!)

                mView?.hideLoading()
            }
        }
    }
}