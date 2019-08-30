package com.wyty.callme.dial

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wyty.callme.DialActivity
import com.wyty.callme.R

class DialNumAdapter(val items: List<String>, val activity: DialActivity) : RecyclerView.Adapter<DialNumAdapter.ViewHolder>() {

    class ViewHolder(view: View, val textView: TextView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dial, parent, false)
        val textView = view.findViewById<TextView>(R.id.tv_item)
        return ViewHolder(view, textView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp = items[position]
        holder.textView.text = temp
        holder.itemView.setOnClickListener {
            activity.addContent(items[position])
        }
    }
}