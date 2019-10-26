package jp.matsumura.kenta.lessonattendanceapp.timetable

import com.google.firebase.firestore.QueryDocumentSnapshot
import jp.matsumura.kenta.lessonattendanceapp.data.Lesson

class LessonContract {
    interface View : BaseContract.View {
        fun showLessonFragment()
        fun loadLessonSuccess(list: QueryDocumentSnapshot)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadLesson()
    }
}
