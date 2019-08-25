package com.wyty.callme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.utils.SnackBarUtil
import com.wyty.callme.commons.view.enableLightStatusBarMode
import kotlinx.android.synthetic.main.activity_main.*
import org.linphone.core.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mCoreListener: CoreListener
    private lateinit var mAccountCreator: AccountCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableLightStatusBarMode(true)
        window.statusBarColor = Color.parseColor("#FFFFFF")
        mAccountCreator = LinphoneService.getCore().createAccountCreator(null)

        mCoreListener= object : CoreListenerStub(){
            override fun onRegistrationStateChanged(
                lc: Core?,
                cfg: ProxyConfig?,
                cstate: RegistrationState?,
                message: String?
            ) {
                Log.i("cstate",cstate.toString() + "  " + message)
                when (cstate) {
                    RegistrationState.Ok->{
                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                    }
                    RegistrationState.Failed -> {
                        SnackBarUtil.error(this@LoginActivity,message.orEmpty())
                    }
                    RegistrationState.Progress -> {
                        SnackBarUtil.normal(this@LoginActivity,"登录ing...")
                    }
                    else -> {

                    }
                }
            }
        }
        login_button.setOnClickListener {
          configureAccount()
        }
    }

    override fun onResume() {
        super.onResume()
        LinphoneService.getCore().addListener(mCoreListener)

    }

    override fun onPause() {
        super.onPause()
        LinphoneService.getCore().removeListener(mCoreListener)
    }

    private fun configureAccount(){
        val username = edit_username.text.toString()
        val password = edit_password.text.toString()
        val domain = edit_domain.text.toString()
        when {
            username == "" -> {
                SnackBarUtil.error(this@LoginActivity,"请输入用户名！")
            }
            password == "" -> {
                SnackBarUtil.error(this@LoginActivity,"请输入密码!")
            }
            domain=="" -> {
                SnackBarUtil.error(this@LoginActivity,"请输入域！")
            }
        }
        mAccountCreator.username = username

        mAccountCreator.domain = domain
        mAccountCreator.password = password
        mAccountCreator.transport = TransportType.Tls


        // This will automatically create the proxy config and auth info and add them to the Core
        val cfg = mAccountCreator.createProxyConfig()
        // Make sure the newly created one is the default
        LinphoneService.getCore().defaultProxyConfig = cfg
    }
}
