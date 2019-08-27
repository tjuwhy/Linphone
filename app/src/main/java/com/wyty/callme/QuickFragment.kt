@file:JvmName("HistoryFragmentKt")

package com.wyty.callme

import com.wyty.callme.commons.fragmentation.SimpleFragment

class QuickFragment : SimpleFragment() {
    override val perMainFragmentLayoutId: Int
        get() = R.layout.fragment_quick

    override fun initFragments() {

    }

}
