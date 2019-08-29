package com.wyty.callme.contact.modify_contact

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wyty.callme.R
import com.wyty.callme.commons.Constants.CONTACT
import com.wyty.callme.commons.cache.Preference
import com.wyty.callme.commons.utils.SnackBarUtil
import com.wyty.callme.commons.view.enableLightStatusBarMode
import com.wyty.callme.contact.ContactBean
import com.wyty.callme.contact.ContactsLiveData
import kotlinx.android.synthetic.main.activity_modify_contact.*

class ModifyContactActivity : AppCompatActivity() {

    private lateinit var oldName: String
    private lateinit var oldComment: String
    private lateinit var oldSip: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_contact)
        add_arr_back.setOnClickListener {
            onBackPressed()
        }
        val contactName = intent.extras[CONTACT] as String
        var find = false
        var oldContactBean: ContactBean? = null
        Preference.contacts.forEach {
            if (it.name == contactName) {
                oldContactBean = it
                it.apply {
                    oldName = name
                    add_name.setText(oldName)
                    oldComment = remark
                    add_comment.setText(oldComment)
                    oldSip = sip
                    add_sip.setText(oldSip)
                }
                find = true
            }
        }
        if (!find) {
            oldName = ""
            oldComment = ""
            oldSip = ""
        }

        window.statusBarColor = Color.parseColor("#FFFFFF")
        enableLightStatusBarMode(true)
        add_confirm.setOnClickListener {
            val name = add_name.text.toString()
            val sip = add_sip.text.toString()
            val comment = add_comment.text.toString()
            when {
                name == "" -> {
                    SnackBarUtil.error(this, "请输入姓名")
                    return@setOnClickListener
                }
                sip == "" -> {
                    SnackBarUtil.error(this, "请输入号码")
                    return@setOnClickListener
                }
                sip == oldSip && name == oldName && comment == oldComment -> {
                    return@setOnClickListener
                }
                else -> {
                }
            }
            if(oldContactBean==null){
                SnackBarUtil.error(this@ModifyContactActivity,"")
            } else {
                ContactsLiveData.modify(oldContactBean!!,ContactBean(name, sip, comment, 0))
                SnackBarUtil.normal(this, "联系人修改成功")
                finish()
            }

        }
    }


}
