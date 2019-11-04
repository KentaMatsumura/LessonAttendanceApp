package jp.matsumura.kenta.lessonattendanceapp.di.component

import dagger.Component
import jp.matsumura.kenta.lessonattendanceapp.di.module.FragmentModule
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.view.LessonDetailsFragment

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(lessonDetailsFragment: LessonDetailsFragment)
}
