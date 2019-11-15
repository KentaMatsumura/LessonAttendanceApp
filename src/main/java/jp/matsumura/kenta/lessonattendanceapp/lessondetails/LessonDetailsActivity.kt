package jp.matsumura.kenta.lessonattendanceapp.lessondetails

import android.net.Uri
import android.os.Bundle
import jp.matsumura.kenta.lessonattendanceapp.BaseActivity
import jp.matsumura.kenta.lessonattendanceapp.R
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.view.LessonDetailsFragment

class LessonDetailsActivity : BaseActivity(),
    LessonDetailsFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_details)

        val docName = intent.getStringExtra(DOC_KEY)
        val bundle = Bundle()
        bundle.putString(LessonDetailsFragment.TAG, docName)

        val fragment = LessonDetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(
                AnimType.FADE.getAnimPair().first,
                AnimType.FADE.getAnimPair().second
            )
            .replace(R.id.frame, fragment)
            .commit()
    }

    enum class AnimType {
        SLIDE,
        FADE;

        fun getAnimPair(): Pair<Int, Int> {
            return when (this) {
                SLIDE -> Pair(R.anim.slide_left, R.anim.slide_right)
                FADE -> Pair(R.anim.fade_in, R.anim.fade_out)
            }

            return Pair(R.anim.slide_left, R.anim.slide_right)
        }
    }

    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        val DOC_KEY = "lessondetails.activity.intent"
    }

}
