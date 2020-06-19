package com.tbright.ktbaselibrary.utils.contentresolver

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentObserverUri(vararg val uri: String)//这个是默认的，都是精确匹配


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentObserverUriForChild(val uri: String, val notifyForDescendants: Boolean)//notifyForDescendants:false：表示精确匹配，即只匹配该Uri；true：表示可以同时匹配其派生的Uri