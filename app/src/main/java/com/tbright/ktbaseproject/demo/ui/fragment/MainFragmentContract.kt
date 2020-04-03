package com.tbright.ktbaseproject.demo.ui.fragment

import com.tbright.ktbaselibrary.mvp.BaseModel
import com.tbright.ktbaselibrary.mvp.BasePresenter
import com.tbright.ktbaselibrary.mvp.BaseView

interface MainFragmentContract {

    abstract class MainPresenter : BasePresenter<MainFragmentModel, MainView>(){

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