package com.tbright.ktbaselibrary.mvp

import android.content.Context
import com.tbright.ktbaselibrary.base.BaseFragment
import com.tbright.ktbaselibrary.utils.ReflectUtils

abstract class BaseMvpFragment<P : IPresenter> : BaseFragment(), BaseView {

    open var mPresenter: P? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //mPresenter的初始化可以换成dagger
        mPresenter = ReflectUtils.getObject(this, 0)
        mPresenter?.onAttachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMessage: String) {

    }
}