package com.wyty.callme

import android.arch.lifecycle.Observer
import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.fragmentation.SimpleFragment
import kotlinx.android.synthetic.main.fragment_history.*
import org.linphone.core.CallLog
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : SimpleFragment() {
    override fun initFragments() {
        rec_history.layoutManager = LinearLayoutManager(mActivity)

        val core = LinphoneService.getCore()
        Log.d("callLogs",core.callLogs.size.toString())

        val callLogsLiveData = MutableLiveData<List<CallLog>>()
        callLogsLiveData.observe(this,Observer {
            Log.d("callLogs",it?.size.toString())
            it?.forEach {
                rec_history.withItems {

                }
            }
        })

        callLogsLiveData.value = core.callLogs.toList()


    }

    @SuppressLint("SimpleDateFormat")
    private fun transferLongToDate(dateFormat: String, millSec: Long): String {

        val sdf = SimpleDateFormat(dateFormat)

        val date = Date(millSec)

        return sdf.format(date)

    }

    override val perMainFragmentLayoutId: Int
        get() = R.layout.fragment_history

}
