package jp.matsumura.kenta.lessonattendanceapp.timetable.presenter

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import jp.matsumura.kenta.lessonattendanceapp.timetable.contract.LessonContract

class LessonPresenter : LessonContract.Presenter {

    private lateinit var view: LessonContract.View

    override fun attach(view: LessonContract.View) {
        this.view = view

    }

    override fun loadLesson() {
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    view.loadLessonSuccess(document)
                }
            }
            .addOnFailureListener { e ->
                Log.w("ERROR LOG", e)
            }
    }

}
