package com.tbright.ktbaseproject.demo.net.response

import com.tbright.ktbaselibrary.base.BaseResponse

class BaseMagicResponse<T> : BaseResponse<T>() {

    var result: T? = null

    var message : String? = null

    var code = 200

    override fun isResponseSuccess(): Boolean {
        return code == 200
    }

    override fun getResponseData(): T? {
        return result
    }

    override fun getResponseMessage(): String? {
        return message
    }

    override fun getResponseStatus(): Any? {
        return code
    }
}