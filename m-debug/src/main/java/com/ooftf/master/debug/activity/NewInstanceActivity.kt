package com.ooftf.master.debug.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ooftf.master.debug.R
import com.ooftf.service.base.BaseSlidingActivity
import com.ooftf.service.constant.RouterPath
import kotlinx.android.synthetic.main.activity_new_instance.*

@Route(path = "/debug/activity/newInstance")
class NewInstanceActivity : BaseSlidingActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_instance)
        click.setOnClickListener {
            ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY_MAIN).navigation()
        }
    }
}
