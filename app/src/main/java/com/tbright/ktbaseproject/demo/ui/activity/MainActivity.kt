package com.tbright.ktbaseproject.demo.ui.activity

import android.Manifest
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
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
        btDownLoad.setOnClickListener {
            checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE) {
               if(it){
                   download()
               }
            }
        }
    }

    private fun download() {
        var url = "http://cc-download.edrawsoft.cn/inst/mindmaster7_cn_setup_full5375.exe"
        url = "https://fga1.market.xiaomi.com/download/AppStore/069f944844904eddc7dd542b7c1147cc55e43da3a/com.quxiangtou.release.apk"
        var file1 = File("${cacheDir.absolutePath}/1.exe")
        FileUtils.createOrExistsFile(file1)

        var file2 = File("${cacheDir.absolutePath}/2.exe")
        FileUtils.createOrExistsFile(file2)

        var file3 = File("${cacheDir.absolutePath}/3.exe")
        FileUtils.createOrExistsFile(file3)

        var file4 = File("${cacheDir.absolutePath}/4.exe")
        FileUtils.createOrExistsFile(file4)

        var file5 = File("${cacheDir.absolutePath}/5.exe")
        FileUtils.createOrExistsFile(file5)

        var file6 = File("${cacheDir.absolutePath}/6.exe")
        FileUtils.createOrExistsFile(file6)

        var file7 = File("${cacheDir.absolutePath}/7.exe")
        FileUtils.createOrExistsFile(file7)

        var task1 = DownloadTask.Build(url, file1).setPriority(1).build()
        var task2 = DownloadTask.Build(url, file3).setPriority(3).build()
        var task3 = DownloadTask.Build(url, file2).setPriority(2).build()
        var task4 = DownloadTask.Build(url, file7).setPriority(7).build()
        var task5 = DownloadTask.Build(url, file4).setPriority(4).build()
        var task6 = DownloadTask.Build(url, file5).setPriority(5).build()
        var task7 = DownloadTask.Build(url, file6).setPriority(6).build()
        DownloadManager().setMaxDownloadCount(3).addTask(arrayListOf<DownloadTask>(task1, task2, task3, task4, task5, task6, task7), object : DownloadCallback {
            override fun started(task: DownloadTask) {

            }

            override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {

            }

            override fun canceled(task: DownloadTask) {

            }

            override fun error(task: DownloadTask, e: Exception) {

            }

            override fun completed(task: DownloadTask) {

            }
        })
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
