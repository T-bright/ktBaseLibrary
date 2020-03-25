package com.tbright.ktbaseproject.demo

import android.os.Bundle
import com.tbright.ktbaselibrary.mvp.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.MainView {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        btHttp.setOnClickListener {
            mPresenter?.login("x1", "123456")
        }
    }

    override fun loginResult(result: String) {
        tvShow.text = result
    }


}
