package com.wyty.callme.contact

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.wyty.callme.R
import com.wyty.callme.commons.fragmentation.SimpleFragment
import kotlinx.android.synthetic.main.fragment_contact.*
import me.yokeyword.fragmentation.SupportFragment


class ContactFragment : SimpleFragment() {
    val colors = arrayListOf("#651FFF","#00E5FF","#FF3D00","#FF9100")

    override val perMainFragmentLayoutId: Int
        get() = R.layout.fragment_contact

    override fun initFragments() {
        var i = 0
        rec_contact.layoutManager = LinearLayoutManager(mActivity)
        rec_contact.withItems{
            contact(this@ContactFragment.mActivity, ContactBean("二大爷","12345678","",Color.parseColor(colors[i++%4])),'E',true)
            repeat(4){
                contact(this@ContactFragment.mActivity, ContactBean("二哥","12345678","",Color.parseColor(colors[i++%4])),'E',false)
            }
            contact(this@ContactFragment.mActivity, ContactBean("三大爷","12345678","",Color.parseColor(colors[i++%4])),'S',true)
            repeat(4){
                contact(this@ContactFragment.mActivity, ContactBean("三哥","12345678","",Color.parseColor(colors[i++%4])),'S',false)
            }
        }
    }
}

fun MutableList<Item>.contact(context:Context,contactBean: ContactBean,firstLetter:Char,isFirst:Boolean)=add(ContactItem(context,isFirst,firstLetter,contactBean))