package com.tbright.ktbaselibrary.proxy

import com.blankj.utilcode.util.NetworkUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tbright.ktbaselibrary.BuildConfig
import com.tbright.ktbaselibrary.base.BaseResponse
import com.tbright.ktbaselibrary.event.MessageEvent
import com.tbright.ktbaselibrary.global.GlobalConfig
import com.tbright.ktbaselibrary.global.TIME_OUT
import com.tbright.ktbaselibrary.net.exception.NoNetworkException

import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
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


class HttpConfigProxyDefault : HttpConfigProxy() {


    override var baseUrl: String = ""


    override var baseUrls: Map<String, String>
        set(value) {}
        get() {
            var urls = linkedMapOf<String, String>()
            if (urls.isNotEmpty()) return urls
            if (GlobalConfig.isDebug) {
                urls["baseUrl"] = "http://www.baidu.com"
            } else {
                urls["baseUrl"] = "http://www.baidu.com"
            }
            return urls
        }

    override suspend fun <T> parseResponseData(responseData: Deferred<BaseResponse<T>>): T? {
        try {
            var response = responseData.await()
            if (response.status == RESPONSE_OK) {
                return response.data
            }else{
                MessageEvent(EVENTCODE_RESPONSE_FAIL, response.message).send()
                return null
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
                is NoNetworkException -> errMsg = "无可用网络"
                is HttpException -> {
                    when (e.code()) {
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


    override fun initRetrofit() {
        initClient()
        mRetrofitBuilder = Retrofit.Builder()
        mRetrofit = mRetrofitBuilder?.run {
            baseUrl(
                GlobalConfig.httpConfigProxy?.baseUrl
                    ?: GlobalConfig.httpConfigProxy?.baseUrls!!.values.first()
            )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(mOkHttpClientBuilder!!.build())
                .build()
            build()
        }
    }

    private var mRetrofitServices = hashMapOf<String, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T> create(clazz: Class<T>) : T {
        var key = clazz.canonicalName
        var mRetrofitService = mRetrofitServices[key]
        if (mRetrofitService == null) {
            mRetrofitService = mRetrofit!!.create(clazz)
            mRetrofitServices[key!!] = mRetrofitService!!
        }
        return mRetrofitService as T
    }

    private fun initClient() {
        mOkHttpClientBuilder = OkHttpClient.Builder()
        mOkHttpClientBuilder?.run {
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)

            //错误重连
            retryOnConnectionFailure(true)

            //无网络判断
            addInterceptor { chain ->
                val request = chain.request()
                if (!NetworkUtils.isConnected()) {
                    throw NoNetworkException()
                }
                chain.proceed(request)
            }
        }
    }
}