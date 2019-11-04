package jp.matsumura.kenta.lessonattendanceapp.lessondetails.view

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

import jp.matsumura.kenta.lessonattendanceapp.R
import jp.matsumura.kenta.lessonattendanceapp.di.component.DaggerFragmentComponent
import jp.matsumura.kenta.lessonattendanceapp.di.module.FragmentModule
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract.LessonDetailsContract
import kotlinx.android.synthetic.main.fragment_lesson_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

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

    @Inject
    lateinit var presenter: LessonDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            docName = it.getString(TAG)
        }
        injectDependency()
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

    override fun loadDataSuccess(list: DocumentSnapshot) {
        class_name.text = list.data!!["lessonName"].toString()
        class_location.text = list.data!!["lessonLocation"].toString()
        val st = list.data!!["startTime"] as Timestamp
        val et = list.data!!["endTime"] as Timestamp
        start_time.text = convertTimestamp2String(st.toDate())
        end_time.text = convertTimestamp2String(et.toDate())
        val attendanceList = list.data!!["attendanceState"] as ArrayList<*>
        val attendanceListArray = attendanceList.toArray() as Array
        val_status_attend.text = countAttendanceState(attendanceListArray, 0).toString()
        val_status_absence.text = countAttendanceState(attendanceListArray, 1).toString()
        val_status_late.text = countAttendanceState(attendanceListArray, 2).toString()
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LessonDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
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
}
