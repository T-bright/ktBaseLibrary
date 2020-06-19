package com.tbright.ktbaselibrary.utils.contentresolver

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 *
 */
object ContentResolverManager {

    /**
     * 每个页面可能会有多个注册,contentObserverMaps的key是类的全类名，value是当前页面的ContentObserver的集合
     */
    private var contentObserverMaps = hashMapOf<String, MutableList<ContentObserver>>()

    /**
     * 页面注解方法的集合类
     */
    private var methodMaps = hashMapOf<String, MutableList<ContentResolverMethod>>()

    /**
     * 注册。
     * @param any : 需要注册的页面
     */
    fun register(any: Any) {
        val key = any.javaClass.canonicalName
        if (!methodMaps.containsKey(key!!)) { //不包含，将注解方法放到集合中。包含就没必要重复放了。
            methodMaps[key] = findAnnotationMethod(any)
        }
    }

    /**
     *取消注册
     */
    fun unregister(any: Any) {
        var key = any.javaClass.canonicalName
        if (contentObserverMaps.containsKey(key!!)) {
            contentObserverMaps[key]?.let {
                it.forEach { contentObserver ->
                    when (any) {
                        is FragmentActivity -> {
                            any.contentResolver.unregisterContentObserver(contentObserver)
                        }
                        is Fragment -> {
                            any.context?.contentResolver?.unregisterContentObserver(contentObserver)
                        }
                        is Context -> {
                            any.contentResolver?.unregisterContentObserver(contentObserver)
                        }
                        is View -> {
                            any.context?.contentResolver?.unregisterContentObserver(contentObserver)
                        }
                    }
                }
                it.clear()
            }
            contentObserverMaps.remove(key)
        }

        if (methodMaps.containsKey(key)) {
            methodMaps.remove(key)
        }
    }


    private fun findAnnotationMethod(any: Any): MutableList<ContentResolverMethod> {
        var methodList = arrayListOf<ContentResolverMethod>()
        //首先获取类中的注解
        var clazz: Class<*>? = any.javaClass
        while (clazz != null) {
            val clazzName = clazz.name
            if (clazzName.startsWith("java.") || clazzName.startsWith("android.") || clazzName.startsWith("androidx.") || clazzName.startsWith("javax.")) {
                break
            }
            var methods = clazz.methods
            for (index in methods.indices) {
                val method = methods[index]
                val contentObserverUri: ContentObserverUri? = method.getAnnotation(ContentObserverUri::class.java)
                val contentObserverUriForChild: ContentObserverUriForChild? = method.getAnnotation(ContentObserverUriForChild::class.java)
                if (contentObserverUri == null && contentObserverUriForChild == null) {//方法上没有这两个注解，直接跳过
                    continue
                }
                val returnType = method.genericReturnType
                if ("void" != returnType.toString()) {
                    throw RuntimeException("${method.name}方法返回必须是void")
                }
                var parameterTypes = method.parameterTypes
                if (parameterTypes.size != 1) {
                    throw RuntimeException("参数必须含有一个参数")
                }
                if (contentObserverUri?.uri?.size == 0) {
                    throw RuntimeException("请添加需要注册的uri")
                }
                if (contentObserverUriForChild != null) {//方法上如果添加了ContentObserverUriForChild注解，就不考虑ContentObserverUri注解了
                    registerContentObserver(any, Uri.parse(contentObserverUriForChild.uri), contentObserverUriForChild.notifyForDescendants)
                    methodList.add(ContentResolverMethod(method,  contentObserverUriForChild.uri))
                } else {
                    contentObserverUri?.uri?.forEach {
                        registerContentObserver(any, Uri.parse(it), false)
                        methodList.add(ContentResolverMethod(method, it))
                    }
                }
            }
            clazz = clazz.superclass
        }
        return methodList
    }


    private fun registerContentObserver(any: Any, uri: Uri, notifyForDescendants: Boolean) {
        val key = any.javaClass.canonicalName
        val myContentObserver: MyContentObserver
        if (contentObserverMaps.containsKey(key!!)) { //如果当前页面不止一个注册的，扔到list集合里面
            myContentObserver = MyContentObserver(any, methodMaps)
            contentObserverMaps[key]?.add(myContentObserver)
        } else {
            val contentObservers = arrayListOf<ContentObserver>()
            myContentObserver = MyContentObserver(any, methodMaps)
            contentObservers.add(myContentObserver)
            contentObserverMaps[key] = contentObservers
        }
        when (any) {
            is FragmentActivity -> {
                any.contentResolver.registerContentObserver(uri, notifyForDescendants, myContentObserver)
            }
            is Fragment -> {
                any.context?.contentResolver?.registerContentObserver(uri, notifyForDescendants, myContentObserver)
            }
            is Context -> {
                any.contentResolver?.registerContentObserver(uri, notifyForDescendants, myContentObserver)
            }
            is View -> {
                any.context.contentResolver?.registerContentObserver(uri, notifyForDescendants, myContentObserver)
            }
        }
    }


    private class MyContentObserver(var any: Any, var methodMaps: MutableMap<String, MutableList<ContentResolverMethod>>, handler: Handler? = null) : ContentObserver(handler ?: Handler()) {
        var key = ""

        init {
            key = any.javaClass.canonicalName ?: ""
        }

        override fun onChange(selfChange: Boolean, uri: Uri) {
            super.onChange(selfChange, uri)
            methodMaps[key]?.forEach { contentResolverMethod ->
                if (uri.toString() == contentResolverMethod.uri) {
                    contentResolverMethod.method.invoke(any, uri.toString())
                }
            }
        }
    }
}