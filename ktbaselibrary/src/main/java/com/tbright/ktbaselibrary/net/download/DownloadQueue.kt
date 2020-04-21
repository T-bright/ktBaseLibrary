package com.tbright.ktbaselibrary.net.download

import android.os.Handler
import android.os.Looper
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.Executors
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

class DownloadQueue constructor(downLoadEngine: DownLoadEngine? = null, var maxDownloadCount: Int = 5) {
    private var downloadQueues: PriorityBlockingQueue<DownloadTask> = PriorityBlockingQueue()
    private var downLoadEngine: DownLoadEngine
    private val mainHandler = Handler(Looper.getMainLooper())
    private val count: AtomicInteger = AtomicInteger(0)
    private val singleThread = Executors.newSingleThreadExecutor()

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
        downloadQueues.addAll(downloadTasks)
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
        singleThread.execute {
            while (true) {
                if (count.get() < maxDownloadCount) {
                    val task = downloadQueues.take()
                    if (task != null) {
                        count.addAndGet(1)
                        mainHandler.post {
                            downloadCallback.started(task)
                        }
                        download(task, downloadCallback)
                    }
                }
            }
        }
    }

    private fun download(task: DownloadTask, downloadCallback: DownloadCallback) {
        downLoadEngine.startDownload(task, object : DownLoadEngine.CallBack {
            override fun onCancel(url: String) {
                downloadQueues.forEach {
                    if (it.url == url) {
                        mainHandler.post {
                            downloadCallback.canceled(it)
                            count.decrementAndGet()
                        }
                    }
                }
            }

            override fun onFailure(downloadTask: DownloadTask, e: Exception) {
                mainHandler.post {
                    downloadCallback.error(downloadTask, e)
                    count.decrementAndGet()
                }
            }

            override fun onSuccess(downloadTask: DownloadTask, inputStream: InputStream) {
                val buffer = ByteArray(1024 * 1000)
                var len: Int = 0
                var process: Long = 0
                var fos = FileOutputStream(downloadTask.uri!!.encodedPath?.orEmpty())
                while (((inputStream.read(buffer)).also { len = it }) != -1) {
                    fos.write(buffer, 0, len)
                    process += len
                    downloadTask.process = process
                    mainHandler.post { downloadCallback.progress(downloadTask, process, downloadTask.totalLength) }
                }

                mainHandler.post {
                    downloadCallback.completed(downloadTask)
                    count.decrementAndGet()
                }
                fos.close()
                inputStream.close()
            }
        })
    }
}
