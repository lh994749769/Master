package tf.oof.com.service.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by master on 2017/10/9 0009.
 */
class LineItemDecoration: RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.set(1,2,3,4)
    }
}