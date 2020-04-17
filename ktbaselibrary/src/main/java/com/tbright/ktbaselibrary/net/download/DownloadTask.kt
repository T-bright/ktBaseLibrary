package com.tbright.ktbaselibrary.net.download

import android.net.Uri
import java.io.File

class DownloadTask private constructor(var url: String?, var uri: Uri?, var tag: String?) {
    var totalLength: Long = 0
    var process: Long = 0

    companion object {
        fun enqueues(tasks: List<DownloadTask>, downloadCallback: DownloadCallback) {
        }

        fun cancel(url: String) {

        }

        fun cancelAll() {

        }
    }

    fun enqueue(downloadCallback: DownloadCallback) {
    }

    fun cancel() {

    }

    class Build {
        var url: String? = null
        var uri: Uri? = null
        var tag: String? = null

        constructor(url: String, parentPath: String, filename: String) {
            this.url = url
        }

        constructor(url: String, file: File) {
            this.url = url
            this.uri = Uri.fromFile(file)
        }

        fun build(): DownloadTask {
            return DownloadTask(url, uri, tag)
        }

        fun addHeader() {

        }

        fun addHeaders() {

        }
    }
}
