package com.tbright.ktbaselibrary.net.download

import okhttp3.*
import java.io.IOException

//写一个基于OkHttp的下载工具类
class OkHttpDownload : DownLoadEngine {
    private val okHttpClient = OkHttpClient()
    private val callCaches = hashMapOf<String, Call>()
    private val callbackCaches = hashMapOf<String, DownLoadEngine.CallBack>()
    override fun startDownload(downloadTask: DownloadTask, callback: DownLoadEngine.CallBack) {
        val request: Request = Request.Builder()
            .url(downloadTask.url!!)
            .addHeader("Connection", "close")
            .build()
        var call = okHttpClient.newCall(request)
        callCaches[downloadTask.url!!] = call
        callbackCaches[downloadTask.url!!] = callback
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(downloadTask, e)
                callCaches.remove(downloadTask.url!!)
            }

            override fun onResponse(call: Call, response: Response) {
                callCaches.remove(downloadTask.url!!)
                if (response.isSuccessful) {
                    downloadTask.totalLength = response.body()!!.contentLength()
                    callback.onSuccess(downloadTask, response.body()!!.byteStream())
                }
            }
        })
    }

    override fun cancel(downloadTask: DownloadTask){
        callCaches[downloadTask.url]?.cancel()
        callCaches.remove(downloadTask.url)
        callbackCaches[downloadTask.url]?.onCancel(downloadTask.url!!)
    }

    override fun cancel(url: String) {
        val call = callCaches[url]
        call?.cancel()
        callCaches.remove(url)
        callbackCaches[url]?.onCancel(url)
    }

    override fun cancelAll() {
        callCaches.values.forEach {
            it.cancel()
        }
        callCaches.clear()
        callbackCaches.clear()
    }

}