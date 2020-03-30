package com.tbright.ktbaseproject.demo.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tbright.ktbaselibrary.base.BaseActivity
import com.tbright.ktbaselibrary.extension.addFragment
import com.tbright.ktbaseproject.demo.R

class MyFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_fragment
    }

    override fun initView(savedInstanceState: Bundle?) {
        addFragment(R.id.contain,MainFragment.newInstance())
    }

    override fun initData() {

    }
    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,MyFragmentActivity::class.java))
        }
    }
}
