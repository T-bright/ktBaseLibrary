package com.tbright.ktbaselibrary.net.download

import android.net.Uri
import java.io.File

class DownloadTask private constructor(var url: String?,
                                       var uri: Uri?,
                                       var priority: Int, //下载的优先级，值越大，优先级越高
                                       var headerMaps: Map<String, String> //header
) : Comparable<DownloadTask> {
    var totalLength: Long = 0
    var process: Long = 0

    class Build {
        private var url: String? = null
        private var uri: Uri? = null
        private var priority: Int = 0
        private var headerMaps = hashMapOf<String, String>()

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
            return DownloadTask(url, uri, priority, headerMaps)
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
