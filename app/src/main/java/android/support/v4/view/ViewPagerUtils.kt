package android.support.v4.view

import android.view.View

object ViewPagerUtils {

    fun getCurrentView(viewPager: ViewPager): View? {
        val currentItem = viewPager.currentItem
        for (i in 0 until viewPager.childCount) {
            val child = viewPager.getChildAt(i)
            val layoutParams = child.layoutParams as ViewPager.LayoutParams
            if (!layoutParams.isDecor && currentItem == layoutParams.position) {
                return child
            }
        }
        return null
    }

}
