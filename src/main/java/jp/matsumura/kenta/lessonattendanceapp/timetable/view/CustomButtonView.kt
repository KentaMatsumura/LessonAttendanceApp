package jp.matsumura.kenta.lessonattendanceapp.timetable.view

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.Nullable
import jp.matsumura.kenta.lessonattendanceapp.R


class CustomButtonView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    init {

        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_custom_button, this)
    }

    @Nullable
    private var listener: OnClickListener? = null

    override fun setOnClickListener(@Nullable l: OnClickListener?) {
        this.listener = l
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            // 範囲内のチェックを追加
            if (listener != null && checkInside(ev)) {
                post { listener!!.onClick(this@CustomButtonView) }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun checkInside(ev: MotionEvent): Boolean {
        val point = IntArray(2)

        getLocationOnScreen(point)

        val x = point[0]
        val y = point[1]

        return ev.rawX >= x && ev.rawX <= x + width && ev.rawY >= y && ev.rawY <= y + height
    }


}
