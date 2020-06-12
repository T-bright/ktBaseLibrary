package com.tbright.ktbaseproject.demo.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.FileUtils
import com.tbright.ktbaselibrary.dialog.CommonDialog
import com.tbright.ktbaselibrary.mvp.BaseMvpActivity
import com.tbright.ktbaselibrary.net.download.DownloadCallback
import com.tbright.ktbaselibrary.net.download.DownloadManager
import com.tbright.ktbaselibrary.net.download.DownloadTask
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.R
import com.tbright.ktbaseproject.demo.ui.fragment.MyFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.Exception


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
        btDialog.setOnClickListener {
            CommonDialog(this).show {

            }
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
