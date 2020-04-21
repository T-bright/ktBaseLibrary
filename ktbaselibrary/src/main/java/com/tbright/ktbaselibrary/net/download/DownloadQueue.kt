package com.tbright.ktbaselibrary.net.download

import android.os.Handler
import android.os.Looper
import android.util.Log
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
        val iterator = downloadQueues.iterator()
        while (iterator.hasNext()) {
            val task = iterator.next()
            if (task.url == url && task.isDownLoading()) {
                downLoadEngine.cancel(url)
            }
        }
    }

    fun cancel(downloadTask: DownloadTask) {
        if (downloadTask.isDownLoading()) {
            downLoadEngine.cancel(downloadTask)
        }
    }


    fun cancelAll() {
        downloadQueues.clear()
        downLoadEngine.cancelAll()
        count.set(0)
    }

    fun startDownload(downloadCallback: DownloadCallback) {
        singleThread.execute {
            while (true) {
                if (count.get() < maxDownloadCount) {
                    val task = downloadQueues.take()
                    if (task != null) {
                        count.addAndGet(1)
                        task.downloadType = DownloadTask.START
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
                Log.e("CCC","onCancel  url :${url}")
                val iterator = downloadQueues.iterator()
                while (iterator.hasNext()) {
                    val downloadTask = iterator.next()
                    if (downloadTask.url == url) {
                        downloadTask.downloadType = DownloadTask.CANCEL
                        iterator.remove()
                        downloadCallback.canceled(downloadTask)
                        count.decrementAndGet()
                    }
                }
            }

            override fun onFailure(downloadTask: DownloadTask, e: Exception) {
                mainHandler.post {
                    downloadTask.downloadType = DownloadTask.ERROR
                    downloadCallback.error(downloadTask, e)
                    count.decrementAndGet()
                }
            }

            override fun onSuccess(downloadTask: DownloadTask, inputStream: InputStream) {
                val buffer = ByteArray(downloadTask.bufferSize)
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
                    downloadTask.downloadType = DownloadTask.FINISH
                    downloadCallback.completed(downloadTask)
                    count.decrementAndGet()
                }
                fos.close()
                inputStream.close()
            }
        })
    }
}
