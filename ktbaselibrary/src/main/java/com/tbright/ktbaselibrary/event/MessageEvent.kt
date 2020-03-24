package com.tbright.ktbaselibrary.event

import org.greenrobot.eventbus.EventBus

class MessageEvent<T> {
    var code: Int = 0
        private set
    var data: T? = null

    constructor() {

    }

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }

    fun send() {
        EventBus.getDefault().post(this)
    }
}
