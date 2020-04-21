package com.tbright.ktbaseproject.demo.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
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
                if (it) {
                    download()
                }
            }
        }
        btCancelDownLoad.setOnClickListener {
            downloadManager!!.cancel(task4!!)
        }
    }

    var task4: DownloadTask? = null
    var downloadManager: DownloadManager? = null
    private fun download() {
        var url1 = "http://cc-download.edrawsoft.cn/inst/mindmaster7_cn_setup_full5375.exe"
        var url2 = "https://fga1.market.xiaomi.com/download/AppStore/069f944844904eddc7dd542b7c1147cc55e43da3a/com.quxiangtou.release.apk"
        var url3 = "https://fga1.market.xiaomi.com/download/AppStore/0ec4845db86df49773326cd5ade2b5e34c340f746/com.xiaomi.o2o.apk"
        var url4 = "https://fga1.market.xiaomi.com/download/AppStore/02e954531fe4b44a5145d31e2d305e83d5faab68c/com.qts.customer.apk"
        var url5 = "https://fga1.market.xiaomi.com/download/AppStore/07e1151452d65f3ab40a8f310c571f5e5ad41708b/com.dewmobile.kuaiya.apk"
        var url6 = "https://fga1.market.xiaomi.com/download/AppStore/092a2b4fb406e42c803e6f54c40a00539d15564d8/com.jiaming.qiming.apk"
        var url7 = "https://fga1.market.xiaomi.com/download/AppStore/03f345d8c8784eeab9f5acde850e1355b9143f7bd/com.weichong.handline.apk"
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

        var task1 = DownloadTask.Build(url1, file1).setPriority(1).build()
        var task2 = DownloadTask.Build(url2, file3).setPriority(3).build()
        var task3 = DownloadTask.Build(url3, file2).setPriority(2).build()
        task4 = DownloadTask.Build(url4, file7).setPriority(7).build()
        var task5 = DownloadTask.Build(url5, file4).setPriority(4).build()
        var task6 = DownloadTask.Build(url6, file5).setPriority(5).build()
        var task7 = DownloadTask.Build(url7, file6).setPriority(6).build()
        downloadManager = DownloadManager()
        downloadManager!!.setMaxDownloadCount(3).addTask(arrayListOf<DownloadTask>(task1, task2, task3, task4!!, task5, task6, task7), object : DownloadCallback {
            override fun started(task: DownloadTask) {
                Log.e("AAA", "started  priority :${task.priority} -- thread : ${Thread.currentThread().name}")
            }

            override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {
                Log.e("BBB", "progress  priority :${task.priority} -- thread : ${Thread.currentThread().name}")
            }

            override fun canceled(task: DownloadTask) {
                Log.e("AAA", "canceled  priority :${task.priority} -- thread : ${Thread.currentThread().name}")
            }

            override fun error(task: DownloadTask, e: Exception) {

            }

            override fun completed(task: DownloadTask) {
                Log.e("AAA", "completed  priority :${task.priority} -- thread : ${Thread.currentThread().name}")
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
