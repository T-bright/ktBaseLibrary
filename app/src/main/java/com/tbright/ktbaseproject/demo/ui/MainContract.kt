package com.tbright.ktbaseproject.demo.ui

import com.tbright.ktbaselibrary.mvp.BaseModel
import com.tbright.ktbaselibrary.mvp.BasePresenter
import com.tbright.ktbaselibrary.mvp.BaseView

interface MainContract {

    abstract class MainPresenter : BasePresenter<BaseModel, MainView>(){
        abstract fun login(username : String, password : String)

        abstract fun getUserInfo(userId : String)
    }

    interface MainView : BaseView{
        fun loginResult(result : String)
    }
}