package com.wyty.callme.contact.detailed_info

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.wyty.callme.R
import com.wyty.callme.commons.Constants.CONTACT
import com.wyty.callme.commons.vbps.ViewPagerBottomSheetBehavior
import com.wyty.callme.commons.vbps.ViewPagerBottomSheetDialogFragment
import com.wyty.callme.contact.ContactBean
import com.wyty.callme.contact.modify_contact.ModifyContactActivity
import kotlinx.android.synthetic.main.fragment_detail_info.view.*

class DetailedInfoBottomSheet:ViewPagerBottomSheetDialogFragment() {


    companion object {
        private lateinit var mContactBean: ContactBean
        private val TAG_SHARE = "detailed_info"
        private val cachedFragment = DetailedInfoBottomSheet()

        fun showInfo(activity: AppCompatActivity, contactBean: ContactBean) {
            val fm = activity.supportFragmentManager
            var fragment = fm.findFragmentByTag(TAG_SHARE) as? DetailedInfoBottomSheet
            if (fragment == null) {
                fragment = cachedFragment
            }
            if (fragment.isAdded) {
                return
            }
            fragment.show(fm, TAG_SHARE)
            mContactBean = contactBean
        }
    }

    override fun setupDialog(dialog: Dialog?, style: Int) {
        if (dialog==null){
            return
        }

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_detail_info,null,false)
        view.apply {
            info_name.text= mContactBean.name
            info_sip.text= "号码：${mContactBean.sip}"
            info_comment.text = "备注：${mContactBean.remark}"
            icon_modify.setOnClickListener {
                val intent = Intent(context,ModifyContactActivity::class.java)
                intent.putExtra(CONTACT, mContactBean.name)
                startActivity(intent)
                dismiss()
            }
            icon_qrcode.setOnClickListener {

            }
            info_call.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.apply {
                    setTitle(" ${mContactBean.name},请选择通话方式")
                    setItems(arrayOf("语音通话", "视频通话")) { dialog, which ->
                        when (which) {
                            0 -> {
                            }
                            1 -> {

                            }
                        }
                    }
                    show()
                }
            }
        }

        dialog.setContentView(view)
        val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
        val bottomSheetBehavior = ViewPagerBottomSheetBehavior.from(bottomSheet)


        dialog.setOnShowListener {
            val detailLayoutHeight = 1376
            bottomSheetBehavior.peekHeight = detailLayoutHeight
            bottomSheetBehavior.state = ViewPagerBottomSheetBehavior.STATE_COLLAPSED
        }
    }
}