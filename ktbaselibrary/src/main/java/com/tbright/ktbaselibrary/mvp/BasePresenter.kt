package com.tbright.ktbaselibrary.mvp

import com.tbright.ktbaselibrary.global.GlobalConfig
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import java.lang.NullPointerException


abstract class BasePresenter<M : IModel, V : BaseView>: IPresenter {
    open var mView: V? = null
    open var mModel : M? = null

    protected open var mainScope = MainScope()

    @Suppress("UNCHECKED_CAST")
    override fun onAttachView(baseView: BaseView) {
        this.mView = baseView as V
    }

   abstract fun onAttachModel() : M?

    override fun onDestroy() {
        mView?.hideLoading()
        mModel?.onDestroy()
        mainScope.cancel()
        mView = null
        mModel = null
    }
}