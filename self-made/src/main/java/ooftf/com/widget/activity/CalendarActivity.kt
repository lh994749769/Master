package ooftf.com.widget.activity
import android.os.Bundle
import com.ooftf.service.base.BaseSlidingActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import ooftf.com.widget.R

class CalendarActivity : BaseSlidingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        calendarView.setOnItemClickListener {
            toast(it.time.toLocaleString())
        }
    }
}
