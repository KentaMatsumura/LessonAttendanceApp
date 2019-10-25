package jp.matsumura.kenta.lessonattendanceapp.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import jp.matsumura.kenta.lessonattendanceapp.timetable.LessonContract
import jp.matsumura.kenta.lessonattendanceapp.timetable.LessonPresenter

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): LessonContract.Presenter {
        return LessonPresenter()
    }

}
