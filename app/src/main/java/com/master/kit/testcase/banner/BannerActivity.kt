package com.master.kit.testcase.banner

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.master.kit.R
import com.master.kit.activity.GuideActivity
import com.master.kit.dagger.DaggerBannerComponent
import com.master.kit.testcase.retrofit.EControlViewObserver
import com.master.kit.testcase.retrofit.IEResponse
import com.master.kit.testcase.retrofit.ServiceHolder
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import com.youth.banner.loader.ImageLoaderInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_banner.*
import tf.oof.com.service.base.BaseSlidingActivity
import tf.oof.com.service.engine.image_loader.IImageLoader
import javax.inject.Inject


class BannerActivity : BaseSlidingActivity() {

    @Inject lateinit var imageLoader: IImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerBannerComponent.create().inject(this)
        setContentView(R.layout.activity_banner)
        setupBanner()
        requestBanner()
        responseLayout.setOnRetryListener { requestBanner() }
        next.setOnClickListener {
            startActivity(GuideActivity::class.java)
        }
    }

    private fun setupBanner() {
        banner.setImageLoader(object : ImageLoaderInterface<ImageView> {
            override fun createImageView(context: Context): ImageView? {
                return null;
            }

            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                val bean = path as BannerBean.BodyEntity.PicListEntity
                imageLoader.display(context, bean.picUrl, imageView)
            }

        })
    }

    private fun requestBanner() {
        ServiceHolder
                .service
                .getBanner("1", "2")
                .bindToLifecycle(banner)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(HomeObserver(responseLayout))
    }

    inner class HomeObserver(responseView: IEResponse<BannerBean>) : EControlViewObserver<BannerBean>(responseView) {
        override fun onResponseSuccess(bean: BannerBean) {
            super.onResponseSuccess(bean)
            banner.setImages(bean.body.picList)
            banner.start()
        }
    }
}
