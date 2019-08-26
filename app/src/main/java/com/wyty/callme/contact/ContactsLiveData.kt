package com.wyty.callme.contact

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wyty.callme.commons.cache.Preference

object ContactsLiveData : MutableLiveData<List<ContactBean>>() {

    override fun getValue(): List<ContactBean> = Preference.contacts

    fun add(contactBean: ContactBean) {
        val list = Preference.contacts.toMutableList()
        list.add(contactBean)
        Preference.contacts = list
        value = list
    }

    fun remove(contactBean: ContactBean) {
        val list = Preference.contacts.toMutableList()
        list.remove(contactBean)
        Preference.contacts = list
        value = list
    }

    fun modify(old:ContactBean, new: ContactBean) {
        val list = Preference.contacts.toMutableList()
        list.remove(old)
        list.add(new)
        Preference.contacts = list
        value = list
    }
}