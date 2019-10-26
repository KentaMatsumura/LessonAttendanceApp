package jp.matsumura.kenta.lessonattendanceapp.timetable

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import jp.matsumura.kenta.lessonattendanceapp.BaseActivity
import jp.matsumura.kenta.lessonattendanceapp.R
import jp.matsumura.kenta.lessonattendanceapp.di.component.DaggerActivityComponent
import jp.matsumura.kenta.lessonattendanceapp.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_timetable.*
import kotlinx.android.synthetic.main.view_custom_button.view.*
import javax.inject.Inject


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

        custom_button_1_1.setOnClickListener { toastMessage(it) }
        custom_button_1_2.setOnClickListener { toastMessage(it) }
        custom_button_1_3.setOnClickListener { toastMessage(it) }
        custom_button_1_4.setOnClickListener { toastMessage(it) }
        custom_button_1_5.setOnClickListener { toastMessage(it) }

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

    override fun showLessonFragment() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    override fun loadLessonSuccess(list: QueryDocumentSnapshot) {
        val button: CustomButtonView =
            when (list.data["lessonId"].toString()) {
                "11" -> findViewById(R.id.custom_button_1_1)
                "21" -> findViewById(R.id.custom_button_1_2)
                "31" -> findViewById(R.id.custom_button_1_3)
                "41" -> findViewById(R.id.custom_button_1_4)
                "51" -> findViewById(R.id.custom_button_1_5)

                else -> findViewById(R.id.custom_button_5_5)
            }

        button.lesson_name.text = list.data["lessonName"].toString()
        button.lesson_location.text = list.data["lessonLocation"].toString()

    }

}
