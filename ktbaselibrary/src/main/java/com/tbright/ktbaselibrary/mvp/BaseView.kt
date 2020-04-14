package com.tbright.ktbaselibrary.mvp

interface BaseView {
    fun showLoading()

    fun hideLoading()

    /**
     * 网络错误等之类的错误提示
     * @dayParam errorMessage
     */
    fun showError(errorMessage: String)

    fun finishRefresh()

    /**
     * @param isLoaderAll:是否加载所有
     */
    fun finishLoadMore(isLoaderAll: Boolean = false)
}