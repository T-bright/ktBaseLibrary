package com.tbright.ktbaselibrary.extension

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String?) {
    Glide.with(context).load(url).into(this)
}

//placeHolder
fun ImageView.loadHolder(imgUrl: String?, placeHolder: Int) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().placeholder(placeHolder))
        .into(this)
}

//加载图片 error
fun ImageView.loadErr(imgUrl: String, error: Int) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().error(error))
        .into(this)
}

//加载圆形图片
fun ImageView.loadCircle(imgUrl: String?, error: Int) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().circleCrop().error(error))
        .into(this)
}

//加载圆形图片
fun ImageView.loadCircle(imgUrl: Int) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().circleCrop())
        .into(this)
}

//
fun ImageView.loadHolderCircle(imgUrl: String, placeHolder: Int) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().circleCrop().placeholder(placeHolder))
        .into(this)
}

//placeHolder 占位符 error错误符
fun ImageView.loadHolderErr(imgUrl: String, placeHolder: Drawable, error: Drawable) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().placeholder(placeHolder).error(error))
        .into(this)
}


//加载圆角图片
fun ImageView.loadHolderRoundedCorner(imgUrl: String?, conner: Int, placeHolder: Int) {
    Glide.with(context)
        .load(imgUrl)
        .apply(RequestOptions().transform(RoundedCorners(conner)).placeholder(placeHolder))
        .into(this)
}

var View.isVisiable: Boolean
    get() {
        return visibility == View.VISIBLE
    }
    set(value) {
        if (value) {
            visibility = View.VISIBLE
        } else {
            visibility = View.INVISIBLE
        }
    }

var View.isGone: Boolean
    get() {
        return visibility == View.VISIBLE
    }
    set(value) {
        if (value) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
    }

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun PopupWindow.showDropDown(anchor: View, xoff: Int, yoff: Int) {
    if (Build.VERSION.SDK_INT >= 24) {
        val visibleFrame = Rect()
        anchor.getGlobalVisibleRect(visibleFrame)
        val height = anchor.resources.displayMetrics.heightPixels - visibleFrame.bottom
        this.height = height
        this.showAsDropDown(anchor, xoff, yoff)
    } else {
        this.showAsDropDown(anchor, xoff, yoff)
    }
}
