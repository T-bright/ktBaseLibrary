package com.tbright.ktbaselibrary.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbright.ktbaselibrary.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewCreate()
        setContentView(getLayoutId())
        EventBus.getDefault().register(this)
        initView(savedInstanceState)
        initData()
    }


    open fun beforeViewCreate() {

    }

    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> onMessageEvent(messageEvent: MessageEvent<T>) {

    }

}