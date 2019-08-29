package com.wyty.callme.commons.cache

import com.orhanobut.hawk.Hawk
import com.wyty.callme.contact.ContactBean
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Preference{
    var contacts : Set<ContactBean> by hawk("contacts", mutableSetOf(ContactBean("一哥","123","",0),ContactBean("大姐","123","",0),ContactBean("表哥","123","",0),ContactBean("李四","123","",0),ContactBean("Bernardo Silva","123","",0)))
}

fun <T> hawk(key: String, default: T) = object : ReadWriteProperty<Any?, T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = Hawk.get(key, default)

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        Hawk.put(key, value)
    }

}
