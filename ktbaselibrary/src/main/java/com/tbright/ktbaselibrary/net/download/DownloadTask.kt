package com.tbright.ktbaselibrary.net.download

import android.net.Uri
import java.io.File

class DownloadTask private constructor(var url: String?,
                                       var uri: Uri?,
                                       var priority: Int, //下载的优先级，值越大，优先级越高
                                       var bufferSize: Int = 16384,
                                       var headerMaps: Map<String, String> //header
) : Comparable<DownloadTask> {
    companion object {
        const val DEFAULT = 0 //默认的状态，已经添加到下载队列，但还没开始下载
        const val START = 1 //开始下载
        const val CANCEL = 2 //取消下载
        const val ERROR = 3 //下载错误
        const val FINISH = 4 //下载完成
    }

    var downloadType = DEFAULT
    var totalLength: Long = 0
    var process: Long = 0

    //是否在正在下载
    fun isDownLoading(): Boolean {
        return downloadType == START
    }

    class Build {
        private var url: String? = null
        private var uri: Uri? = null
        private var priority: Int = 0
        private var headerMaps = hashMapOf<String, String>()
        private var bufferSize = 16384 //byte

        constructor(url: String, parentPath: String, filename: String) {
            this.url = url
        }

        constructor(url: String, file: File) {
            this.url = url
            this.uri = Uri.fromFile(file)
        }

        fun setPriority(priority: Int): Build {
            this.priority = priority
            return this
        }


        fun setBufferSize(bufferSize: Int): Build {
            this.bufferSize = bufferSize
            return this
        }

        fun build(): DownloadTask {
            return DownloadTask(url, uri, priority, bufferSize, headerMaps)
        }

        fun addHeader(key: String, value: String) {
            headerMaps[key] = value
        }

        fun addHeaders(headerMaps: Map<String, String>) {
            this.headerMaps.putAll(headerMaps)
        }
    }

    override fun compareTo(other: DownloadTask): Int {
        return other.priority - this.priority
    }
}
