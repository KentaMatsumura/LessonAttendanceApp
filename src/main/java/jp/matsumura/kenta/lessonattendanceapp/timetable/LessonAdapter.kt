package jp.matsumura.kenta.lessonattendanceapp.timetable

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import jp.matsumura.kenta.lessonattendanceapp.R
import jp.matsumura.kenta.lessonattendanceapp.data.Lesson

class LessonAdapter(private val context: Context, private val list: MutableList<Lesson>) {

    private val listener: AdapterView.OnItemClickListener
        get() {
            TODO()
        }

    fun onBindViewHolder(holder: ListViewHolder?, pos: Int){
        val post = list[pos]

        holder!!.lessonName.text = post.lessonName
        holder.lessonLocation.text = post.lessonLocation
        holder.attendanceStatus.text = post.attendanceState.size.toString()

    }

    class ListViewHolder(itemView: View) {
        var layout: LinearLayout = itemView.findViewById(R.id.custom_button_layout)
        val lessonName: TextView = itemView.findViewById(R.id.lesson_name)!!
        val lessonLocation: TextView = itemView.findViewById(R.id.lesson_location)
        val attendanceStatus: TextView = itemView.findViewById(R.id.attendance_status)
    }

}
