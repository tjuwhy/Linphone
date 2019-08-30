package com.wyty.callme.commons

import java.text.Collator

class Selector(val content:String):Comparable<Selector> {

    private val comparator = Collator.getInstance(java.util.Locale.CHINA)

    override fun compareTo(other: Selector): Int {
        return comparator.compare(content, other.content)
    }

}