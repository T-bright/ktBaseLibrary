package com.tbright.ktbaselibrary.mvp

interface IPresenter {
    fun onAttachView(baseView: BaseView)

    fun onDestroy()
}