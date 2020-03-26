package com.tbright.ktbaseproject.demo.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.BarUtils
import com.tbright.ktbaselibrary.mvp.BaseMvpActivity
import com.tbright.ktbaseproject.demo.GlobalConstants
import com.tbright.ktbaseproject.demo.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainPresenter>(),
    MainContract.MainView {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        btLogin.setOnClickListener {
            mPresenter?.login("x1", "123456")
        }
        btUser.setOnClickListener {
            mPresenter?.getUserInfo(GlobalConstants.token?.userId?:"")
        }
        btParallelRequest.setOnClickListener {
            mPresenter?.parallelRequest("x1", "123456")
        }
        btChange.setOnClickListener {
            mPresenter?.changeBaseUrl()
        }
    }

    override fun loginResult(result: String) {
        tvShow.text = result
    }


}
