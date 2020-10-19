package com.ooftf.master.m.entrance.ui

import android.Manifest
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.request.RequestOptions
import com.ooftf.basic.AppHolder
import com.ooftf.master.m.entrance.R
import com.ooftf.service.base.BaseApplication
import com.ooftf.service.constant.RouterPath
import com.ooftf.service.engine.GlideApp
import com.ooftf.service.engine.router.FinishCallback
import com.ooftf.service.utils.LifecycleUtil
import com.ooftf.service.utils.ThreadUtil
import com.ooftf.service.utils.TimeRuler
import com.tbruyelle.rxpermissions3.RxPermissions
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class SplashActivity : AppCompatActivity() {
    var drawable: Drawable? = null
    var drawableLive = MutableLiveData<Drawable>()

    init {
        TimeRuler.marker("MyApplication", "SplashActivity init")
        ThreadUtil.runOnNewThread {
            var url = "http://p1.ifengimg.com/fck/2018_01/4b3586c88209a81_w640_h429.jpg"
            try {
                TimeRuler.start("drawable", "start")
                drawable = GlideApp.with(BaseApplication.instance).load(url).apply(RequestOptions().onlyRetrieveFromCache(true)).submit().get()
                if (drawable != null) {
                    drawableLive.postValue(drawable)
                }
                TimeRuler.end("drawable", "end")
            } catch (e: Exception) {
                GlideApp.with(AppHolder.app).load(url).preload()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        drawableLive.observe(this, Observer<Drawable> { t -> window.setBackgroundDrawable(t) })
        window.setBackgroundDrawable(drawable)
        super.onCreate(savedInstanceState)
        TimeRuler.marker("MyApplication", "SplashActivity onCreate")
        setContentView(R.layout.activity_splash)

        skip.setOnClickListener {
            startNextActivity()
        }
        typerTextView.animateText("welcome to ooftf's world")
        RxPermissions(this).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA).subscribe()
        TimeRuler.marker("MyApplication", "SplashActivity onCreate end")
        Observable.intervalRange(0, 30, 0, 100, TimeUnit.MILLISECONDS)
                .bindUntilEvent(this, Lifecycle.Event.ON_DESTROY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.rxjava3.core.Observer<Long> {
                    override fun onComplete() {
                        LifecycleUtil.postOnResume(lifecycle) { startNextActivity() }
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: Long) {

                        skip.text = "跳过${((3000 - t * 100) / 1000f).roundToInt()}"
                    }

                    override fun onError(e: Throwable) {
                        LifecycleUtil.postOnResume(lifecycle) { startNextActivity() }
                    }

                })
    }


    private fun startNextActivity() {
        ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY_MAIN).navigation(this, FinishCallback(this))
    }

}
