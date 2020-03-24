package com.tbright.ktbaselibrary.utils

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import java.util.*

object AppUtils {
    var mApplication: Application? = null
    var activityLifecycleImpl: ActivityLifecycleImpl = ActivityLifecycleImpl()
    fun init(application: Application) {
        this.mApplication = application
        application.registerActivityLifecycleCallbacks(activityLifecycleImpl)
    }


    class ActivityLifecycleImpl : ActivityLifecycleCallbacks {
        val mActivityList = LinkedList<Activity>()
        var isForForeground: ((Boolean) -> Unit)? = null
        private var mForegroundCount = 0
        private var mConfigCount = 0
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            addActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            addActivity(activity)
            if (mConfigCount < 0) {
                ++mConfigCount
            } else {
                ++mForegroundCount
            }
            isForForeground?.invoke((mForegroundCount != 0))
        }

        override fun onActivityResumed(activity: Activity) {
            addActivity(activity)
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
            mActivityList.remove(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

        }


        fun addActivity(activity: Activity) {
            if (mActivityList.contains(activity) && mActivityList.last != activity) {
                mActivityList.remove(activity)
                mActivityList.addLast(activity)
            } else if (!mActivityList.contains(activity)) {
                mActivityList.addLast(activity)
            }
        }
    }

}