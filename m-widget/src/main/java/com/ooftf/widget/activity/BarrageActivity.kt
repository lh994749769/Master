package com.ooftf.widget.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.ooftf.barrage.BarrageView
import com.ooftf.arch.frame.mvvm.activity.BaseActivity
import com.ooftf.widget.R
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_barrage.*
import java.util.*
import java.util.concurrent.TimeUnit

@Route(path = "/widget/activity/Barrage")
class BarrageActivity : BaseActivity() {
    lateinit var disposable: Disposable
    var random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrage)
        barrage.setViewCreator(object : BarrageView.ViewOperator {
            override fun createView(parent: BarrageView?, `object`: Any?): View {
                var textview = TextView(this@BarrageActivity);
                textview.textSize = 15F
                textview.setPadding(5, 5, 5, 5)
                textview.setText(`object` as String)
                return textview;
            }

            override fun destroyView(parent: BarrageView, view: View) {
                parent.removeView(view)
            }

        })

        disposable = Flowable.interval(100, 500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    barrage.addItem(random.nextInt().toString())
                }


        Log.e("root", root.parent.toString())
        Log.e("decorView", window.decorView.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
