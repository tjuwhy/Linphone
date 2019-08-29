package com.wyty.callme

import android.arch.lifecycle.Observer
import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.wyty.callme.commons.LinphoneService
import com.wyty.callme.commons.fragmentation.SimpleFragment
import com.wyty.callme.history.addHistory
import com.wyty.callme.history.addTime
import kotlinx.android.synthetic.main.fragment_history.*
import org.linphone.core.CallLog
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : SimpleFragment() {
    override fun initFragments() {
        rec_history.layoutManager = LinearLayoutManager(mActivity)

        val core = LinphoneService.getCore()
        Log.d("callLogs", core.callLogs.size.toString())

        val callLogsLiveData = MutableLiveData<List<CallLog>>()
        callLogsLiveData.observe(this, Observer {
            var ruler = 100000000000000L
            rec_history.withItems {
                it?.forEach {
                    if ((ruler - it.startDate) > 86400) {
                        ruler = it.startDate
                        val now = Date()
                        if ((now.time / 86400000) == (it.startDate / 86400)) {
                            addTime("今天")
                        } else if (((now.time / 86400000) - 1) == (it.startDate / 86400)) {
                            addTime("昨天")
                        } else {
                            addTime(transferLongToDate("MM月dd日", it.startDate * 1000))
                        }
                    }
                    var dir = false
                    if(it.dir.toString()=="Outgoing"){
                        dir = true
                    }
                    var miss = false
                    if(it.status.toString()=="Missed"){
                        miss = true
                    }

                    addHistory(miss,it.remoteAddress.username,
                        transferLongToDate("E HH:mm", it.startDate * 1000),it.remoteAddress.username,dir,this@HistoryFragment.context!!)
                    Log.d("callLogs", it.dir.toString())
                    Log.d("callLogs",it.fromAddress.username)
                    Log.d("callLogs",it.localAddress.username)
                    Log.d("callLogs",it.toAddress.username)
                    Log.d("callLogs",it.remoteAddress.username)
                    Log.d("callLogs",it.status.toString())



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
