package jp.matsumura.kenta.lessonattendanceapp.lessondetails.presenter

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract.LessonDetailsContract

class LessonDetailsPresenter : LessonDetailsContract.Presenter {
    private lateinit var view: LessonDetailsContract.View

    override fun attach(view: LessonDetailsContract.View) {
        this.view = view
    }

    override fun loadData(docName: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .document(docName)
            .get()
            .addOnSuccessListener { result ->
                view.loadDataSuccess(result)
            }
            .addOnFailureListener { e ->
                Log.d("TESTTEST", e.toString())
            }
    }

}
