package com.tbright.ktbaselibrary.net.download

import java.io.File

/**
 * TODO 下载功能。目前还有bug。
 */
class DownloadManager {
    private val downloadQueue: DownloadQueue by lazy {
        DownloadQueue(downLoadEngine, maxDownloadCount)
    }
    private var maxDownloadCount = 5
    private var downLoadEngine: DownLoadEngine? = null

    /**
     * 设置最大下载个数，默认是5个
     */
    fun setMaxDownloadCount(maxDownloadCount: Int): DownloadManager {
        this.maxDownloadCount = maxDownloadCount
        return this
    }

    /**
     * 设置自定义下载引擎。默认的下载引擎是 OkHttpDownload
     * @see com.tbright.ktbaselibrary.net.download.OkHttpDownload
     */
    fun setDownLoadEngine(downLoadEngine: DownLoadEngine?): DownloadManager {
        this.downLoadEngine = downLoadEngine
        return this
    }

    /**
     * 添加下载任务
     */
    fun addTask(downloadTask: DownloadTask, downloadCallback: DownloadCallback): DownloadManager {
        downloadQueue.enqueue(downloadTask)
        downloadQueue.startDownload(downloadCallback)
        return this
    }

    /**
     * 添加下载任务
     */
    fun addTask(downloadTasks: List<DownloadTask>, downloadCallback: DownloadCallback): DownloadManager {
        downloadQueue.enqueue(downloadTasks)
        downloadQueue.startDownload(downloadCallback)
        return this
    }

    fun addTask(url: String, file: File, priority: Int = 0, downloadCallback: DownloadCallback): DownloadManager {
        var downloadTask = DownloadTask.Build(url, file).setPriority(priority).build()
        downloadQueue.enqueue(downloadTask)
        downloadQueue.startDownload(downloadCallback)
        return this
    }

    fun addTask(url: String, parentPath: String, filename: String, priority: Int = 0, downloadCallback: DownloadCallback): DownloadManager {
        var downloadTask = DownloadTask.Build(url, parentPath, filename).setPriority(priority).build()
        downloadQueue.enqueue(downloadTask)
        downloadQueue.startDownload(downloadCallback)
        return this
    }

    /**
     * 取消下载
     */
    fun cancel(downloadTask: DownloadTask) {
        downloadQueue.cancel(downloadTask)
    }

    /**
     * 取消下载
     */
    fun cancel(url: String) {
        downloadQueue.cancel(url)
    }

    /**
     * 取消所有下载
     */
    fun cancelAll() {
        downloadQueue.cancelAll()
    }

    /**
     * 获取当前正在下载的数量
     */
    fun getNowDownLoadCount() {

    }


}