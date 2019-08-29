package com.wyty.callme.contact

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wyty.callme.commons.cache.Preference

object ContactsLiveData : MutableLiveData<Set<ContactBean>>() {

    override fun getValue(): Set<ContactBean> = Preference.contacts

    fun add(contactBean: ContactBean) {
        val list = Preference.contacts.toMutableSet()
        list.add(contactBean)
        Preference.contacts = list
        value = list
    }

    fun remove(contactBean: ContactBean) {
        val list = Preference.contacts.toMutableSet()
        list.remove(contactBean)
        Preference.contacts = list
        value = list
    }

    fun modify(old:ContactBean, new: ContactBean) {
        val list = Preference.contacts.toMutableSet()
        list.remove(old)
        list.add(new)
        Preference.contacts = list
        value = list
    }
}