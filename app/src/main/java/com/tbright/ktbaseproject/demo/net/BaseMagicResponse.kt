package com.tbright.ktbaseproject.demo.net

import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaseproject.demo.customconfig.RESPONSE_OK

class BaseMagicResponse<T> : BaseResponse<T>() {

    var data: T? = null

    var message : String? = null

    var status = ""

    override fun isResponseSuccess(): Boolean {
        return status == RESPONSE_OK
    }

    override fun getResponseData(): T? {
        return data
    }

    override fun getResponseMessage(): String? {
        return message
    }
}