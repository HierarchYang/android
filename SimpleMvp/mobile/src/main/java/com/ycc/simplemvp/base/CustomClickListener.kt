package com.ycc.simplemvp.base

import android.view.View

import java.util.Calendar

/**
 * 需要过滤重复点击 1.0s内的点击无效
 * Created by issuser on 2017/12/29.
 */

abstract class CustomClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    private var lastView = 0

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
            lastView = v.id
        }
    }

    protected abstract fun onNoDoubleClick(v: View)

    companion object {

        private val MIN_CLICK_DELAY_TIME = 1000
        private var lastClickId = -1
    }
}
