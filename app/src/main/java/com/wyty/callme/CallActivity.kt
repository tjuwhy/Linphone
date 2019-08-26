package com.wyty.callme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.TextureView
import android.widget.RelativeLayout
import com.wyty.callme.commons.LinphoneService
import kotlinx.android.synthetic.main.activity_call.*
import org.linphone.core.Call
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.tools.Log

class CallActivity : AppCompatActivity() {

    lateinit var mVideoView: TextureView
    lateinit var mCaptureView: TextureView

    private lateinit var mCoreListenerStub: CoreListenerStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
        mVideoView = videoSurface
        mCaptureView = videoCaptureSurface

        val core = LinphoneService.getCore()
        core.apply {
            setNativeVideoWindowId(mVideoView)
            setNativePreviewWindowId(mCaptureView)
            terminate_call.setOnClickListener {
                if (core.callsNb > 0) {
                    var call: Call? = core.currentCall
                    if (call == null) {
                        // Current call can be null if paused for example
                        call = core.calls[0]
                    }
                    call!!.terminate()
                }
            }

        }
        mCoreListenerStub = object : CoreListenerStub() {
            override fun onCallStateChanged(core: Core?, call: Call?, state: Call.State?, message: String?) {
                if (state == Call.State.End || state == Call.State.Released) {
                    // Once call is finished (end state), terminate the activity
                    // We also check for released state (called a few seconds later) just in case
                    // we missed the first one
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        LinphoneService.getCore().addListener(mCoreListenerStub)
        resizePreview()
    }

    override fun onPause() {
        LinphoneService.getCore().removeListener(mCoreListenerStub)
        super.onPause()

    }

    private fun resizePreview() {
        val core = LinphoneService.getCore()
        if (core.getCallsNb() > 0) {
            var call: Call? = core.getCurrentCall()
            if (call == null) {
                call = core.getCalls()[0]
            }
            if (call == null) return

            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val screenHeight = metrics.heightPixels
            val maxHeight = screenHeight / 4 // Let's take at most 1/4 of the screen for the camera preview

            var videoSize = call.currentParams
                .sentVideoDefinition // It already takes care of rotation
            if (videoSize.width == 0 || videoSize.height == 0) {
                Log.w(
                    "[Video] Couldn't get sent video definition, using default video definition"
                )
                videoSize = core.getPreferredVideoDefinition()
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
            mCaptureView.setLayoutParams(newLp)
            Log.d("[Video] Video preview size set to " + width + "x" + height)
        }
    }
}
