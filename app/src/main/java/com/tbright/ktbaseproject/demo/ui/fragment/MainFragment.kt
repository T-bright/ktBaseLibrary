package com.tbright.ktbaseproject.demo.ui.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tbright.ktbaselibrary.extension.gone
import com.tbright.ktbaselibrary.mvp.BaseMvpFragment
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.R
import com.tbright.ktbaseproject.demo.ui.MainContract
import com.tbright.ktbaseproject.demo.ui.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainFragment : BaseMvpFragment<MainPresenter>() , MainContract.MainView {

    companion object{
        fun newInstance() = MainFragment()
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_main,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


    private fun requestPer() {
        checkPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO) {
            tvShow.text = if (it) "有权限" else "没有权限"
        }
    }

    override fun loginResult(result: String) {
        tvShow.text = result
    }
}