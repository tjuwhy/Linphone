package com.wyty.callme.contact.add_contact

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.wyty.callme.R
import com.wyty.callme.commons.utils.SnackBarUtil
import com.wyty.callme.commons.view.enableLightStatusBarMode
import com.wyty.callme.contact.ContactBean
import com.wyty.callme.contact.ContactsLiveData
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        add_arr_back.setOnClickListener {
            onBackPressed()
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
                else -> {
                }
            }
            ContactsLiveData.add(ContactBean(name, sip, comment, 0))
            SnackBarUtil.normal(this, "联系人 $name 添加成功")
            finish()
        }
    }

    override fun onBackPressed() {
        if (add_name.text.toString() != "" || add_sip.text.toString() != "" || add_comment.text.toString() != "") {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setMessage("确定要放弃正在编辑的联系人信息吗？")
                setPositiveButton("放弃") { _, _ ->
                    super.onBackPressed()
                }
                setNegativeButton("继续编辑") { _, _ -> }
                show()
            }
        } else {
            super.onBackPressed()
        }
    }
}
