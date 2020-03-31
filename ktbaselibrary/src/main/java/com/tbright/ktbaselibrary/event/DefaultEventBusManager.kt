package com.tbright.ktbaselibrary.event

import com.tbright.ktbaselibrary.proxy.EventBusProxy
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

object DefaultEventBusManager : EventBusProxy {

    private const val EVENT_BUS = "org.greenrobot.eventbus.EventBus"

    private var isUseEventBus = false

    init {
        isUseEventBus = try {
            Class.forName(EVENT_BUS)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun register(subscriber: Any?) {
        if (isUseEventBus) {
            org.greenrobot.eventbus.EventBus.getDefault().register(subscriber)
        }
    }


    override fun unregister(subscriber: Any?) {
        if (isUseEventBus) {
            org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber)
        }
    }

    override fun <T> post(event: MessageEvent<T>) {
        if (isUseEventBus) {
            org.greenrobot.eventbus.EventBus.getDefault().post(event)
        }
    }

    override fun <T> postSticky(event: MessageEvent<T>) {
        if (isUseEventBus) {
            org.greenrobot.eventbus.EventBus.getDefault().postSticky(event)
        }
    }

    override fun <T> removeSticky(event: MessageEvent<T>) {
        if (isUseEventBus) {
            org.greenrobot.eventbus.EventBus.getDefault().removeAllStickyEvents()
            org.greenrobot.eventbus.EventBus.getDefault().removeStickyEvent(event)
        }
    }

    override fun clear() {
        if (isUseEventBus) {
            org.greenrobot.eventbus.EventBus.getDefault().removeAllStickyEvents()
        }
    }

    private var onEventBusListener: EventBusProxy.OnEventBusListener? = null
    override fun setOnEventBusListener(onEventBusListener: EventBusProxy.OnEventBusListener) {
        this.onEventBusListener = onEventBusListener
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T> onMessageEvent(messageEvent: MessageEvent<T>) {
        onEventBusListener?.onMessage(messageEvent)
    }
}