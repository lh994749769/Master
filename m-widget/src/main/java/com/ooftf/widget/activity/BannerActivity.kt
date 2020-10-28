package com.ooftf.widget.activity

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.ooftf.service.base.BaseSlidingActivity
import com.ooftf.service.engine.imageloader.IImageLoader
import com.ooftf.service.net.etd.bean.BannerBean
import com.ooftf.service.utils.DensityUtil
import com.ooftf.widget.R
import com.ooftf.widget.dagger.DaggerBannerComponent
import com.youth.banner.loader.ImageLoaderInterface
import kotlinx.android.synthetic.main.activity_banner.*
import javax.inject.Inject

@Route(path = "/widget/activity/banner")
class BannerActivity : BaseSlidingActivity() {

    @Inject
    lateinit var imageLoader: IImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerBannerComponent.create().inject(this)
        setContentView(R.layout.activity_banner)
        setupBanner()
        requestBanner()
       // responseLayout.setOnRetryListener { requestBanner() }
        next.setOnClickListener {
            /*ServiceHolder
                    .service
                    .getBanner("1", "2")
                    .bindToLifecycle(banner)
                    .observeOn(AndroidSchedulers.mainThread())
                    //.compose(ButtonAction(next, "正在获取Banner"))
                    .subscribe(object : BaseResponse<BannerBean>() {
                        override fun onSuccess(bean: BannerBean) {
                            banner.setImages(bean.body.picList)
                            banner.start()
                        }

                    })*/
        }
    }

    private fun setupBanner() {
        var height = DensityUtil.getScreenWidthPixels(this) * 400f / 700f
        banner.post {
            banner.layoutParams.height = height.toInt()
        }
        banner.setImageLoader(object : ImageLoaderInterface<ImageView> {
            override fun createImageView(context: Context): ImageView? {
                return null
            }

            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                val bean = path as BannerBean.BodyEntity.PicListEntity
                imageLoader.display(bean.picUrl, imageView)
            }

        })
    }

    private fun requestBanner() {
       /* ServiceHolder
                .service
                .getBanner("1", "2")
                .bindToLifecycle(banner)
                .observeOn(AndroidSchedulers.mainThread())
               // .compose(responseLayout.getAction())
                .subscribe(object : BaseResponse<BannerBean>() {
                    override fun onSuccess(bean: BannerBean) {
                        banner.setImages(bean.body.picList)
                        banner.start()
                    }

                })*/
    }

}
