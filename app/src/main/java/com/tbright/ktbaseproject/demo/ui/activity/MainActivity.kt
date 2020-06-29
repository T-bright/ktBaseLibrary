package com.tbright.ktbaseproject.demo.ui.activity

import android.Manifest
import android.net.Uri
import android.os.Bundle
import com.blankj.utilcode.util.SizeUtils
import com.tbright.ktbaselibrary.dialog.BottomCommonDialog
import com.tbright.ktbaselibrary.dialog.CommonDialog
import com.tbright.ktbaselibrary.extension.showToast
import com.tbright.ktbaselibrary.mvp.BaseMvpActivity
import com.tbright.ktbaselibrary.utils.contentresolver.ContentObserverUri
import com.tbright.ktbaselibrary.utils.contentresolver.ContentResolverManager
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.R
import com.tbright.ktbaseproject.demo.ui.TestProvider
import com.tbright.ktbaseproject.demo.ui.fragment.MyFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_mian.*


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

        //注册内容观察者
        ContentResolverManager.register(this)
        contentResolverManager.setOnClickListener {
            //向内容提供者里面插入一条数据
            contentResolver.insert(Uri.parse(TestProvider.TEST_URI),null)
        }
    }


    //当观察的uri发生了变化，此方法会被触发
    @ContentObserverUri(TestProvider.TEST_URI)//在注解上填写需要观察的uri
    fun contentResolver(uri : String){
        when(uri){
            TestProvider.TEST_URI->{
                var result = ""
                var cursor = contentResolver.query(Uri.parse(TestProvider.TEST_URI),null,null,null,null)
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        result = cursor.getString(0)
                    }
                }
                result.showToast()
            }
        }
    }

    private fun requestPer() {
        checkPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO) {
            tvShow.text = if (it) "有权限" else "没有权限"
        }
    }

    override fun showResult(result: String) {
        tvShow.text = result
    }

    override fun onDestroy() {
        super.onDestroy()
        ContentResolverManager.unregister(this)
    }

}
