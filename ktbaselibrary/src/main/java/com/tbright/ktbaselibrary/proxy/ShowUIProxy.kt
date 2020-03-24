package com.tbright.ktbaselibrary.proxy

import com.tbright.ktbaselibrary.event.MessageEvent

interface ShowUIProxy {

    fun <T> parseResponseFailMessage(messageEvent: MessageEvent<T>)

    fun showLoading()

    fun hideLoading()

    /**
     * 网络错误等之类的错误提示
     * @dayParam errorMessage
     */
    fun showError(errorMessage: String)
}