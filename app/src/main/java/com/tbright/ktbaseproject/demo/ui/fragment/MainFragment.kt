package com.tbright.ktbaseproject.demo.ui.fragment

import android.Manifest
import android.os.Bundle
import com.blankj.utilcode.util.SizeUtils
import com.tbright.ktbaselibrary.dialog.BottomCommonDialog
import com.tbright.ktbaselibrary.dialog.CommonDialog
import com.tbright.ktbaselibrary.extension.gone
import com.tbright.ktbaselibrary.mvp.BaseMvpFragment
import com.tbright.ktbaselibrary.utils.permission.checkPermissions
import com.tbright.ktbaseproject.demo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_mian.*

class MainFragment : BaseMvpFragment<MainFragmentPresenter>(), MainFragmentContract.MainView {

    companion object {
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
            mPresenter?.parallelRequest()
        }
        btChange.setOnClickListener {
            mPresenter?.changeBaseUrl()
        }
        btPermission.setOnClickListener {
            requestPer()
        }
        btDialog.setOnClickListener {
            CommonDialog(activity!!, R.layout.dialog_mian).show {
                with(mContentView) { //如果是自己自定义的布局可以使用这种方式。如果是框架内部请使用使用 message = "修改之后提示"
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
            BottomCommonDialog(R.layout.bottom_dialog_mian).show(childFragmentManager) {
                setPeekHeight(SizeUtils.dp2px(250f))
            }
        }
    }

    override fun initData() {

    }


    private fun requestPer() {
        checkPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO) {
            tvShow.text = if (it) "有权限" else "没有权限"
        }
    }

    override fun showResult(result: String) {
        tvShow.text = result
    }
}