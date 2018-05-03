package com.ooftf.widget.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.alibaba.android.arouter.launcher.ARouter

class TaobaoPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ARouter.getInstance().build("/applet/app").navigation() as Fragment
            else -> ARouter.getInstance().build("/widget/tabLayout").navigation() as Fragment
        }
    }

    override fun getCount(): Int = 2
}