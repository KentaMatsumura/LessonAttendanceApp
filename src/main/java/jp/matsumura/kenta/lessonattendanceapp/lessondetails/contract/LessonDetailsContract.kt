package jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract

import com.google.firebase.firestore.DocumentSnapshot
import jp.matsumura.kenta.lessonattendanceapp.BaseContract

class LessonDetailsContract {
    interface View : BaseContract.View {
        fun loadDataSuccess(list: DocumentSnapshot)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadData(docName: String)
    }
}
