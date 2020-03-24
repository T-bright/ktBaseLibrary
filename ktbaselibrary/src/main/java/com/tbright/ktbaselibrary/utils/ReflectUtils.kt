package com.tbright.ktbaselibrary.utils

import java.lang.reflect.ParameterizedType

object ReflectUtils {

    @Suppress("UNCHECKED_CAST")
    fun <T> getObject(o: Any, i: Int): T? {
        try {
            return ((o.javaClass
                .genericSuperclass as ParameterizedType).actualTypeArguments[i] as Class<T>)
                .newInstance()
        } catch (e: InstantiationException) {
        } catch (e: IllegalAccessException) {
        } catch (e: ClassCastException) {
        }
        return null
    }
}