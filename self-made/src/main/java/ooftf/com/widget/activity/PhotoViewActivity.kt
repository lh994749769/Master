package ooftf.com.widget.activity

import android.os.Bundle
import com.ooftf.service.base.BaseSlidingActivity
import ooftf.com.widget.R

class PhotoViewActivity : BaseSlidingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)
    }
}
