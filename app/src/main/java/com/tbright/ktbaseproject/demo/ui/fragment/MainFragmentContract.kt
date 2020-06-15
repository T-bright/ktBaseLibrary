package com.tbright.ktbaseproject.demo.ui.fragment

import com.tbright.ktbaselibrary.mvp.BasePresenter
import com.tbright.ktbaselibrary.mvp.BaseView

interface MainFragmentContract {

    abstract class MainPresenter : BasePresenter<MainFragmentModel, MainView>(){

        abstract fun singlePoetry()

        //并行请求
        abstract fun parallelRequest ()

        //并行请求
        abstract fun changeBaseUrl ()
    }

    interface MainView : BaseView{
        fun showResult(result : String)
    }
}