package com.tbright.ktbaselibrary.mvp

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


abstract class BasePresenter<M : IModel, V : BaseView>: IPresenter {
    open var mView: V? = null
    open var mModel : M? = null

    protected open var mainScope = MainScope()

    @Suppress("UNCHECKED_CAST")
    override fun onAttachView(baseView: BaseView) {
        this.mView = baseView as V
    }

    override fun onDestroy() {
        mView?.hideLoading()
        mModel?.onDestroy()
        mainScope.cancel()
        mView = null
        mModel = null
    }
}