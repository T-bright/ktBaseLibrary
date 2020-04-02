package com.tbright.ktbaselibrary.utils

import android.app.Activity
import java.util.*

object ActivityUtils {
    private val mActivityList = LinkedList<Activity>()

    fun addActivity(activity: Activity) {
        if (mActivityList.contains(activity) && mActivityList.last != activity) {
            mActivityList.remove(activity)
            mActivityList.addLast(activity)
        } else if (!mActivityList.contains(activity)) {
            mActivityList.addLast(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        mActivityList.remove(activity)
    }

    fun getActivityLists(): List<Activity> {
        return mActivityList
    }

    fun getTopActivity(): Activity {
        return mActivityList.last
    }

    fun isActivityExistInStack(activity: Activity): Boolean {
        return mActivityList.contains(activity)
    }

    fun finishActivities(vararg clazzs: Class<Activity>) {
        mActivityList.forEach {
            if (clazzs.contains(it::class.java)) {
                it.finish()
            }
        }
    }

    fun finishOtherActivities(vararg clazzs: Class<Activity>) {
        mActivityList.forEach {
            if (!clazzs.contains(it::class.java)) {
                it.finish()
            }
        }
    }

    fun finishAllActivities() {
        mActivityList.forEach {
            it.finish()
        }
    }

    fun isForForeground(isForForeground: ((Boolean) -> Unit)? = null) {
        AppUtils.activityLifecycleImpl.isForForeground = isForForeground
    }
}