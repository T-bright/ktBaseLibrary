package com.tbright.ktbaselibrary.extension

import com.tbright.ktbaselibrary.utils.ToastUtils

fun String.showToast(){
    ToastUtils.showShort(this)
}