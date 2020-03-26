package com.tbright.ktbaselibrary.base


/**
 * 返回数据的基类。大部分后端返回的数据格式如下，区别就是json的key不一样。如：status 有的是 Int类型，有的可能是String类型。
 * 所以这里需要自己定义。如果是多个baseUrl的项目，后端返回的数据不一样，也需要自定定义。
 *  {
 *   "data":{
 *      "result":"请求成功"
 *   },
 *   "status":100,
 *   "message":"请求成功"
 *  }
 */
abstract class BaseResponse<T> {

    abstract fun getResponseMessage(): String?

    abstract fun getResponseData(): T?

    abstract fun isResponseSuccess(): Boolean
}
