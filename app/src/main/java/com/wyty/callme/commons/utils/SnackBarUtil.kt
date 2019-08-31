package com.wyty.callme.commons.utils

import android.app.Activity
import android.view.View
import com.wyty.callme.R
import com.wyty.callme.commons.view.Reminder

/**
 * Created by bdpqchen on 17-4-21.
 */

object SnackBarUtil {

    private val NORMAL_BG = R.color.reminderBgNormal
    private val NOTICE_BG = R.color.reminderBgNotice
    private val ERROR_BG = R.color.reminderBgError

    @JvmOverloads
    fun normal(act: Activity, m: String, isLongTime: Boolean = false) {
        show(act, m, isLongTime(isLongTime), NORMAL_BG)
    }


    @JvmOverloads
    fun notice(act: Activity, m: String, isLongTime: Boolean = false) {
        val duration = if (isLongTime) 1500 else 2000
        show(act, m, duration, NOTICE_BG)
    }

    @JvmOverloads
    fun error(act: Activity, m: String, isLongTime: Boolean = false) {
        show(act, m, isLongTime(isLongTime), ERROR_BG)
    }

    private fun show(
        act: Activity?,
        m: String,
        duration: Int,
        color: Int
    ) {
        if (act == null) return
        Reminder.Builder(act)
            .setMessage(m)
            .setDuration(duration)
            .setBackgroundColor(color)
            .show()
    }

    private fun isLongTime(isL: Boolean): Int {
        return if (isL) 3000 else 1000
    }

}
