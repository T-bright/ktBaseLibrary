package com.tbright.ktbaselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tbright.ktbaselibrary.event.MessageEvent
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        return initView(inflater, container, savedInstanceState)
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    open fun <T> onMessageEvent(messageEvent: MessageEvent<T>) {

    }
}