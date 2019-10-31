package jp.matsumura.kenta.lessonattendanceapp.lessondetails.presenter

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract.LessonDetailsContract

class LessonDetailsPresenter : LessonDetailsContract.Presenter {
    override fun attach(view: LessonDetailsContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .document("jojo.k.bump2526@gmail.com_1_1")
            .get()
            .addOnSuccessListener { result ->
                Log.d("TESTTEST", result.toString())
            }
            .addOnFailureListener { e ->
                Log.d("TESTTEST", e.toString())
            }
    }

}
