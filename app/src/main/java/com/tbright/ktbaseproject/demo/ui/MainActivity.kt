package com.tbright.ktbaseproject.demo.ui

import android.Manifest
import android.os.Bundle
import com.tbright.ktbaselibrary.mvp.BaseMvpActivity
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.GlobalConstants
import com.tbright.ktbaseproject.demo.R
import com.tbright.ktbaseproject.demo.ui.fragment.MyFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainPresenter>(),
    MainContract.MainView {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        btGotoFragment.setOnClickListener {
            MyFragmentActivity.start(this)
        }

        btGetNews.setOnClickListener {
            mPresenter?.singlePoetry()
        }

        btParallelRequest.setOnClickListener {
            mPresenter?.parallelRequest("x1", "123456")
        }
        btChange.setOnClickListener {
            mPresenter?.changeBaseUrl()
        }
        btPermission.setOnClickListener {
            requestPer()
        }
    }

    private fun requestPer() {
        checkPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO) {
            tvShow.text = if (it) "有权限" else "没有权限"
        }
    }

    override fun loginResult(result: String) {
        tvShow.text = result
    }


}
