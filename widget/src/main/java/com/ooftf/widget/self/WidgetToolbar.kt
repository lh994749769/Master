package com.ooftf.widget.self

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import com.ooftf.service.widget.toolbar.ScrollToolbar
import com.ooftf.widget.R

class WidgetToolbar : ScrollToolbar {
    override fun turnInitState() {
        settting.isVisible = false
        sort.isVisible = false
        settings_black.isVisible = true
        sort_black.isVisible = true
    }

    override fun turnUnState() {
        settting.isVisible = true
        sort.isVisible = true
        settings_black.isVisible = false
        sort_black.isVisible = false
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /*var settting: MenuItem? = null
    var sort: MenuItem? = null
    var settings_black: MenuItem? = null
    var sort_black: MenuItem? = null*/
    lateinit var settting: MenuItem
    lateinit var sort: MenuItem
    lateinit var settings_black: MenuItem
    lateinit var sort_black: MenuItem
    override fun inflateMenu(resId: Int) {
        super.inflateMenu(resId)
        settting = menu.findItem(R.id.action_settings)
        sort = menu.findItem(R.id.action_sort)
        settings_black = menu.findItem(R.id.action_settings_black)
        sort_black = menu.findItem(R.id.action_sort_black)
    }

}