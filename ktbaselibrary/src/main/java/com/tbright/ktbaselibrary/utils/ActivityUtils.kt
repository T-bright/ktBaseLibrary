package com.tbright.ktbaselibrary.utils

import android.app.Activity

fun getActivityLists(): List<Activity> {
    return AppUtils.activityLifecycleImpl.mActivityList
}

fun getTopActivity(): Activity {
    return AppUtils.activityLifecycleImpl.mActivityList.last
}

fun isActivityExistInStack(activity: Activity): Boolean {
    return AppUtils.activityLifecycleImpl.mActivityList.contains(activity)
}

fun finishActivities(vararg clazzs: Class<Activity>) {
    AppUtils.activityLifecycleImpl.mActivityList.forEach {
        if (clazzs.contains(it::class.java)) {
            it.finish()
        }
    }
}

fun finishOtherActivities(vararg clazzs: Class<Activity>) {
    AppUtils.activityLifecycleImpl.mActivityList.forEach {
        if (!clazzs.contains(it::class.java)) {
            it.finish()
        }
    }
}

fun finishAllActivities() {
    AppUtils.activityLifecycleImpl.mActivityList.forEach {
        it.finish()
    }
}

fun isForForeground(isForForeground: ((Boolean) -> Unit)? = null) {
    AppUtils.activityLifecycleImpl.isForForeground = isForForeground
}