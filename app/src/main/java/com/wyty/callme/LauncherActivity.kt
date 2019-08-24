package com.wyty.callme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.wyty.callme.commons.LinphoneService

class LauncherActivity : AppCompatActivity() {
    private lateinit var mHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        mHandler = Handler()
    }
    override fun onStart() {
        super.onStart()

        // Check whether the Service is already running
        if (LinphoneService.isReady()) {
            onServiceReady()
        } else {
            // If it's not, let's start it
            startService(
                Intent().setClass(this, LinphoneService::class.java)
            )
            // And wait for it to be ready, so we can safely use it afterwards
            ServiceWaitThread().start()
        }
    }

    private fun onServiceReady() {
        // Once the service is ready, we can move on in the application
        // We'll forward the intent action, type and extras so it can be handled
        // by the next activity if needed, it's not the launcher job to do that
        val intent = Intent()
        intent.setClass(this@LauncherActivity, LoginActivity::class.java)
        if (getIntent() != null && getIntent().extras != null) {
            intent.putExtras(getIntent().extras!!)
        }
        intent.action = getIntent().action
        intent.type = getIntent().type
        startActivity(intent)
    }

    // This thread will periodically check if the Service is ready, and then call onServiceReady
    private inner class ServiceWaitThread : Thread() {
        override fun run() {
            while (!LinphoneService.isReady()) {
                try {
                    sleep(30)
                } catch (e: InterruptedException) {
                    throw RuntimeException("waiting thread sleep() has been interrupted")
                }

            }
            // As we're in a thread, we can't do UI stuff in it, must post a runnable in UI thread
            mHandler.post { onServiceReady() }
        }
    }
}
