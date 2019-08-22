package com.wyty.callme.me

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.linphone.core.LinphoneAddress
import org.linphone.core.LinphoneCore
import org.linphone.core.LinphoneCoreFactory
import org.linphone.core.LinphoneCoreListener
import java.util.*

class LinphoneHelper {

    private lateinit var linphoneCore: LinphoneCore
    public fun getLinphoneCoreIfManagerExists(): LinphoneCore? {
        if (singleton == null) {
            Log.i("tag", "Helper does not exist")
            return null
        }
        return getLinphoneCore()
    }

    companion object {
        private var singleton: LinphoneHelper? = null

        fun getSingleton(): LinphoneHelper {
                    if (singleton == null) {
                        singleton = LinphoneHelper()
                    }


            return singleton!!
        }

    }

    fun startService(context: Context) {
        val intent = Intent(context, LinPhoneService::class.java)
        context.startService(intent)
    }

    @Synchronized
    fun init(listener: LinphoneCoreListener, context: Context) {
        linphoneCore = LinphoneCoreFactory.instance().createLinphoneCore(listener, context)
        linphoneCore.apply {
            isNetworkReachable = true
            enableEchoCancellation(true)
            enableAdaptiveRateControl(true)
        }
        val task = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    linphoneCore.iterate()
                }
            }
        }
        val timer = Timer("Linphone Mini Scheduler")
        timer.schedule(task, 0, 20)

    }

    fun getLinphoneCore() = linphoneCore

    fun login(name: String, password:String, host: String) {
        val identify = "sip:$name@$host"
        val proxy = "sip:$host"
        val linphoneAddress = LinphoneCoreFactory.instance().createLinphoneAddress(proxy)
        linphoneAddress.transport = LinphoneAddress.TransportType.LinphoneTransportTls
        val identifyAddress = LinphoneCoreFactory.instance().createLinphoneAddress(identify)
        val authInfo = LinphoneCoreFactory.instance().createAuthInfo(name, null, password, null, null, host)
        val prxCfg = linphoneCore.createProxyConfig(identifyAddress.asString(),
            linphoneAddress.asStringUriOnly(), linphoneAddress.asStringUriOnly(), true)
        prxCfg.enableAvpf(false)
        prxCfg.avpfRRInterval = 0
        prxCfg.enableQualityReporting(false)
        prxCfg.qualityReportingCollector = null
        prxCfg.qualityReportingInterval = 0
        prxCfg.enableRegister(true)
        linphoneCore.addProxyConfig(prxCfg)
        linphoneCore.addAuthInfo(authInfo)
//        lc.defaultProxyConfig = proxyConfig;
    }
}