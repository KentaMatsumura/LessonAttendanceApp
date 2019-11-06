package jp.matsumura.kenta.lessonattendanceapp.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import kotlin.collections.ArrayList


open class Lesson {
    var lessonName: String? = null
    var lessonLocation: String? = null
    var startTime: Timestamp? = null
    var endTime: Timestamp? = null
    var attendanceState: ArrayList<*>? = null
    var geoFrag: Boolean = false
    var coordinate: GeoPoint? = null

    fun RegistCoordinate() {
    }
}

