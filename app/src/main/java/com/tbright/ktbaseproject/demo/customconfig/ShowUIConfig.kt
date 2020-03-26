package com.tbright.ktbaseproject.demo.customconfig

import com.tbright.ktbaselibrary.event.MessageEvent
import com.tbright.ktbaselibrary.extension.hideLoadingDialog
import com.tbright.ktbaselibrary.extension.showLoadingDialog
import com.tbright.ktbaselibrary.extension.showToast
import com.tbright.ktbaselibrary.proxy.ShowUIProxy
import com.tbright.ktbaselibrary.utils.getTopActivity
import com.tbright.ktbaseproject.demo.extensions.reLogin

class ShowUIConfig : ShowUIProxy {
    override fun <T> parseResponseFailMessage(messageEvent: MessageEvent<T>) {
        when (messageEvent.code) {
            EVENTCODE_RESPONSE_FAIL -> {
                hideLoading()
                if (messageEvent.data != null) {
                    var errorMessage = messageEvent.data as String
                    showError(errorMessage)
                } else {
                    showError("网络错误")
                }
            }
            EVENTCODE_RELOGIN -> {//重新登录
                hideLoading()
                getTopActivity().reLogin()
            }
        }
    }

    override fun showLoading() {
        getTopActivity().showLoadingDialog()
    }

    override fun hideLoading() {
        getTopActivity().hideLoadingDialog()
    }

    override fun showError(errorMessage: String) {
        errorMessage.showToast()
    }
}