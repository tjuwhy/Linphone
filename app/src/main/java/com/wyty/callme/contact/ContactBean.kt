package com.wyty.callme.contact

import android.graphics.Color

data class ContactBean(val name:String,val sip:String,val remark:String,val avatar:Int){
    override fun equals(other: Any?): Boolean {
        if (other is ContactBean){
            return name == other.name
        } else {
            return false
        }
    }
}
