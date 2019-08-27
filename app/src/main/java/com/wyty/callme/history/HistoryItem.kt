package com.wyty.callme.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.wyty.callme.R
import com.wyty.callme.commons.view.CircleImageView
import kotlinx.android.synthetic.main.item_history_record.view.*

class HistoryItem(
    val hasRecord: Boolean,
    val userName: String,
    val time: String,
    val callNum: String,
    val status: Boolean
) : Item {
    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as HistoryItemViewHolder
            item as HistoryItem

            holder.apply {
                if (item.hasRecord) {
                    userCover.visibility = View.GONE
                    cirleImg.visibility = View.VISIBLE
                    first.visibility = View.VISIBLE
                }
                first.text = item.userName.subSequence(0, 1)
                callNum.text = item.callNum
                if (item.status) {
                    callStatus.setImageResource(R.drawable.phone_send)
                } else {
                    callStatus.setImageResource(R.drawable.phone_receive)
                }
                time.text = item.time
                more.setOnClickListener { }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_history_record, parent, false)
            view.apply {
                return HistoryItemViewHolder(
                    view,
                    user_img,
                    default_img,
                    telnumber,
                    call_status,
                    history_time,
                    more_if,
                    first_letter_avatar
                )
            }

        }
    }

    override val controller: ItemController
        get() = HistoryItem

    class HistoryItemViewHolder(
        itemView: View,
        val cirleImg: CircleImageView,
        val userCover: ImageView,
        val callNum: TextView,
        val callStatus: ImageView,
        val time: TextView,
        val more: ImageView,
        val first: TextView
    ) : RecyclerView.ViewHolder(itemView)

}

fun MutableList<Item>.addHistory(
    hasRecord: Boolean,
    userName: String,
    time: String,
    callNum: String,
    status: Boolean
) = add(HistoryItem(hasRecord, userName, time, callNum, status))