package com.tbright.ktbaseproject.demo.ui.fragment

import android.Manifest
import android.os.Bundle
import com.tbright.ktbaselibrary.extension.gone
import com.tbright.ktbaselibrary.mvp.BaseMvpFragment
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainFragment : BaseMvpFragment<MainFragmentPresenter>() , MainFragmentContract.MainView {

    companion object{
        fun newInstance() = MainFragment()
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        btGotoFragment.gone()
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

    override fun initData() {

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