package com.tbright.ktbaselibrary.mvp

import android.content.Context
import com.tbright.ktbaselibrary.base.BaseFragment
import com.tbright.ktbaselibrary.event.MessageEvent
import com.tbright.ktbaselibrary.global.GlobalConfig
import com.tbright.ktbaselibrary.utils.ReflectUtils

abstract class BaseMvpFragment<P : IPresenter> : BaseFragment(), BaseView {

    open var mPresenter: P? = null

    override fun onAttach(context: Context) {
        //mPresenter的初始化可以换成dagger
        mPresenter = ReflectUtils.getObject(this, 0)
        mPresenter?.onAttachView(this)
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    override fun onMessageEvent(messageEvent: MessageEvent<*>) {
        super.onMessageEvent(messageEvent)
        GlobalConfig.showUIProxy?.parseResponseFailMessage(messageEvent)
    }

    override fun showLoading() {
        GlobalConfig.showUIProxy?.showLoading()
    }

    override fun hideLoading() {
        GlobalConfig.showUIProxy?.hideLoading()
    }

    override fun showError(errorMessage: String) {
        GlobalConfig.showUIProxy?.showError(errorMessage)
    }

    override fun finishRefresh() {

    }

     override fun finishLoadMore(isLoaderAll: Boolean) {

     }


 }