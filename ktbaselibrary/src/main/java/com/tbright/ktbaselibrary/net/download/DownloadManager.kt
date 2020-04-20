package com.tbright.ktbaselibrary.net.download

import android.os.SystemClock
import kotlin.concurrent.thread


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
    fun addTask(downloadTask: DownloadTask, downloadCallback: DownloadCallback) {
        downloadQueue.enqueue(downloadTask)
        downloadQueue.startDownload(downloadCallback)
    }

    /**
     * 添加下载任务
     */
    fun addTask(downloadTasks: List<DownloadTask>, downloadCallback: DownloadCallback) {
        downloadQueue.enqueue(downloadTasks)
        thread {
            SystemClock.sleep(1000)
            downloadQueue.startDownload(downloadCallback)
        }
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
    fun getNowDownLoadCount(){

    }

}