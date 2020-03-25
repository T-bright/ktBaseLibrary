package com.tbright.ktbaselibrary.mvp

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseModel : IModel {

    protected open var mainScope = MainScope()

    override fun onDestroy() {
        mainScope.cancel()
    }

}