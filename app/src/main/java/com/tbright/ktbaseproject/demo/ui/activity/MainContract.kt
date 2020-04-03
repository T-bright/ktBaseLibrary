package com.tbright.ktbaseproject.demo.ui.activity

import com.tbright.ktbaselibrary.mvp.BaseModel
import com.tbright.ktbaselibrary.mvp.BasePresenter
import com.tbright.ktbaselibrary.mvp.BaseView

interface MainContract {

    abstract class MainPresenter : BasePresenter<BaseModel, MainView>(){

        abstract fun singlePoetry()

        //并行请求
        abstract fun parallelRequest (username : String, password : String)

        //并行请求
        abstract fun changeBaseUrl ()
    }

    interface MainView : BaseView{
        fun loginResult(result : String)
    }
}