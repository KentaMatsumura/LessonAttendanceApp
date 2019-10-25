package jp.matsumura.kenta.lessonattendanceapp.timetable

class LessonContract {
    interface View : BaseContract.View {
        fun showLessonFragment()
    }

    interface Presenter : BaseContract.Presenter<View> {}
}
