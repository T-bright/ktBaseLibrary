package com.tbright.ktbaseproject.demo.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tbright.ktbaselibrary.base.BaseActivity
import com.tbright.ktbaselibrary.extension.addFragment
import com.tbright.ktbaselibrary.extension.extraDelegate
import com.tbright.ktbaselibrary.extension.showToast
import com.tbright.ktbaseproject.demo.R
import kotlinx.android.synthetic.main.activity_fragment.*

class MyFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_fragment
    }

    private var content by extraDelegate("content", "")

    override fun initView(savedInstanceState: Bundle?) {
        Log.e("MyFragmentActivity",content)
        addFragment(R.id.contain, MainFragment.newInstance())
        content = "修改后"
        Log.e("MyFragmentActivity",content)
    }

    override fun initData() {

    }

    companion object {
        fun start(context: Context, content: String) {
            context.startActivity(Intent(context, MyFragmentActivity::class.java).apply {
                putExtra("content", content)
            })
        }
    }
}
