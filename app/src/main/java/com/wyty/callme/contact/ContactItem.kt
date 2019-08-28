package com.wyty.callme.contact

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.wyty.callme.R
import com.wyty.callme.contact.detailed_info.DetailedInfoBottomSheet
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactItem(val context: Context, val isFirst: Boolean, val firstLetter: Char, val contactBean: ContactBean) :
    Item {

    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as ContactItem
            holder.apply {
                itemView.setOnClickListener {
                    val builder = AlertDialog.Builder(item.context)
                    builder.apply {
                        setTitle(" ${item.contactBean.name},请选择通话方式")
                        setItems(arrayOf("语音通话", "视频通话","编辑")) { dialog, which ->
                            when (which) {
                                0 -> {
                                }
                                1 -> {

                                }
                                2 -> {
                                    DetailedInfoBottomSheet.showInfo( item.context as AppCompatActivity ,item.contactBean)
                                }
                            }
                        }
                        show()
                    }
                }
                itemView.setOnLongClickListener {
                    val builder = AlertDialog.Builder(item.context).also {
                        it.apply {
                            setTitle("确定要删除联系人 ${item.contactBean.name} 吗？")
                            setPositiveButton("确定") { _, _ ->
                                ContactsLiveData.remove(item.contactBean)
                            }
                            setNegativeButton("取消") { _, _ -> }
                        }
                    }
                    builder.show()
                    false
                }
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

fun MutableList<Item>.contact(context: Context, contactBean: ContactBean, firstLetter: Char, isFirst: Boolean) =
    add(ContactItem(context, isFirst, firstLetter, contactBean))