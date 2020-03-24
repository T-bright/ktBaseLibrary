package com.tbright.ktbaselibrary.proxy

import com.bumptech.glide.load.HttpException
import com.tbright.ktbaselibrary.BuildConfig
import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaselibrary.event.MessageEvent

import kotlinx.coroutines.Deferred
import org.json.JSONException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


const val RESPONSE_OK = "S00000000"

const val UNAUTHORIZED = 401
const val FORBIDDEN = 403
const val NOT_FOUND = 404
const val REQUEST_TIMEOUT = 408
const val INTERNAL_SERVER_ERROR = 500
const val BAD_GATEWAY = 502
const val SERVICE_UNAVAILABLE = 503
const val GATEWAY_TIMEOUT = 504

const val EVENTCODE_RELOGIN = 998 //需要重新登录
const val EVENTCODE_RESPONSE_FAIL = 999 //http请求失败


class HttpConfigProxyDefault : HttpConfigProxy {
    override fun getBaseUrl(): Map<String, String> {
        var urls = linkedMapOf<String, String>()
        if (BuildConfig.DEBUG) {
            urls.put("baseUrl", "http://www.baidu.com")
        } else {
            urls.put("baseUrl", "http://www.baidu.com")
        }
        return urls
    }

    override suspend fun <T> parseResponseData(responseData: Deferred<BaseResponse<T>>): T? {
        try {
            var response = responseData.await()
            if (response.status == RESPONSE_OK) {
                return response.data
            }
        } catch (e: Throwable) {
            var errMsg = "网络异常"
            when (e) {
                is UnknownHostException -> errMsg = "连接失败"
                is ConnectException -> errMsg = "连接失败"
                is SocketTimeoutException -> errMsg = "连接超时"
                is InterruptedIOException -> errMsg = "连接中断"
                is SSLHandshakeException -> errMsg = "证书验证失败"
                is JSONException -> errMsg = "数据解析错误"
                is HttpException -> {
                    when (e.statusCode) {
                        UNAUTHORIZED, FORBIDDEN -> {//这两个一般会要求重新登录
                            MessageEvent(EVENTCODE_RELOGIN, errMsg).send()
                            return null
                        }
                    }
                }
                else -> errMsg = e.message.toString()
            }
            MessageEvent(EVENTCODE_RESPONSE_FAIL, errMsg).send()
        }
        return null
    }

}