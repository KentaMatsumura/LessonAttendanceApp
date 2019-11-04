package jp.matsumura.kenta.lessonattendanceapp.timetable.contract

import com.google.firebase.firestore.QueryDocumentSnapshot
import jp.matsumura.kenta.lessonattendanceapp.BaseContract

class LessonContract {
    interface View : BaseContract.View {
        fun showLessonFragment(docName: String)
        fun loadLessonSuccess(list: QueryDocumentSnapshot)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadLesson()
    }
}
