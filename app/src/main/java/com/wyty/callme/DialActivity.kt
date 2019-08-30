package com.wyty.callme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Toast
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.utils.SnackBarUtil
import com.wyty.callme.dial.DialNumAdapter
import kotlinx.android.synthetic.main.activity_dial.*
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.ProxyConfig
import org.linphone.core.RegistrationState
import org.linphone.core.tools.Log
import java.util.*

class DialActivity : AppCompatActivity() {

    lateinit var mCoreListener : CoreListenerStub
    private var phoneNumber: MutableList<String> = mutableListOf<String>()
    private lateinit var adapter: DialNumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial)
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()
        edit.setSelection(edit.length())

        initValues()
        adapter = DialNumAdapter(phoneNumber, this@DialActivity)
        rv_numpad.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rv_numpad.adapter = adapter

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
            val items = arrayOf("视频通话", "音频通话")
            var choice = -1
            val singleChoiceDialog = AlertDialog.Builder(this@DialActivity)
            singleChoiceDialog.apply {
                setTitle("请选择服务")
                setSingleChoiceItems(items, -1) { _, which ->
                    choice = which
                }
            }
            singleChoiceDialog.setPositiveButton("确定") { _, _ ->
                if (choice !== -1) {
                    Toast.makeText(this@DialActivity,
                        "你选择了" + items[choice],
                        Toast.LENGTH_SHORT).show()
                    if (choice == 0) {
                        StartCall.startVideoCall(this,edit.text.toString())

                    } else {
                        StartCall.startVoiceCall(this,edit.text.toString())
                    }
                } else {
                    SnackBarUtil.error(this, "请选择服务")
                }
                var intent = Intent(this,VoiceActivity::class.java)
                intent.putExtra("flag",true)
                startActivity(intent)
            }
            singleChoiceDialog.show()


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


    fun addContent(string: String) {
        edit.setText("${edit.text}$string")
    }

    public fun initValues() {
        for (i in 1..9) {
            phoneNumber.add(i.toString())
        }
        phoneNumber.add("*")
        phoneNumber.add("0")
        phoneNumber.add("#")
    }
}
