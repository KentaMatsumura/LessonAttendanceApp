package jp.matsumura.kenta.lessonattendanceapp.di.component

import dagger.Component
import jp.matsumura.kenta.lessonattendanceapp.di.module.ActivityModule
import jp.matsumura.kenta.lessonattendanceapp.timetable.TimetableActivity

@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: TimetableActivity)

}
