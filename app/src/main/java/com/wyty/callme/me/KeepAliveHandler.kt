package com.wyty.callme.me

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by tjwhm@TWTStudio at 2:17 AM, 2018/9/15.
 * Happy coding!
 */

class KeepAliveHandler : BroadcastReceiver() {

    companion object {
        private const val TAG = "KeepAliveHandler"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (LinphoneHelper.getSingleton().getLinphoneCoreIfManagerExists() != null) {
//            LinphoneManager.getLinphoneCore().refreshRegisters()
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "Cannot sleep for 2s")
            }
        }
    }
}