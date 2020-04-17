package com.tbright.ktbaselibrary.net.download

import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

class DownloadQueue constructor(downLoadEngine: DownLoadEngine? = null, var maxDownloadCount: Int = 5) {
    private var downloadQueues: PriorityBlockingQueue<DownloadTask> = PriorityBlockingQueue(Int.MAX_VALUE)
    private var downLoadEngine: DownLoadEngine
    private val count: AtomicInteger = AtomicInteger(0)

    init {
        if (downLoadEngine == null) {
            this.downLoadEngine = OkHttpDownload()
        } else {
            this.downLoadEngine = downLoadEngine
        }
    }

    fun enqueue(downloadTask: DownloadTask) {
        downloadQueues.put(downloadTask)
    }

    fun enqueue(downloadTasks: List<DownloadTask>) {
        downloadTasks.iterator().forEach {
            downloadQueues.put(it)
        }
    }

    fun cancel(url: String) {
        downLoadEngine.cancel(url)
        val iterator = downloadQueues.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().url == url) {
                iterator.remove()
            }
        }
    }

    fun cancel(downloadTask: DownloadTask) {
        downLoadEngine.cancel(downloadTask)
        downloadQueues.remove(downloadTask)
    }


    fun cancelAll() {
        downLoadEngine.cancelAll()
        downloadQueues.clear()
    }

    fun startDownload(downloadCallback: DownloadCallback) {
        while (count.get() < maxDownloadCount) {
            val task = downloadQueues.take()
            if (task != null) {
                count.addAndGet(1)
                downloadCallback.started(task)
                downLoadEngine.startDownload(task, object : DownLoadEngine.CallBack {
                    override fun onCancel(url: String) {
                        count.decrementAndGet()
                        downloadQueues.forEach {
                            if (it.url == url) {
                                downloadCallback.canceled(it)
                            }
                        }
                    }

                    override fun onFailure(downloadTask: DownloadTask, e: Exception) {
                        count.decrementAndGet()
                        downloadCallback.error(downloadTask, e)
                    }

                    override fun onSuccess(downloadTask: DownloadTask, inputStream: InputStream) {
                        count.decrementAndGet()
                        val buffer = ByteArray(1024 * 1000)
                        var len: Int = 0
                        var process: Long = 0
                        var fos = FileOutputStream("")
                        while (((inputStream.read(buffer)).also { len = it }) != -1) {
                            fos.write(buffer, 0, len)
                            process += len
                            downloadTask.process = process
                            downloadCallback.progress(downloadTask,process,downloadTask.totalLength)
                        }
                        fos.close()
                        inputStream.close()
                        downloadCallback.completed(downloadTask)
                    }
                })
            }
        }
    }
}