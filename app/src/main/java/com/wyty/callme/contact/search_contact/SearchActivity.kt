package com.wyty.callme.contact.search_contact

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.wyty.callme.R
import com.wyty.callme.commons.cache.Preference
import com.wyty.callme.commons.utils.Selector
import com.wyty.callme.commons.view.enableLightStatusBarMode
import com.wyty.callme.contact.contact
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        icon_back.setOnClickListener {
            onBackPressed()
        }
        enableLightStatusBarMode(true)
        window.statusBarColor = Color.parseColor("#FFFFFF")
        rec_search_contact.layoutManager=LinearLayoutManager(this)
        search_edit.addTextChangedListener(object :TextWatcher{
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                rec_search_contact.withItems {
                    Preference.contacts
                        .filter { it.name.contains(s,true) }
                        .sortedBy {
                            Selector(it.name)
                        }
                        .forEach {
                            contact(this@SearchActivity,it,it.name[0],false)
                        }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                  }

        })
    }
}
