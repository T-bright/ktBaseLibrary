package com.tbright.ktbaseproject.demo.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.SizeUtils
import com.tbright.ktbaselibrary.dialog.BottomCommonDialog
import com.tbright.ktbaselibrary.dialog.CommonDialog
import com.tbright.ktbaselibrary.mvp.BaseMvpActivity
import com.tbright.ktbaselibrary.net.download.DownloadCallback
import com.tbright.ktbaselibrary.net.download.DownloadManager
import com.tbright.ktbaselibrary.net.download.DownloadTask
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.R
import com.tbright.ktbaseproject.demo.ui.fragment.MyFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_mian.*
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
            MyFragmentActivity.start(this,"修改前")
        }

        btGetNews.setOnClickListener {
            mPresenter?.singlePoetry()
        }

        btParallelRequest.setOnClickListener {
            mPresenter?.parallelRequest()
        }
        btChange.setOnClickListener {
            mPresenter?.changeBaseUrl()
        }
        btPermission.setOnClickListener {
            requestPer()
        }
        btDialog.setOnClickListener {
            CommonDialog(this,R.layout.dialog_mian).show {
                with(mContentView){//如果是自己自定义的布局可以使用这种方式。如果是框架内部请使用使用 message = "修改之后提示"
                    tvMainMessage.text = "修改之后提示"
                    tvMainIKnow.setOnClickListener {
                        dismiss()
                    }
                }
            }

            //这个用的是框架内部布局，不能使用上面那种方式。因为kotlin-android-extensions不支持
//            CommonDialog(this).show {
//                //如果是自己自定义的布局可以使用这种方式。如果是框架内部请使用使用 message = "修改之后提示"
//                message = "修改之后提示"
//                positiveClickListener {
//                    dismiss()
//                }
//            }
        }
        btBottomDialog.setOnClickListener {
            BottomCommonDialog(R.layout.bottom_dialog_mian).show(supportFragmentManager) {
                setPeekHeight(SizeUtils.dp2px(250f))
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
