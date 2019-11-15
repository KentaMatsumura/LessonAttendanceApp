package jp.matsumura.kenta.lessonattendanceapp.data

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlin.collections.ArrayList


open class Lesson {
    var lessonName: String? = null
    var lessonLocation: String? = null
    var startTime: Timestamp? = null
    var lessonId: Long? = null
    var endTime: Timestamp? = null
    var attendanceState: ArrayList<*>? = null
    var geoFlag: Boolean? = null
    var coordinate: GeoPoint? = null
    var presentLocation: GeoPoint? = null

    fun registrationCoordinate(docName: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .document(docName)
            .set(this)
            .addOnSuccessListener {
                Log.d(
                    "UpdateSuccess",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e ->
                Log.w("UpdateError", "Error writing document", e)
            }
    }

    fun updateCoordinate(docName: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("lessons")
            .document(docName)
            .set(this)
            .addOnSuccessListener {
                Log.d(
                    "UpdateSuccess",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e ->
                Log.w("UpdateError", "Error writing document", e)
            }
    }

}


