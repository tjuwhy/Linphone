package com.wyty.callme

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.Constants.CALL
import com.wyty.callme.commons.utils.SnackBarUtil
import kotlinx.android.synthetic.main.activity_voice.*
import org.linphone.core.Call
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.tools.Log
import org.linphone.mediastream.Version

class VoiceActivity : AppCompatActivity() {
    lateinit var mCoreListener: CoreListenerStub


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)
        val core = LinphoneService.getCore()
        val videoEnabled = intent.getBooleanExtra(CALL, false)
        mCoreListener = object : CoreListenerStub() {
            override fun onCallStateChanged(
                core: Core,
                call: Call,
                state: Call.State,
                message: String
            ) {
                if (state == Call.State.Connected){
                    if (!call.remoteParams.videoEnabled()){
                        call_incoming.visibility= View.GONE
                        voice_connected.visibility = View.VISIBLE
                    }
                }
                if (state == Call.State.End || state == Call.State.Released) {
                    finish()
                }
            }
        }
        voice_connect.setOnClickListener {
            val cores = LinphoneService.getCore()
            if (cores.callsNb > 0) {
                var call: Call? = cores.currentCall
                if (call == null) {
                    call = cores.calls[0]
                }
                val params = LinphoneService.getCore().createCallParams(call)
                params.enableVideo(false);
                call?.acceptWithParams(params)///jieting

            }

        }
        voice_terminate.setOnClickListener {
            val cores = LinphoneService.getCore()
            if (cores.callsNb > 0) {
                var call: Call? = cores.currentCall
                if (call == null) {
                    call = cores.calls[0]
                }
                call?.terminate()
            }
            finish()
        }
        on_terminate.setOnClickListener {
            val cores = LinphoneService.getCore()
            if (cores.callsNb > 0) {
                var call: Call? = cores.currentCall
                if (call == null) {
                    call = cores.calls[0]
                }
                call?.terminate()
            }
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        LinphoneService.getCore().addListener(mCoreListener)
    }

    override fun onPause() {
        LinphoneService.getCore().removeListener(mCoreListener)
        super.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onUserLeaveHint() {
        val supportsPip = packageManager
            .hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        Log.i("[Call] Is picture in picture supported: $supportsPip")
        if (supportsPip && Version.sdkAboveOrEqual(24)) {
//            enterPictureInPictureMode()
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        if (isInPictureInPictureMode) {
            // Currently nothing to do has we only display video
            // But if we had controls or other UI elements we should hide them
        } else {
            // If we did hide something, let's make them visible again
        }
    }



}

