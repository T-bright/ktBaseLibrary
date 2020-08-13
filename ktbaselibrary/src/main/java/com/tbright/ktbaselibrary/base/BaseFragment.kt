package com.tbright.ktbaselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tbright.ktbaselibrary.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initData()
    }

    //是不是第一次加载
    private var isFirstLoad = true

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            lazyInitData()
            isFirstLoad = false
        }
    }

    /**
     * 延迟加载。
     * androidx 废弃掉了 setUserVisibleHint 方法。使用FragmentTransaction的setMaxLifecycle()来替代
     * 使用viewpager 须使用 FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) 适配器。
     * 注意：如果需要使用延迟加载，就用 lazyInitData 方法，不要与 initData 方法同时使用。initData 方法不具备延迟加载特性。
     */
    open fun lazyInitData() {

    }

    abstract fun getLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent<*>) {

    }
}