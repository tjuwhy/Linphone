package com.wyty.callme.commons.view

import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.TypedValue

/**
 * Created by bdpqchen on 17-10-7.
 */

object Utils {

    fun notNull(i: Int): Boolean {
        return i != 0
    }

    fun notNull(str: String): Boolean {
        return !TextUtils.isEmpty(str)
    }

    fun notNull(`object`: Any?): Boolean {
        return `object` != null
    }

    fun getMinHeight(context: Context): Int {
        return getActionBarHeight(context) + getStatusBarHeight(context)
    }

    private fun getActionBarHeight(context: Context): Int {
        val tv = TypedValue()
        var height = 0
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            height = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        }
        //        Log.d("height action bar", String.valueOf(height));
        return height
    }

    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        //        Log.d("height status bar", String.valueOf(statusBarHeight));
        return statusBarHeight
    }

    fun getColor(context: Context, color: Int): Int {
        return ContextCompat.getColor(context, color)
    }


}
