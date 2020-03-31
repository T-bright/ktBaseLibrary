package com.tbright.ktbaseproject.demo.bean

class MusicBean {
    var title: String? = null
    var cid: Int? = 0
    var channellist: List<ChannelList>? = null
}

class ChannelList {
    var thumb: String? = null

    var name: String? = null

    var cate_name: String? = null

    var cate_sname: String? = null

    var ch_name: String? = null

    var value: Int? = null

    var channelid: String? = null
}