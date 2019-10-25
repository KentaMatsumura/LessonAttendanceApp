package jp.matsumura.kenta.lessonattendanceapp.timetable

class LessonPresenter : LessonContract.Presenter{

    private lateinit var view: LessonContract.View

    override fun attach(view: LessonContract.View) {
        this.view = view

    }

}
