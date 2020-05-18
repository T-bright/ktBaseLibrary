package com.tbright.ktbaselibrary.utils

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

object AppUtils {
    var mApplication: Application? = null
    var activityLifecycleImpl: ActivityLifecycleImpl = ActivityLifecycleImpl()
    private var lifecycleCallbacks = arrayListOf<ActivityLifecycleCallbacks>()
    fun init(application: Application, callback: ActivityLifecycleCallbacks? = null) {
        this.mApplication = application
        registerActivityLifecycleCallback(activityLifecycleImpl)
        callback?.let {
            registerActivityLifecycleCallback(callback)
        }
    }

    fun registerActivityLifecycleCallback(callback: ActivityLifecycleCallbacks) {
        if(!lifecycleCallbacks.contains(callback)){
            mApplication?.registerActivityLifecycleCallbacks(callback)
            lifecycleCallbacks.add(callback)
        }
    }

    fun unregisterActivityLifecycleCallback(callback: ActivityLifecycleCallbacks) {
        if(lifecycleCallbacks.contains(callback)){
            mApplication?.unregisterActivityLifecycleCallbacks(callback)
            lifecycleCallbacks.remove(callback)
        }
    }

    fun unregisterAllActivityLifecycleCallback() {
        lifecycleCallbacks.forEach {
            mApplication?.unregisterActivityLifecycleCallbacks(it)
        }
        lifecycleCallbacks.clear()
    }

    class ActivityLifecycleImpl : ActivityLifecycleCallbacks {
        //不推荐使用这种方式去管理Activity。原因是，当我们finish一个activity时，onActivityDestroyed会最先触发，然后再去执行Activity的onDestroy,所以如果是
        //统一在这里去管理activity，会发现在activity的onDestroy中执行的时候，当前栈中的activity已经不存在了。
        var isForForeground: ((Boolean) -> Unit)? = null
        private var mForegroundCount = 0
        private var mConfigCount = 0
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
            if (mConfigCount < 0) {
                ++mConfigCount
            } else {
                ++mForegroundCount
            }
            isForForeground?.invoke((mForegroundCount != 0))
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
            if (activity.isChangingConfigurations) {
                --mConfigCount
            } else {
                --mForegroundCount
                isForForeground?.invoke(mForegroundCount > 0)
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        }
    }

}