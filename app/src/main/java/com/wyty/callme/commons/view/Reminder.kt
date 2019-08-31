package com.wyty.callme.commons.view

import android.app.Activity
import android.view.View
import android.view.ViewGroup

/**
 * Created by bdpqchen on 17-10-7.
 */

class Reminder {

    private var mActivity: Activity? = null
    private lateinit var mReminderView: ReminderView

    constructor() {}

    constructor(activity: Activity) {
        this.mActivity = activity
    }

    constructor(activity: Activity, params: Params) {
        this.mActivity = activity
        if (mActivity == null) return
        mReminderView = ReminderView(mActivity)
        mReminderView.setParams(params)
    }

    fun show() {
        if (Utils.notNull(mReminderView)) {
            val decorView = mActivity!!.window.decorView as ViewGroup
            //            final ViewGroup contentLayout = decorView.findViewById(android.R.id.content);
            if (!Utils.notNull(mReminderView.parent)) {
                decorView.addView(mReminderView)
            }
        }
    }

    class Builder(private val mActivity: Activity) {
        private val mParams = Params()

        fun setMessage(msg: String): Builder {
            mParams.message = msg
            return this
        }

        fun setMessageTextSize(textSize: Int): Builder {
            mParams.messageTextSize = textSize
            return this
        }

        fun setMessageTextColor(color: Int): Builder {
            mParams.messageTextColor = color
            return this
        }

        fun setAction(name: String, listener: OnActionClickListener): Builder {
            setAction(name, "", listener)
            return this
        }

        fun setAction(name: String, clickedName: String, listener: OnActionClickListener): Builder {
            mParams.actionName = name
            mParams.actionListener = listener
            mParams.clickedActionName = clickedName
            return this
        }

        fun setActionTextSize(textSize: Int): Builder {
            mParams.actionTextSize = textSize
            return this
        }

        fun setActionTextColor(color: Int): Builder {
            mParams.actionTextColor = color
            return this
        }

        fun setBackgroundColor(backgroundColor: Int): Builder {
            mParams.backgroundColor = backgroundColor
            return this
        }

        fun setDuration(duration: Int): Builder {
            mParams.duration = duration
            return this
        }

        private fun create(): Reminder {
            return Reminder(mActivity, mParams)
        }

        fun show() {
            create().show()
        }

    }

    class Params {
        var message: String? = null
        var actionName: String? = null
        var clickedActionName: String? = null
        var actionListener: OnActionClickListener? = null
        var messageTextSize: Int = 0
        var actionTextSize: Int = 0
        var backgroundColor: Int = 0
        var messageTextColor: Int = 0
        var actionTextColor: Int = 0
        var duration = 2000
    }

    interface OnActionClickListener : View.OnClickListener {
        override fun onClick(view: View)
    }

    companion object {

        private val TAG = "Reminder"
    }

}
