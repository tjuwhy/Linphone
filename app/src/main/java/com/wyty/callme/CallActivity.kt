package com.wyty.callme

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.DisplayMetrics
import android.view.TextureView
import android.widget.RelativeLayout
import com.wyty.callme.commons.core.LinphoneService
import kotlinx.android.synthetic.main.activity_call.*
import org.linphone.core.Call
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.tools.Log
import org.linphone.mediastream.Version

class CallActivity : AppCompatActivity() {
    lateinit var mVideoView: TextureView
    lateinit var mCaptureView: TextureView
    lateinit var mCoreListener: CoreListenerStub


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
        mVideoView = videoSurface
        mCaptureView = videoCaptureSurface
        val core = com.wyty.callme.commons.core.LinphoneService.getCore()
        core.setNativeVideoWindowId(mVideoView)
        core.setNativePreviewWindowId(mCaptureView)
        mCoreListener = object : CoreListenerStub() {
            override fun onCallStateChanged(
                core: Core,
                call: Call,
                state: Call.State,
                message: String
            ) {
                if (state == Call.State.End || state == Call.State.Released) {
                    finish()
                }
            }
        }

        terminate_call.setOnClickListener {
            val cores = com.wyty.callme.commons.core.LinphoneService.getCore()
            if (cores.callsNb > 0) {
                var call: Call? = cores.currentCall
                if (call == null) {
                    call = cores.calls[0]
                }
                call?.terminate()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        com.wyty.callme.commons.core.LinphoneService.getCore().addListener(mCoreListener)
        resizePreview()
    }


    override fun onPause() {
        com.wyty.callme.commons.core.LinphoneService.getCore().removeListener(mCoreListener)
        super.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onUserLeaveHint() {
        val supportsPip = packageManager
            .hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        Log.i("[Call] Is picture in picture supported: $supportsPip")
        if (supportsPip && Version.sdkAboveOrEqual(24)) {
            enterPictureInPictureMode()
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


    private fun resizePreview() {
        val core = com.wyty.callme.commons.core.LinphoneService.getCore()
        if (core.callsNb > 0) {
            var call: Call? = core.currentCall
            if (call == null) {
                call = core.calls[0]
            }
            if (call == null) return

            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val screenHeight = metrics.heightPixels
            val maxHeight =
                screenHeight / 4 // Let's take at most 1/4 of the screen for the camera preview

            var videoSize = call.currentParams
                .sentVideoDefinition // It already takes care of rotation
            if (videoSize.width == 0 || videoSize.height == 0) {
                Log.w(
                    "[Video] Couldn't get sent video definition, using default video definition"
                )
                videoSize = core.preferredVideoDefinition
            }
            var width = videoSize.width
            var height = videoSize.height

            Log.d("[Video] Video height is $height, width is $width")
            width = width * maxHeight / height
            height = maxHeight

            if (mCaptureView == null) {
                Log.e("[Video] mCaptureView is null !")
                return
            }

            val newLp = RelativeLayout.LayoutParams(width, height)
            newLp.addRule(
                RelativeLayout.ALIGN_PARENT_BOTTOM,
                1
            ) // Clears the rule, as there is no removeRule until API 17.
            newLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
            mCaptureView.layoutParams = newLp
            Log.d("[Video] Video preview size set to " + width + "x" + height)
        }
    }
}
