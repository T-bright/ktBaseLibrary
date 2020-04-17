package com.tbright.ktbaselibrary.net.download

import java.lang.Exception

interface DownloadCallback {
    fun started(task: DownloadTask)

    fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long)

    fun canceled(task: DownloadTask)

    fun error(task: DownloadTask, e: Exception)
    
    fun completed(task: DownloadTask)
}