package com.tbright.ktbaselibrary.extension

import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaselibrary.global.GlobalConfig
import kotlinx.coroutines.Deferred

suspend fun <T> Deferred<BaseResponse<T>>.response(): T? {
    return GlobalConfig.httpConfigProxy?.parseResponseData(this)
}