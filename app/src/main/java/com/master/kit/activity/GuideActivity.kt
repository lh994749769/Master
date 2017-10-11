package com.master.kit.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.master.kit.R
import kotlinx.android.synthetic.main.activity_guide.*
import tf.oof.com.service.base.BaseActivity

class GuideActivity : BaseActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewPager.adapter = mSectionsPagerAdapter
        viewPager.offscreenPageLimit = 5
        pageIndicatorView.setViewPager(viewPager)

    }

    class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount() = 3
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return FirstFragment.newInstance()
                1 -> return SecondFragment.newInstance()
                2 -> return ThirdFragment.newInstance()
            }
            return ThirdFragment.newInstance()
        }
        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "SECTION 1"
                1 -> return "SECTION 2"
                2 -> return "SECTION 3"
            }
            return null
        }
    }

    class FirstFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_guide_first, container, false)
            return rootView
        }

        companion object {
            fun newInstance(): FirstFragment {
                val fragment = FirstFragment()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
        }
    }

    class SecondFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_guide_second, container, false)
            return rootView
        }

        companion object {
            fun newInstance(): SecondFragment {
                val fragment = SecondFragment()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
        }
    }

    class ThirdFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_guide_third, container, false)
            return rootView
        }

        companion object {
            fun newInstance(): ThirdFragment {
                val fragment = ThirdFragment()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
        }
    }

}
