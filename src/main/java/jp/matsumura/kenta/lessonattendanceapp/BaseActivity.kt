package jp.matsumura.kenta.lessonattendanceapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

open class BaseActivity : AppCompatActivity() {

    // [START declare_auth]
//    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
    }
}
