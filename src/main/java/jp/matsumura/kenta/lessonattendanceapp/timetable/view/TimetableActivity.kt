package jp.matsumura.kenta.lessonattendanceapp.timetable.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import jp.matsumura.kenta.lessonattendanceapp.BaseActivity
import jp.matsumura.kenta.lessonattendanceapp.R
import jp.matsumura.kenta.lessonattendanceapp.data.Lesson
import jp.matsumura.kenta.lessonattendanceapp.di.component.DaggerActivityComponent
import jp.matsumura.kenta.lessonattendanceapp.di.module.ActivityModule
import jp.matsumura.kenta.lessonattendanceapp.timetable.contract.LessonContract
import kotlinx.android.synthetic.main.activity_timetable.*
import kotlinx.android.synthetic.main.view_custom_button.view.*
import javax.inject.Inject
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.LessonDetailsActivity


class TimetableActivity : BaseActivity(), LessonContract.View{


    private lateinit var auth: FirebaseAuth
    @Inject
    lateinit var presenter: LessonContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        injectDependency()
        auth = FirebaseAuth.getInstance()

        presenter.attach(this)
        setting()

        time_signout_button.setOnClickListener {
            signOut()
        }
    }

    private fun setting() {
        email_password_text.text = auth.currentUser?.email
        presenter.loadLesson()

    }

    private fun toastMessage(v: View?) {
        if (v != null) {
            Toast.makeText(applicationContext, "Click!${v.id}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signOut() {
        auth.signOut()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    override fun showLessonFragment(docName: String) {
        val lessonDetailsIntent = Intent(applicationContext, LessonDetailsActivity::class.java)
        lessonDetailsIntent.putExtra(LessonDetailsActivity.DOC_KEY, docName)
        startActivity(lessonDetailsIntent)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    @SuppressLint("ResourceAsColor")
    override fun loadLessonSuccess(list: QueryDocumentSnapshot) {
        val lessonData = Lesson()
        lessonData.lessonName = list.data["lessonName"].toString()
        lessonData.lessonLocation = list.data["lessonLocation"].toString()
        lessonData.startTime = list.data["startTime"] as Timestamp
        lessonData.endTime = list.data["endTime"] as Timestamp
        lessonData.attendanceState = list.data["attendanceState"] as ArrayList<*>
//        lessonData.geoFlag = list.data["geoFlag"] as Boolean
        lessonData.lessonId = list.data["lessonId"] as Long
        lessonData.coordinate = list.data["coordinate"] as GeoPoint

        val button: CustomButtonView =
            when (lessonData.lessonId.toString()) {
                "11" -> findViewById(R.id.custom_button_1_1)
                "12" -> findViewById(R.id.custom_button_1_2)
                "13" -> findViewById(R.id.custom_button_1_3)
                "14" -> findViewById(R.id.custom_button_1_4)
                "15" -> findViewById(R.id.custom_button_1_5)
                "16" -> findViewById(R.id.custom_button_1_6)
                "21" -> findViewById(R.id.custom_button_2_1)
                "22" -> findViewById(R.id.custom_button_2_2)
                "23" -> findViewById(R.id.custom_button_2_3)
                "24" -> findViewById(R.id.custom_button_2_4)
                "25" -> findViewById(R.id.custom_button_2_5)
                "26" -> findViewById(R.id.custom_button_2_6)
                "31" -> findViewById(R.id.custom_button_3_1)
                "32" -> findViewById(R.id.custom_button_3_2)
                "33" -> findViewById(R.id.custom_button_3_3)
                "34" -> findViewById(R.id.custom_button_3_4)
                "35" -> findViewById(R.id.custom_button_3_5)
                "36" -> findViewById(R.id.custom_button_3_6)
                "41" -> findViewById(R.id.custom_button_4_1)
                "42" -> findViewById(R.id.custom_button_4_2)
                "43" -> findViewById(R.id.custom_button_4_3)
                "44" -> findViewById(R.id.custom_button_4_4)
                "45" -> findViewById(R.id.custom_button_4_5)
                "46" -> findViewById(R.id.custom_button_4_6)
                "51" -> findViewById(R.id.custom_button_5_1)
                "52" -> findViewById(R.id.custom_button_5_2)
                "53" -> findViewById(R.id.custom_button_5_3)
                "54" -> findViewById(R.id.custom_button_5_4)
                "55" -> findViewById(R.id.custom_button_5_5)
                "56" -> findViewById(R.id.custom_button_5_6)

                else -> findViewById(R.id.custom_button_5_6)
            }
        button.lesson_name.text = lessonData.lessonName
        button.lesson_location.text = lessonData.lessonLocation
        setButtonAction(lessonData)
    }

    private fun setButtonAction(lesson: Lesson) {
        when (lesson.lessonId.toString()) {
            "11" -> {
                custom_button_1_1.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_1_1")
                }
            }
            "12" -> {
                custom_button_1_2.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_1_2")
                }
            }
            "13" -> {
                custom_button_1_3.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_1_3")
                }
            }
            "14" -> {
                custom_button_1_4.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_1_4")
                }
            }
            "15" -> {
                custom_button_1_5.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_1_5")
                }
            }
            "16" -> {
                custom_button_1_6.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_1_6")
                }
            }
            "21" -> {
                custom_button_2_1.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_2_1")
                }
            }
            "22" -> {
                custom_button_2_2.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_2_2")
                }
            }
            "23" -> {
                custom_button_2_3.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_2_3")
                }
            }
            "24" -> {
                custom_button_2_4.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_2_4")
                }
            }
            "25" -> {
                custom_button_2_5.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_2_5")
                }
            }
            "26" -> {
                custom_button_2_6.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_2_6")
                }
            }
            "31" -> {
                custom_button_3_1.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_3_1")
                }
            }
            "32" -> {
                custom_button_3_2.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_3_2")
                }
            }
            "33" -> {
                custom_button_3_3.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_3_3")
                }
            }
            "34" -> {
                custom_button_3_4.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_3_4")
                }
            }
            "35" -> {
                custom_button_3_5.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_3_5")
                }
            }
            "36" -> {
                custom_button_3_6.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_3_6")
                }
            }
            "41" -> {
                custom_button_4_1.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_4_1")
                }
            }
            "42" -> {
                custom_button_4_2.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_4_2")
                }
            }
            "43" -> {
                custom_button_4_3.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_4_3")
                }
            }
            "44" -> {
                custom_button_4_4.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_4_4")
                }
            }
            "45" -> {
                custom_button_4_5.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_4_5")
                }
            }
            "46" -> {
                custom_button_4_6.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_4_6")
                }
            }
            "51" -> {
                custom_button_5_1.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_5_1")
                }
            }
            "52" -> {
                custom_button_5_2.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_5_2")
                }
            }
            "53" -> {
                custom_button_5_3.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_5_3")
                }
            }
            "54" -> {
                custom_button_5_4.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_5_4")
                }
            }
            "55" -> {
                custom_button_5_5.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_5_5")
                }
            }
            "56" -> {
                custom_button_5_6.setOnClickListener {
                    showLessonFragment("${auth.currentUser?.email}_5_6")
                }
            }
        }
    }

}

