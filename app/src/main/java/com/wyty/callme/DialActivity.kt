package com.wyty.callme

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wyty.callme.commons.LinphoneService
import kotlinx.android.synthetic.main.activity_home.*
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.ProxyConfig
import org.linphone.core.RegistrationState
import org.linphone.core.tools.Log
import java.util.ArrayList

class DialActivity : AppCompatActivity() {

    lateinit var mCoreListener: CoreListenerStub
    lateinit var callButton: Button
    lateinit var mSipAddressToCall: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial)

        mCoreListener = object : CoreListenerStub() {
            override fun onRegistrationStateChanged(
                core: Core,
                cfg: ProxyConfig,
                state: RegistrationState,
                message: String
            ) {

            }
        }
        callButton.setOnClickListener {
            val core = LinphoneService.getCore()
            val addressToCall = core.interpretUrl(mSipAddressToCall.text.toString())
            val params = core.createCallParams(null)
            if (addressToCall != null) {
                core.inviteAddressWithParams(addressToCall, params)
            } else {
                Toast.makeText(this, "地址不能为空", Toast.LENGTH_LONG).show()

            }
        }

    }

    override fun onStart() {
        super.onStart()
        checkAndRequestCallPermissions()
    }


    override fun onPause() {
        super.onPause()
        LinphoneService.getCore().removeListener(mCoreListener)
    }

    private fun checkAndRequestCallPermissions() {
        val permissionsList = ArrayList<String>()

        // Some required permissions needs to be validated manually by the user
        // Here we ask for record audio and camera to be able to make video calls with sound
        // Once granted we don't have to ask them again, but if denied we can
        val recordAudio = packageManager
            .checkPermission(Manifest.permission.RECORD_AUDIO, packageName)
        Log.i(
            "[Permission] Record audio permission is " + if (recordAudio == PackageManager.PERMISSION_GRANTED)
                "granted"
            else
                "denied"
        )
        val camera = packageManager.checkPermission(Manifest.permission.CAMERA, packageName)
        Log.i(
            ("[Permission] Camera permission is " + (if (camera == PackageManager.PERMISSION_GRANTED) "granted" else "denied"))
        )

        if (recordAudio != PackageManager.PERMISSION_GRANTED) {
            Log.i("[Permission] Asking for record audio")
            permissionsList.add(Manifest.permission.RECORD_AUDIO)
        }

        if (camera != PackageManager.PERMISSION_GRANTED) {
            Log.i("[Permission] Asking for camera")
            permissionsList.add(Manifest.permission.CAMERA)
        }

        if (permissionsList.size > 0) {
            var permissions : Array<String> = permissionsList.toTypedArray()
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }
}
