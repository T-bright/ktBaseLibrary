package com.tbright.ktbaselibrary.net.download

class DownloadInfo {
    /**
     * 是否开启断点续传。false.不开启；true.开启
     * 默认不开启
     */
    var isOpenBreakpointResume = false

    var total: Long = 0
    var progress: Long = 0
    var url: String = ""
    var filename: String = ""


}