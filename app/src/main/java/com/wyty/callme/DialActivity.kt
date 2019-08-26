package com.wyty.callme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.utils.SnackBarUtil
import kotlinx.android.synthetic.main.activity_dial.*
import kotlinx.android.synthetic.main.activity_home.*
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.ProxyConfig
import org.linphone.core.RegistrationState
import org.linphone.core.tools.Log
import java.util.ArrayList

class DialActivity : AppCompatActivity() {

    lateinit var mCoreListener : CoreListenerStub

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
        btn.setOnClickListener {
            val core = LinphoneService.getCore()
            val addr = core.interpretUrl(edit.text.toString())
            val param = core.createCallParams(null)

            param.enableVideo(checkbox.isChecked)

            if (addr!=null){
                core.inviteAddressWithParams(addr,param)
            } else {
                SnackBarUtil.error(this@DialActivity,"请检查对方地址是否输入正确")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkAndRequestCallPermissions()
    }

    override fun onResume() {
        super.onResume()

        // The best way to use Core listeners in Activities is to add them in onResume
        // and to remove them in onPause
        LinphoneService.getCore().addListener(mCoreListener)

        // Manually update the LED registration state, in case it has been registered before
        // we add a chance to register the above listener
        val proxyConfig = LinphoneService.getCore().defaultProxyConfig
        if (proxyConfig != null) {
        } else {
            // No account configured, we display the configuration activity
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()

        // Like I said above, remove unused Core listeners in onPause
        LinphoneService.getCore().removeListener(mCoreListener)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        // Callback for when permissions are asked to the user
        for (i in permissions.indices) {
            Log.i(
                "[Permission] "
                        + permissions[i]
                        + " is "
                        + if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    "granted"
                else
                    "denied"
            )
        }
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
            var permissions = permissionsList.toTypedArray()
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
    }
}
