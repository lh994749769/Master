package ooftf.com.widget.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_swipe_recycler.*
import ooftf.com.widget.R
import ooftf.com.widget.adapter.SwipeRecyclerAdapter
import ooftf.com.widget.bean.SwipeBean
import ooftf.com.widget.dagger.DaggerSwipeRecyclerComponent
import ooftf.com.widget.dagger.SwipeModule
import java.util.*
import javax.inject.Inject

/**
 * 只要改变的内容（即使调用notifyDataSetChanged）不影响布局也就是不会触发（SwipeLayout的onLayout ？）就不会导致划出来菜单栏自动回缩
 *
 * 结论：菜单栏回缩有可能是SwipeLayout的onLayout监听里面
 */
class SwipeRecyclerActivity : AppCompatActivity() {

    @Inject lateinit var adapter: SwipeRecyclerAdapter
    lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_recycler)
        setSupportActionBar(tailoredToolbar)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        DaggerSwipeRecyclerComponent.builder().swipeModule(SwipeModule(this)).build().inject(this)
        recyclerView.adapter = adapter
        adapter.body.add(SwipeBean(0))
        adapter.body.add(SwipeBean(1))
        adapter.body.add(SwipeBean(2))
        adapter.body.add(SwipeBean(3))
        adapter.notifyDataSetChanged()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                adapter.body.forEach { it.position++ }
                runOnUiThread { adapter.notifyDataSetChanged() }
            }
        }, 0, 5000)

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}