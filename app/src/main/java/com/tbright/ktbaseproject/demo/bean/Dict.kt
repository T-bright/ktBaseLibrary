package com.tbright.ktbaseproject.demo.bean

data class Dict(
        var key: String = "",
        var type: String? = null, // 1.学段2.年级3.版本4.册别5.学科6.家属关系，指定sysDictId无此字段
        var value: String = "",
        var valueEn: String = ""
)