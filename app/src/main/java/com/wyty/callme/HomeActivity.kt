package com.wyty.callme

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.wyty.callme.contact.ContactBean
import com.wyty.callme.contact.ContactFragment
import com.wyty.callme.contact.ContactsLiveData
import kotlinx.android.synthetic.main.activity_home.*
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.SupportFragment

class HomeActivity : SupportActivity() {
    private val fragments = arrayOfNulls<SupportFragment>(3)
    private val resourcesLight = arrayOf(R.drawable.home_quick_light, R.drawable.home_history_light, R.drawable.home_contact_light)
    private val resourcesDark = arrayOf(R.drawable.home_quick_dark, R.drawable.home_history_dark, R.drawable.home_contact_dark)
    private val FIRST = 0
    private val SECOND = 1
    private val THIRD = 2
    private var mShowingFragment = FIRST
    private var mHidingFragment = FIRST
    private var mIsExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setTabStatus(mShowingFragment)

        dial_fab.setOnClickListener {
            startActivity(Intent(this@HomeActivity,DialActivity::class.java))
        }

        add_person.setOnClickListener {
            ContactsLiveData.add(ContactBean("大舅","123","",0))
        }
        search.setOnClickListener {  }

        if (savedInstanceState == null) {
            fragments[FIRST] = QuickFragment()
            fragments[SECOND] = HistoryFragment()
            fragments[THIRD] = ContactFragment()
            loadMultipleRootFragment(R.id.container, FIRST, fragments[FIRST], fragments[SECOND], fragments[THIRD])
        } else {
            fragments[FIRST] = findFragment(QuickFragment::class.java)
            fragments[SECOND] = findFragment(HistoryFragment::class.java)
            fragments[THIRD] = findFragment(ContactFragment::class.java)
        }
        tab_quick.setOnClickListener {
            setTabStatus(FIRST)
            mShowingFragment = FIRST
            clearFullScreen()
            loadFragment()
        }
        tab_history.setOnClickListener {
            setTabStatus(SECOND)
            mShowingFragment = SECOND
            clearFullScreen()
            loadFragment()
        }
        tab_contact.setOnClickListener {
            setTabStatus(THIRD)
            mShowingFragment = THIRD
            clearFullScreen()
            loadFragment()
        }
        tab_quick.post {
            clearFullScreen()
        }

    }

    private fun clearFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            // 状态栏反色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.statusBarColor = Color.WHITE
        }
    }


    private fun loadFragment() {
        showHideFragment(fragments[mShowingFragment], fragments[mHidingFragment])
        mHidingFragment = mShowingFragment
    }

    private fun setTabStatus(pos: Int) {
        val views = arrayOf(tab_quick, tab_history, tab_contact)
        val textViews = arrayOf(kuaisu, tonghuajilu, tongxunlu)
        for (i in 0..2) {
            views[i].setImageResource(if (i == pos) resourcesLight[i] else resourcesDark[i])
            textViews[i].setTextColor(if (i == pos) Color.parseColor("#1296db") else Color.parseColor("#8a8a8a"))
        }
    }
}
