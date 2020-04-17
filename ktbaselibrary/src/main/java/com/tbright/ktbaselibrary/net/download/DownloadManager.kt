package com.tbright.ktbaselibrary.net.download


class DownloadManager {
    private var downloadQueue: DownloadQueue? = null

    fun enqueue(downloadTask: DownloadTask, downloadCallback: DownloadCallback) {
        downloadQueue?.enqueue(downloadTask)
        downloadQueue?.startDownload(downloadCallback)
    }

    fun enqueue(downloadTasks: List<DownloadTask>, downloadCallback: DownloadCallback) {
        downloadQueue?.enqueue(downloadTasks)
        downloadQueue?.startDownload(downloadCallback)
    }

    fun cancel(downloadTask: DownloadTask) {
        downloadQueue?.cancel(downloadTask)
    }

    fun cancel(url: String) {
        downloadQueue?.cancel(url)
    }

    fun cancelAll() {
        downloadQueue?.cancelAll()
    }

}