package com.wyty.callme

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.wyty.callme.commons.Linphone
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.PERMISSION_BOTH
import com.wyty.callme.commons.SimpleRegistrationCallback
import com.wyty.callme.commons.utils.SnackBarUtil

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!LinphoneService.isReady) {
            Linphone.apply {
                startService(this@LoginActivity)
                addCallback(object : SimpleRegistrationCallback(this@LoginActivity) {
                    override fun registrationOk() {

                        SnackBarUtil.normal(this@LoginActivity,"ok")
//                        Toasty.success(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
//                        val activityDial = Intent(this@LoginActivity, DialActivity::class.java)
//                        startActivity(activityDial)
                    }

                    override fun registrationFailed() {
                        super.registrationFailed()
                        SnackBarUtil.error(this@LoginActivity,"failed")
                    }
                }, null)
            }
        }
        getPermissions()
    }
    private fun login() {
        Linphone.setAccount("yangzihang", "yzh147258369", "sip.linphone.org")
        LinphoneService.isManuallyLogin = true
        Linphone.login()
    }

    private fun getPermissions() {
        val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO)
        ActivityCompat.requestPermissions(this, permissions,0x22)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_BOTH &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            login()
        }
    }


}
