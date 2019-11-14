package jp.matsumura.kenta.lessonattendanceapp.lessondetails.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint

import jp.matsumura.kenta.lessonattendanceapp.R
import jp.matsumura.kenta.lessonattendanceapp.data.Lesson
import jp.matsumura.kenta.lessonattendanceapp.di.component.DaggerFragmentComponent
import jp.matsumura.kenta.lessonattendanceapp.di.module.FragmentModule
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract.LessonDetailsContract
import kotlinx.android.synthetic.main.fragment_lesson_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LessonDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LessonDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LessonDetailsFragment : Fragment(), LessonDetailsContract.View {
    private var docName: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var lesson = Lesson()

    @Inject
    lateinit var presenter: LessonDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            docName = it.getString(TAG)
        }
        injectDependency()

        fusedLocationClient = FusedLocationProviderClient(this.context!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        docName?.let { presenter.loadData(it) }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    @SuppressLint("ShowToast")
    override fun loadDataSuccess(list: DocumentSnapshot) {
        val db = list.data
        lesson.lessonName = list.data!!["lessonName"].toString()
        lesson.lessonLocation = list.data!!["lessonLocation"].toString()
        lesson.startTime = list.data!!["startTime"] as Timestamp
        lesson.endTime = list.data!!["endTime"] as Timestamp
        lesson.attendanceState = list.data!!["attendanceState"] as ArrayList<*>
        lesson.geoFlag = list.data!!["geoFlag"] as Boolean
        lesson.lessonId = list.data!!["lessonId"] as Long
        lesson.coordinate = list.data!!["coordinate"] as GeoPoint
        lesson.presentLocation = list.data!!["presentLocation"] as GeoPoint

        setView()
        getGeoPoint()
        updatePresentLocation(list)

        if (lesson.geoFlag!!) {
            /*
            位置情報取得済時
            */
            setButtonView()
            class_edit_button.setOnClickListener{
                lesson.updateCoordinate(docName!!)
                Toast.makeText(context, "update geopoint", Toast.LENGTH_SHORT).show()
            }
        } else {
            /*
            位置情報未取得時
             */
            class_edit_button.setOnClickListener {
                lesson.registrationCoordinate(docName!!)
                Toast.makeText(context, "registered geopoint", Toast.LENGTH_SHORT).show()
                setButtonView()

            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    private fun injectDependency() {
        val lessonDetailsComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        lessonDetailsComponent.inject(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            LessonDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG, param1)
                }
            }

        val TAG: String = "LessonDetailsFragment"
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTimestamp2String(time: Date): String {
        val df = SimpleDateFormat("HH:mm")
        return df.format(time)
    }

    private fun countAttendanceState(list: Array<*>, target: Long): Int {
        return list.count { it == target }
    }

    private fun getGeoPoint() {
        // どのような取得方法を要求
        val locationRequest = LocationRequest().apply {
            // 精度重視(電力大)と省電力重視(精度低)を両立するため2種類の更新間隔を指定
            // 今回は公式のサンプル通りにする。
            interval = 10000                                   // 最遅の更新間隔(但し正確ではない。)
            fastestInterval = 5000                             // 最短の更新間隔
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY  // 精度重視
        }

        // コールバック
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                // 更新直後の位置が格納されているはず
                val location = locationResult?.lastLocation ?: return
                // ここでDBへ登録
                lesson.coordinate = GeoPoint(location.latitude, location.longitude)
                lesson.geoFlag = true
            }
        }

        // 位置情報を更新
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun setView() {
        val attendanceListArray = lesson.attendanceState!!.toArray() as Array

        class_name.text = lesson.lessonName
        class_location.text = lesson.lessonLocation
        start_time.text = convertTimestamp2String(lesson.startTime!!.toDate())
        end_time.text = convertTimestamp2String(lesson.endTime!!.toDate())
        val_status_attend.text = countAttendanceState(attendanceListArray, 0).toString()
        val_status_absence.text = countAttendanceState(attendanceListArray, 1).toString()
        val_status_late.text = countAttendanceState(attendanceListArray, 2).toString()
    }

    @SuppressLint("ResourceAsColor")
    private fun setButtonView() {
        class_edit_button.text = getString(R.string.registered_text)
        class_edit_button.setBackgroundColor(R.color.registered_color)
    }

    private fun updatePresentLocation(list: DocumentSnapshot){
        lesson.lessonName = list.data!!["lessonName"].toString()
        lesson.lessonLocation = list.data!!["lessonLocation"].toString()
        lesson.startTime = list.data!!["startTime"] as Timestamp
        lesson.endTime = list.data!!["endTime"] as Timestamp
        lesson.attendanceState = list.data!!["attendanceState"] as ArrayList<*>
        lesson.geoFlag = list.data!!["geoFlag"] as Boolean
        lesson.lessonId = list.data!!["lessonId"] as Long
        lesson.coordinate = list.data!!["coordinate"] as GeoPoint
        lesson.presentLocation = list.data!!["presentLocation"] as GeoPoint
        getPresentGeoPoint()

        Log.d("TESTTEST", "TESTTETS")
    }

    private fun getPresentGeoPoint() {
        // どのような取得方法を要求
        val locationRequest = LocationRequest().apply {
            // 精度重視(電力大)と省電力重視(精度低)を両立するため2種類の更新間隔を指定
            // 今回は公式のサンプル通りにする。
            interval = 10000                                   // 最遅の更新間隔(但し正確ではない。)
            fastestInterval = 5000                             // 最短の更新間隔
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY  // 精度重視
        }

        // コールバック
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                // 更新直後の位置が格納されているはず
                val location = locationResult?.lastLocation ?: return
                // ここでDBへ登録
                lesson.presentLocation = GeoPoint(location.latitude, location.longitude)
                Log.d("TESTTEST", "TESTTETS")

            }
        }

        // 位置情報を更新
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

}
