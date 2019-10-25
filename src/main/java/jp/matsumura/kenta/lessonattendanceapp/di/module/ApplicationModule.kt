package jp.matsumura.kenta.lessonattendanceapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import jp.matsumura.kenta.lessonattendanceapp.BaseApp
import jp.matsumura.kenta.lessonattendanceapp.di.PerApplication
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }
}
