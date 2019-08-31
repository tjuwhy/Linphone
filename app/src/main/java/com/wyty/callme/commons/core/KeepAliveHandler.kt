package com.wyty.callme.commons.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


/**
 * Created by Mark Xu on 17/3/13.
 * KeepAliveHandler
 */

class KeepAliveHandler : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (LinphoneService.getInstance() != null) {
            LinphoneService.getCore().refreshRegisters()
            //            SPUtils.save(context, "keepAlive", true);
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "Cannot sleep for 2s")
            }

        }
    }

    companion object {
        private val TAG = "KeepAliveHandler"
    }
}
