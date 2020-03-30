package com.tbright.ktbaselibrary.extension

import com.blankj.utilcode.util.ToastUtils

fun String.showToast(){
    ToastUtils.showShort(this)
}