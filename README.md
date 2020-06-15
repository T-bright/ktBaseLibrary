
# ktBaseLibrary

ktBaseLibrary是基于 MVP + Retrofit + 协程 的android快速开发框架。

之前一直使用rxjava + retrofit。在使用了kotlin之后，接触了协程，现在retrofit已经支持了协程，在使用retrofit+协程之后，果断的抛弃了rxjava。

之所以抛弃了rxjava，主要有以下两个方面：
> * 可以以同步的方式写出异步的代码。
> * 减少一个rxjava的依赖，可以减小apk的体积

下面是以同步方式写出异步代码的实例：
```kotlin
mainScope.launch {
    //网络请求
    val singlePoetry = ApiServices.instance.singlePoetry().response()
    
    //singlePoetry是网络请求的结果
    if (singlePoetry != null) {
        mView?.showResult(singlePoetry)
    }
    
    mView?.hideLoading()
}
```
以上代码简洁，逻辑清晰。


# 框架简介
框架主体使用 ``kotlin + mvp + retrofit + 协程`` 的模式。另外引用了一些好用的第三方库以及自己封装的一些基础库。具体如下图：
![image](https://github.com/T-bright/ktBaseLibrary/blob/master/pic/2.png?raw=true)

# 如何使用
## 1、依赖
在主项目app的build.gradle中依赖
```
dependencies {
    ...
   implementation 'com.tbright:ktbaselibrary:1.1.3'
}
```
在project的build.gradle中配置
```
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/tongsiwei49/ktbaselibrary/' }
    }
}
```

或者 下载到本地导入Module.

## 2、使用
### 2.1、配置http请求
需继承 ``HttpConfigProxy``，这里主要是统一处理错误的请求结果。下面是demo里面实现的一个，具体的大家可以根据自己的项目作修改，详细请看：
[HttpConfig](https://github.com/T-bright/ktBaseLibrary/blob/master/app/src/main/java/com/tbright/ktbaseproject/demo/customconfig/HttpConfig.kt/)
```
class HttpConfig : HttpConfigProxy() {

    private var mRetrofit: Retrofit? = null

    private var mRetrofitBuilder: Retrofit.Builder? = null

    private var mOkHttpClientBuilder: OkHttpClient.Builder? = null

    //
    override var baseUrl: String = mBaseUrl

    override var baseUrls: Map<String, String>
        set(value) {}
        get() {
            var urls = linkedMapOf<String, String>()
            if (urls.isNotEmpty()) return urls
            if (GlobalConfig.isDebug) {//可以在这里动态切换服务
                urls[BASE_URL] = mBaseUrl
                urls[GANK_URL] = mGankUrl
            } else {
                urls[BASE_URL] = mBaseUrl
                urls[GANK_URL] = mGankUrl
            }
            return urls
        }

    override suspend fun <T> parseResponseData(responseData: Deferred<BaseResponse<T>>): T? {
        try {
            var response = responseData.await()
            if (response.isResponseSuccess()) {
                return response.getResponseData()
            } else {
                MessageEvent( EVENTCODE_RESPONSE_FAIL,response.getResponseMessage()).send()
                return null
            }
        } catch (e: Throwable) {
            var errMsg = "网络异常"
            when (e) {
                is UnknownHostException -> errMsg = "连接失败"
                is ConnectException -> errMsg = "连接失败"
                is SocketTimeoutException -> errMsg = "连接超时"
                is InterruptedIOException -> errMsg = "连接中断"
                is SSLHandshakeException -> errMsg = "证书验证失败"
                is JSONException -> errMsg = "数据解析错误"
                is JsonSyntaxException -> errMsg = "数据解析错误"
                is NoNetworkException -> errMsg = "无可用网络"
                is HttpException -> {
                    when (e.code()) {
                        UNAUTHORIZED, FORBIDDEN -> {//这两个一般会要求重新登录
                            MessageEvent(EVENTCODE_RELOGIN,errMsg).send()
                            return null
                        }
                    }
                }
                else -> errMsg = e.message.toString()
            }
            MessageEvent(EVENTCODE_RESPONSE_FAIL, errMsg).send()
        }
        return null
    }

    override suspend fun <T> parseResponseWrapperData(responseData: Deferred<BaseResponse<T>>,vararg needDisposeError:Any): BaseResponse<T>? {
        try {
            var response = responseData.await()
            if (response.isResponseSuccess()) {
                return response
            } else {
                if(needDisposeError.contains(response.getResponseStatus())){//如果包含，不统一处理，在相应的页面特殊处理
                    return response
                }else{
                    MessageEvent( EVENTCODE_RESPONSE_FAIL,response.getResponseMessage()).send()
                    return null
                }
            }
        } catch (e: Throwable) {
            var errMsg = "网络异常"
            when (e) {
                is UnknownHostException -> errMsg = "连接失败"
                is ConnectException -> errMsg = "连接失败"
                is SocketTimeoutException -> errMsg = "连接超时"
                is InterruptedIOException -> errMsg = "连接中断"
                is SSLHandshakeException -> errMsg = "证书验证失败"
                is JSONException -> errMsg = "数据解析错误"
                is JsonSyntaxException -> errMsg = "数据解析错误"
                is NoNetworkException -> errMsg = "无可用网络"
                is HttpException -> {
                    when (e.code()) {
                        UNAUTHORIZED, FORBIDDEN -> {//这两个一般会要求重新登录
                            MessageEvent(EVENTCODE_RELOGIN,errMsg).send()
                            return null
                        }
                    }
                }
                else -> errMsg = e.message.toString()
            }
            MessageEvent(EVENTCODE_RESPONSE_FAIL, errMsg).send()
        }
        return null
    }


    override fun initRetrofit() {
        initClient()
        mRetrofitBuilder = Retrofit.Builder()
        mRetrofit = mRetrofitBuilder?.run {
            baseUrl(GlobalConfig.httpConfigProxy?.baseUrl ?: GlobalConfig.httpConfigProxy?.baseUrls!!.values.first())//如果项目就一个域名，可以直接使用baseUrl，baseUrls可以不用管
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(mOkHttpClientBuilder!!.build())
                .build()
            build()
        }
    }

    private var mRetrofitServices = hashMapOf<String, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T> create(clazz: Class<T>): T {
        var key = clazz.canonicalName
        var mRetrofitService = mRetrofitServices[key]
        if (mRetrofitService == null) {
            mRetrofitService = mRetrofit!!.create(clazz)
            mRetrofitServices[key!!] = mRetrofitService!!
        }
        return mRetrofitService as T
    }

    private fun initClient() {
        mOkHttpClientBuilder = OkHttpClient.Builder()
        mOkHttpClientBuilder?.run {
            if (GlobalConfig.isDebug) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                //设置 Debug Log 模式
                addInterceptor(loggingInterceptor)
            }
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)

            //错误重连
            retryOnConnectionFailure(true)
            addInterceptor(CacheInterceptor())
            addInterceptor(HeaderInterceptor())
            addInterceptor(MultiUrlInterceptor())

        }
    }
}
```

### 2.2、配置默认loading框
需继承 ``ShowUIProxy``。它的作用主要是统一显示请求网络是需要显示的loading框。下面是demo里面实现的一个，具体的大家可以根据自己的项目作修改，详细请看：
[ShowUIConfig](https://github.com/T-bright/ktBaseLibrary/blob/master/app/src/main/java/com/tbright/ktbaseproject/demo/customconfig/ShowUIConfig.kt/)
```
class ShowUIConfig : ShowUIProxy {
    override fun <T> parseResponseFailMessage(messageEvent: MessageEvent<T>) {
        when (messageEvent.code) {
            EVENTCODE_RESPONSE_FAIL -> {
                hideLoading()
                if (messageEvent.data != null) {
                    var errorMessage = messageEvent.data as String
                    showError(errorMessage)
                } else {
                    showError("网络错误")
                }
            }
            EVENTCODE_RELOGIN -> {//重新登录
                hideLoading()
                getTopActivity().reLogin()
            }
        }
    }

    override fun showLoading() {
        getTopActivity().showLoadingDialog()
    }

    override fun hideLoading() {
        getTopActivity().hideLoadingDialog()
    }

    override fun showError(errorMessage: String) {
        errorMessage.showToast()
    }
}
```
### 2.3、在Application中设置
这个是必须的
```
GlobalConfig.init(this,httpConfigProxy = HttpConfig(),showUIProxy = ShowUIConfig())
```

### 2.4、Activity
继承``BaseMvpActivity``
```
class MainActivity : BaseMvpActivity<MainPresenter>(),MainContract.MainView {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() { 
    
    }
}
```
当然如果页面很简单，没有什么逻辑要处理，也可以继承 ``BaseActivity`` 。
```
class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        
    }

    override fun initData() {
        
    }
}
```
### 2.5、Fragment
``Fragment`` 的使用方式和 ``Activity`` 差不多，继承 ``BaseMvpFragment``。
```
class MainFragment : BaseMvpFragment<MainFragmentPresenter>(),MainFragmentContract.MainView {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() { 
    
    }
    
}
```
当然也可以直接继承 ``BaseFragment`` ，与直接继承 ``Activity`` 一样。

### 2.6、Presenter和View
``Presenter`` 需继承 ``BasePresenter`` ,``View`` 需继承 ``BaseView``。下面是demo的契约类，可以参考：
```
interface MainContract {

    abstract class MainPresenter : BasePresenter<BaseModel, MainView>(){

        abstract fun singlePoetry()
        
        ...
    }

    interface MainView : BaseView{
        fun showResult(result : String)
    }
}
```

下面是MainPresenter的实例代码：
```
class MainPresenter : MainContract.MainPresenter() {

    override fun singlePoetry() {
        mView?.showLoading()
        mainScope.launch {
            //网络请求
            val singlePoetry = ApiServices.instance.singlePoetry().response()
    
            //singlePoetry是网络请求的结果
            if (singlePoetry != null) {
                mView?.showResult(singlePoetry)
            }
    
            mView?.hideLoading()
        }
    }
    
}
```
可以看见，网络请求的逻辑非常清晰。

具体的实例，可以参考demo。

# 关于框架
后续会进一步完善，欢迎大家多都提意见

QQ群（1105357684）

