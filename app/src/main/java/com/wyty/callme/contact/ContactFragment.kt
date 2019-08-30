package com.wyty.callme.contact

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.wyty.callme.R
import com.wyty.callme.commons.Selector
import com.wyty.callme.commons.cache.Preference
import com.wyty.callme.commons.fragmentation.SimpleFragment
import com.wyty.callme.commons.utils.FirstLetterUtil
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : SimpleFragment() {
    val colors = arrayListOf("#651FFF","#00E5FF","#FF3D00","#FF9100")

    override val perMainFragmentLayoutId: Int
        get() = R.layout.fragment_contact

    override fun initFragments() {
        var i = 0
        rec_contact.layoutManager = LinearLayoutManager(mActivity)
        ContactsLiveData.observe(this, Observer{ it ->
            val list = ContactsLiveData.value.asSequence()
                .sortedBy{ Selector(it.name) }
                .toList()
            rec_contact.withItems{
                var oldChar = ';'
                list.forEach {
                    var newChar = FirstLetterUtil.getFirstLetter(it.name)[0]
                    if (oldChar!=newChar) {
                        contact(mActivity,it,newChar,true)
                        oldChar = newChar
                    } else {
                        contact(mActivity,it,newChar,false)
                    }
                }
            }
        })
        ContactsLiveData.value = Preference.contacts


    }
}

