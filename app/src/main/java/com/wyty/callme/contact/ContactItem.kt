package com.wyty.callme.contact

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.wyty.callme.R
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactItem(val context: Context, val isFirst: Boolean, val firstLetter: Char, val contactBean: ContactBean) :
    Item {

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as ContactItem
            holder.apply {
                itemView.setOnClickListener {  }
                if (item.isFirst) {
                    firstLetter.text = item.firstLetter.toString()
                    firstLetter.visibility = View.VISIBLE
                    line.visibility = View.VISIBLE
                } else {
                    firstLetter.visibility = View.GONE
                    line.visibility = View.GONE
                }
//                avatar.setImageResource(R.layout.)
                firstLetterA.text = item.contactBean.name[0].toString()
                name.text = item.contactBean.name
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_contact, parent, false)
            view.apply {
                return ViewHolder(view, first_letter_en, item_avatar, first_letter_avatar, item_name, line_contact)
            }
        }

    }

    override val controller: ItemController
        get() = Controller

    class ViewHolder(
        itemView: View,
        val firstLetter: TextView,
        val avatar: ImageView,
        val firstLetterA: TextView,
        val name: TextView,
        val line: View
    ) : RecyclerView.ViewHolder(itemView)

}