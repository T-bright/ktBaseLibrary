package com.tbright.ktbaselibrary.widget

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.tbright.ktbaselibrary.R

class LoadingDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        val windowParams = dialog?.window?.attributes
        windowParams?.dimAmount = 0.0f
        dialog?.window?.attributes = windowParams
    }
}