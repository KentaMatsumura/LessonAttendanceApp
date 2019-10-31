package jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract

import jp.matsumura.kenta.lessonattendanceapp.BaseContract

class LessonDetailsContract {
    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadData()
    }
}
