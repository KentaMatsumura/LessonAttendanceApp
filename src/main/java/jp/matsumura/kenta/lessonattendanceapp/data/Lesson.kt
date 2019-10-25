package jp.matsumura.kenta.lessonattendanceapp.data

import java.util.*


data class Lesson(
    var lessonName: String,
    var lessonLocation: String,
    var startTime: Date,
    var endTime: Date,
    var attendanceState: MutableList<Int>,
    var coordinate: MutableList<Double>
)
