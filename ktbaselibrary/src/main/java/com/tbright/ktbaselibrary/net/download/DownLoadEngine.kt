package com.tbright.ktbaselibrary.net.download

import java.io.InputStream


interface DownLoadEngine {

    fun startDownload(downloadTask: DownloadTask, callback: CallBack)

    fun cancel(downloadTask: DownloadTask)

    fun cancel(url: String)

    fun cancelAll()

    interface CallBack {
        fun onCancel(url: String)

        fun onFailure(downloadTask: DownloadTask, e: Exception)

        fun onSuccess(downloadTask: DownloadTask, inputStream: InputStream)
    }
}