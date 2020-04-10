package com.tbright.ktbaselibrary.base

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.blankj.utilcode.util.BarUtils
import com.tbright.ktbaselibrary.event.MessageEvent
import com.tbright.ktbaselibrary.utils.ActivityUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity() {

    //设置状态栏背景颜色。根据设置的颜色可以自动判断是 深色的背景和浅色的背景。可以在子类重写设置颜色
    open var statusBarColor = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewCreate()
        setContentView(getLayoutId())
        ActivityUtils.addActivity(this)
        EventBus.getDefault().register(this)
        setStatusBarColor(statusBarColor)
        initView(savedInstanceState)
        initData()
    }

    open fun setStatusBarColor(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int = 0) {
        setStatusBarColor(color, alpha, isLightStatus(color))
    }

    open fun setStatusBarColor(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int = 0, isLightMode: Boolean) {
        BarUtils.setStatusBarColor(this, color, alpha, false)
        BarUtils.setStatusBarLightMode(this, isLightMode)
    }

    private fun isLightStatus(@ColorInt color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) >= 0.5
    }

    open fun beforeViewCreate() {

    }

    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    open fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtils.removeActivity(this)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent<*>) {

    }

}