package com.wyty.callme.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.wyty.callme.R
import kotlinx.android.synthetic.main.item_history_record.view.*

class TimeItem(val time : String) : Item {
    companion object Controller : ItemController {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as TimeItemViewHolder
            item as TimeItem
            holder.apply {
                time.text = item.time
            }

        }


        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_history_time, parent, false)
            view.apply { return TimeItemViewHolder(view, history_time) }
        }
    }


    override val controller: ItemController
        get() = TimeItem

    class TimeItemViewHolder(
        itemView: View,
        val time: TextView

    ) : RecyclerView.ViewHolder(itemView)
}

fun MutableList<Item>.addTime(time: String) = add(TimeItem(time))