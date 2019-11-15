package jp.matsumura.kenta.lessonattendanceapp

class BaseContract {
    interface View {}

    interface Presenter<in T> {
        fun attach(view: T)
    }
}
