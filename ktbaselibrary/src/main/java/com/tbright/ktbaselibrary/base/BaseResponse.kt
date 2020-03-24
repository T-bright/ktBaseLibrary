package com.tbright.ktbaselibrary.base

class BaseResponse<T> {
    var status: String = ""
    var message: String? = null
    var data: T? = null
}
