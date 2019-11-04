package jp.matsumura.kenta.lessonattendanceapp.timetable.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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


class TimetableActivity : BaseActivity(), LessonContract.View {

    private lateinit var auth: FirebaseAuth
    @Inject
    lateinit var presenter: LessonContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        injectDependency()
        auth = FirebaseAuth.getInstance()

        setting()

        presenter.attach(this)

        time_signout_button.setOnClickListener {
            signOut()
        }
    }

    private fun setting() {
        email_password_text.text = auth.currentUser?.email
        presenter.loadLesson()

        custom_button_1_1.setOnClickListener {
            toastMessage(it)
            showLessonFragment("${auth.currentUser?.email}_1_1")
        }
        custom_button_2_1.setOnClickListener { toastMessage(it) }
        custom_button_3_1.setOnClickListener { toastMessage(it) }
        custom_button_4_1.setOnClickListener { toastMessage(it) }
        custom_button_5_1.setOnClickListener { toastMessage(it) }

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
        val button: CustomButtonView =
            when (list.data["lessonId"].toString()) {
                "11" -> findViewById(R.id.custom_button_1_1)
                "21" -> findViewById(R.id.custom_button_2_1)
                "31" -> findViewById(R.id.custom_button_3_1)
                "41" -> findViewById(R.id.custom_button_4_1)
                "51" -> findViewById(R.id.custom_button_5_1)

                else -> findViewById(R.id.custom_button_5_5)
            }
        button.lesson_name.text = list.data["lessonName"].toString()
        button.lesson_location.text = list.data["lessonLocation"].toString()

    }

}
