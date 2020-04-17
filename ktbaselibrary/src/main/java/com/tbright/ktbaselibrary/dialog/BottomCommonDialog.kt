package com.tbright.ktbaselibrary.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BottomCommonDialog(val contentViewId: Int) : BottomSheetDialogFragment() {

    private var peekHeight = ScreenUtils.getScreenHeight() * 0.75f

    protected open lateinit var mContentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (contentViewId != 0) {
            mContentView = inflater.inflate(contentViewId, container, false)
            return mContentView
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        setPeekHeight()
    }

    open fun setPeekHeight() {
        setPeekHeight(peekHeight)
    }

    open fun setPeekHeight(currentPeekHeight: Float) {
        val view = view
        view!!.post {
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            val measureHeight = view.measuredHeight
            var bottomSheetHeight = ViewGroup.LayoutParams.WRAP_CONTENT
            if (measureHeight < SizeUtils.dp2px(currentPeekHeight)) {
                bottomSheetBehavior!!.peekHeight = measureHeight
                bottomSheetHeight = measureHeight
            } else {
                bottomSheetBehavior!!.peekHeight = SizeUtils.dp2px(currentPeekHeight)
                bottomSheetHeight = SizeUtils.dp2px(currentPeekHeight)
            }
            val dialog = dialog as BottomSheetDialog
            if (dialog != null) {
                val bottomSheet = dialog.delegate.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet!!.layoutParams.height = bottomSheetHeight  //自定义高度
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }


    private var result: ((mContentView: View, dialog: BottomCommonDialog) -> Unit)? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result?.invoke(mContentView, this)
    }

    fun show(fragmentManager: FragmentManager, tag: String = "tag", result: ((mContentView: View, dialog: BottomCommonDialog) -> Unit)?) {
        this.show(fragmentManager, tag)
        this.result = result
    }
}