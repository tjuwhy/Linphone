package com.wyty.callme

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyty.callme.commons.fragmentation.SimpleFragment

class HistoryFragment : SimpleFragment() {
    override fun initFragments() {

    }

    override val perMainFragmentLayoutId: Int
        get() = R.layout.fragment_history

}
