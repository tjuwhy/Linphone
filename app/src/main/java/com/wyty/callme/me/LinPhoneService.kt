package com.wyty.callme.me

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import org.linphone.core.*
import java.nio.ByteBuffer


class LinPhoneService : Service(), LinphoneCoreListener {
    private var mKeepAlivePendingIntent: PendingIntent? = null

    companion object {
        private var INSTANCE: LinPhoneService? = null
        public var isManuallyLogin = false

        val isReady: Boolean
            get() = INSTANCE != null

    }

    override fun onCreate() {
        super.onCreate()
        LinphoneCoreFactoryImpl.instance()
        INSTANCE = this
        try{
            LinphoneHelper.getSingleton().init(this,this)
        } catch (e:LinphoneCoreException) {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
        val intent = Intent(this, KeepAliveHandler::class.java)
        mKeepAlivePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        (this.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 60000, 60000, mKeepAlivePendingIntent)

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun registrationState(
        lc: LinphoneCore,
        lpc: LinphoneProxyConfig,
        state: LinphoneCore.RegistrationState,
        s: String
    ) {
        Log.i("State","registration: " + state + " ---" + lpc.address + " - " + s)
    }



    override fun authInfoRequested(p0: LinphoneCore?, p1: String?, p2: String?, p3: String?) = Unit

    override fun callEncryptionChanged(p0: LinphoneCore?, p1: LinphoneCall?, p2: Boolean, p3: String?)  = Unit

    override fun displayMessage(p0: LinphoneCore?, p1: String?) = Unit

    override fun newSubscriptionRequest(p0: LinphoneCore?, p1: LinphoneFriend?, p2: String?) = Unit

    override fun callStatsUpdated(p0: LinphoneCore?, p1: LinphoneCall?, p2: LinphoneCallStats?) = Unit

    override fun isComposingReceived(p0: LinphoneCore?, p1: LinphoneChatRoom?) = Unit

    override fun fileTransferSend(
        p0: LinphoneCore?,
        p1: LinphoneChatMessage?,
        p2: LinphoneContent?,
        p3: ByteBuffer?,
        p4: Int
    ): Int = 0

    override fun configuringStatus(p0: LinphoneCore?, p1: LinphoneCore.RemoteProvisioningState?, p2: String?) = Unit

    override fun fileTransferProgressIndication(
        p0: LinphoneCore?,
        p1: LinphoneChatMessage?,
        p2: LinphoneContent?,
        p3: Int
    ) = Unit

    override fun notifyReceived(p0: LinphoneCore?, p1: LinphoneCall?, p2: LinphoneAddress?, p3: ByteArray?) = Unit

    override fun notifyReceived(p0: LinphoneCore?, p1: LinphoneEvent?, p2: String?, p3: LinphoneContent?) = Unit

    override fun displayStatus(p0: LinphoneCore?, p1: String?) = Unit

    override fun authenticationRequested(p0: LinphoneCore?, p1: LinphoneAuthInfo?, p2: LinphoneCore.AuthMethod?) = Unit
    override fun infoReceived(p0: LinphoneCore?, p1: LinphoneCall?, p2: LinphoneInfoMessage?) = Unit

    override fun notifyPresenceReceived(p0: LinphoneCore?, p1: LinphoneFriend?) = Unit

    override fun friendListCreated(p0: LinphoneCore?, p1: LinphoneFriendList?) = Unit

    override fun dtmfReceived(p0: LinphoneCore?, p1: LinphoneCall?, p2: Int) = Unit

    override fun messageReceived(p0: LinphoneCore?, p1: LinphoneChatRoom?, p2: LinphoneChatMessage?) = Unit

    override fun transferState(p0: LinphoneCore?, p1: LinphoneCall?, p2: LinphoneCall.State?) = Unit

    override fun friendListRemoved(p0: LinphoneCore?, p1: LinphoneFriendList?) = Unit

    override fun subscriptionStateChanged(p0: LinphoneCore?, p1: LinphoneEvent?, p2: SubscriptionState?) = Unit

    override fun show(p0: LinphoneCore?) = Unit

    override fun callState(p0: LinphoneCore?, p1: LinphoneCall?, p2: LinphoneCall.State?, p3: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun messageReceivedUnableToDecrypted(p0: LinphoneCore?, p1: LinphoneChatRoom?, p2: LinphoneChatMessage?) = Unit

    override fun ecCalibrationStatus(p0: LinphoneCore?, p1: LinphoneCore.EcCalibratorStatus?, p2: Int, p3: Any?) = Unit

    override fun uploadProgressIndication(p0: LinphoneCore?, p1: Int, p2: Int) = Unit

    override fun networkReachableChanged(p0: LinphoneCore?, p1: Boolean) = Unit

    override fun displayWarning(p0: LinphoneCore?, p1: String?) = Unit

    override fun globalState(p0: LinphoneCore?, p1: LinphoneCore.GlobalState?, p2: String?) = Unit

    override fun uploadStateChanged(p0: LinphoneCore?, p1: LinphoneCore.LogCollectionUploadState?, p2: String?)= Unit

    override fun fileTransferRecv(
        p0: LinphoneCore?,
        p1: LinphoneChatMessage?,
        p2: LinphoneContent?,
        p3: ByteArray?,
        p4: Int
    ) = Unit

    override fun publishStateChanged(p0: LinphoneCore?, p1: LinphoneEvent?, p2: PublishState?)= Unit


}
