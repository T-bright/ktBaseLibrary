package com.tbright.ktbaselibrary.net.download

import okhttp3.*
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

//写一个基于OkHttp的下载工具类
class OkHttpDownload : DownLoadEngine {
    private val okHttpClient = OkHttpClient()
    private val callCaches = ConcurrentHashMap<String, Call>()
    private val callbackCaches = ConcurrentHashMap<String, DownLoadEngine.CallBack>()
    override fun startDownload(downloadTask: DownloadTask, callback: DownLoadEngine.CallBack) {
        val headersBuilder = Headers.Builder()
        if (downloadTask.headerMaps.isNotEmpty()) {
            val iterator = downloadTask.headerMaps.iterator()
            while (iterator.hasNext()) {
                headersBuilder.add(iterator.next().key, iterator.next().value)
            }
        }
        val request: Request = Request.Builder()
            .url(downloadTask.url!!)
            .headers(headersBuilder.build())
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
                if (response.isSuccessful) {
                    downloadTask.totalLength = response.body()!!.contentLength()
                    callback.onSuccess(downloadTask, response.body()!!.byteStream())
                    callCaches.remove(downloadTask.url!!)
                }
            }
        })
    }

    override fun cancel(downloadTask: DownloadTask) {
        callCaches[downloadTask.url]?.cancel()
        callCaches.remove(downloadTask.url)
        var callback = callbackCaches[downloadTask.url]
        callback?.onCancel(downloadTask.url!!)
        callback = null
    }

    override fun cancel(url: String) {
        val call = callCaches[url]
        call?.cancel()
        callCaches.remove(url)
        var callback = callbackCaches[url]
        callback?.onCancel(url)
        callback = null
    }

    override fun cancelAll() {
        callCaches.values.forEach {
            it.cancel()
        }
        callCaches.clear()
        callbackCaches.clear()
    }

}