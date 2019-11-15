package jp.matsumura.kenta.lessonattendanceapp.di.component

import dagger.Component
import jp.matsumura.kenta.lessonattendanceapp.BaseApp
import jp.matsumura.kenta.lessonattendanceapp.di.module.ApplicationModule

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: BaseApp)

}
