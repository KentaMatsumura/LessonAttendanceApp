package jp.matsumura.kenta.lessonattendanceapp.timetable

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class LessonPresenter : LessonContract.Presenter {

    private lateinit var view: LessonContract.View

    override fun attach(view: LessonContract.View) {
        this.view = view

    }

    override fun loadLesson() {
        /*
        TODO :
         DBへアクセスしてデータを取得してくる
         [ISSUE]
         どうやって全部のデータを取ってきて各ボタンへ振り分けたらいいのか
        */
        /*
        流れ的にはリストで全部取ってきて、viewへ流し、view側で表示
        */
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.d("SAMPLEDATA", "${document.id} => ${document.data} ")
                    view.loadLessonSuccess(document)
                }
            }
            .addOnFailureListener { e ->
                Log.w("SAMPLEDATA", e)
            }
    }

}
