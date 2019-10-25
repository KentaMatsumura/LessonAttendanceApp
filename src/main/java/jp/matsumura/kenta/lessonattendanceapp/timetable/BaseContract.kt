package jp.matsumura.kenta.lessonattendanceapp.timetable

class BaseContract {
    interface View {}

    interface Presenter<in T> {
        fun attach(view: T)
    }
}
