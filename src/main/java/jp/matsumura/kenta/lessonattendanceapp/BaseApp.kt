package jp.matsumura.kenta.lessonattendanceapp

import android.annotation.SuppressLint
import android.app.Application
import jp.matsumura.kenta.lessonattendanceapp.di.component.ApplicationComponent
import jp.matsumura.kenta.lessonattendanceapp.di.module.ApplicationModule
import jp.matsumura.kenta.lessonattendanceapp.di.component.DaggerApplicationComponent

@SuppressLint("Registered")
class BaseApp : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(
                ApplicationModule(
                    this
                )
            ).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}
