package com.tbright.ktbaselibrary.net.download

import android.net.Uri
import java.io.File

class DownloadTask private constructor(var url: String?,
                                       var uri: Uri?,
                                       var priority: Int //下载的优先级，值越大，优先级越高
) : Comparable<DownloadTask> {
    var totalLength: Long = 0
    var process: Long = 0

    class Build {
        var url: String? = null
        var uri: Uri? = null
        var tag: String? = null
        var priority: Int = 0

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

        fun build(): DownloadTask {
            return DownloadTask(url, uri, priority)
        }

        fun addHeader() {

        }

        fun addHeaders() {

        }
    }

    override fun compareTo(other: DownloadTask): Int {
        return other.priority - this.priority
    }
}
