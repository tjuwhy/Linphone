@file:JvmName("HistoryFragmentKt")

package com.wyty.callme


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyty.callme.commons.fragmentation.SimpleFragment
import me.yokeyword.fragmentation.SupportFragment



/**
 * A simple [Fragment] subclass.
 *
 */
class QuickFragment : SimpleFragment() {
    override val perMainFragmentLayoutId: Int
        get() = R.layout.fragment_quick

    override fun initFragments() {

    }

}
