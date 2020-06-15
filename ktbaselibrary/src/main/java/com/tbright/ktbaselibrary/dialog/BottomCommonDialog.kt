package com.tbright.ktbaselibrary.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.ScreenUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 从底部弹出的dialog。可动态设置高度
 */
open class BottomCommonDialog(@LayoutRes val contentViewId: Int) : BottomSheetDialogFragment() {

    // BottomSheetDialog的peek高度。默认是屏幕的3/4高度。单位px
    private var mPeekHeight : Int = (ScreenUtils.getScreenHeight() * 0.75).toInt()

    fun setPeekHeight(peekHeight : Int){
        mPeekHeight = peekHeight
    }

    protected open lateinit var mContentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (contentViewId != 0) {
            mContentView = inflater.inflate(contentViewId, container, false)
            return mContentView
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onResume() {
        super.onResume()
        setDialogPeekHeight(mPeekHeight)
    }

    /**
     * 默认是屏幕的3/4高度.
     * @param currentPeekHeight : 高度。这个高度的单位是 px
     */
    private fun setDialogPeekHeight(currentPeekHeight: Int) {
        val view = view
        view?.post {
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            val measureHeight = view.measuredHeight
            var bottomSheetHeight = ViewGroup.LayoutParams.WRAP_CONTENT
            if (measureHeight < currentPeekHeight) {
                bottomSheetBehavior!!.peekHeight = measureHeight
                bottomSheetHeight = measureHeight
            } else {
                bottomSheetBehavior!!.peekHeight = currentPeekHeight
                bottomSheetHeight = currentPeekHeight
            }
            val dialog = dialog as BottomSheetDialog
            if (dialog != null) {
                val bottomSheet = dialog.delegate.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet!!.layoutParams.height = bottomSheetHeight  //自定义高度
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    fun show(fragmentManager: FragmentManager, tag: String = "tag", result: BottomCommonDialog.() -> Unit?) {
        this.show(fragmentManager, tag)
        this.result()
    }
}