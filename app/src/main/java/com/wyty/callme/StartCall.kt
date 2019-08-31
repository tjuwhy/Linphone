package com.wyty.callme

import android.app.Activity
import com.wyty.callme.commons.core.LinphoneService
import com.wyty.callme.commons.utils.SnackBarUtil
object StartCall {

    fun startVideoCall(activity: Activity,sip : String) {

        val core = com.wyty.callme.commons.core.LinphoneService.getCore()
        val addr = core.interpretUrl(sip)
        val param = core.createCallParams(null)

        param.enableVideo(true)

        if (addr!=null){
            core.inviteAddressWithParams(addr,param)
        } else {
            SnackBarUtil.error(activity,"请检查对方地址是否输入正确")
        }

    }


    fun startVoiceCall(activity: Activity,sip : String) {
        val core = com.wyty.callme.commons.core.LinphoneService.getCore()
        val addr = core.interpretUrl(sip)
        val param = core.createCallParams(null)

        param.enableVideo(false)

        if (addr!=null){
            core.inviteAddressWithParams(addr,param)
        } else {
            SnackBarUtil.error(activity,"请检查对方地址是否输入正确")
        }

    }}