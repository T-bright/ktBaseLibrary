package com.tbright.ktbaselibrary.proxy

import com.tbright.ktbaselibrary.event.MessageEvent
import com.tbright.ktbaselibrary.extension.hideLoadingDialog
import com.tbright.ktbaselibrary.extension.showLoadingDialog
import com.tbright.ktbaselibrary.extension.showToast
import com.tbright.ktbaselibrary.utils.getTopActivity

class ShowUIProxyDefault : ShowUIProxy {
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