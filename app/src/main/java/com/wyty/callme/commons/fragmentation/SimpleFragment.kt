package com.wyty.callme.commons.fragmentation

import android.content.Context
import me.yokeyword.fragmentation.SupportFragment
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class SimpleFragment : SupportFragment() {

    protected var mContext: Context? = null
    protected lateinit var mView: View
    protected lateinit var mActivity: Activity


    protected abstract val perMainFragmentLayoutId: Int
    protected abstract fun initFragments()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(perMainFragmentLayoutId, null)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragments()
    }


}
