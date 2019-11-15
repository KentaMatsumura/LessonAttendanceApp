package jp.matsumura.kenta.lessonattendanceapp.di.module

import dagger.Module
import dagger.Provides
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.contract.LessonDetailsContract
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.presenter.LessonDetailsPresenter
import jp.matsumura.kenta.lessonattendanceapp.lessondetails.view.LessonDetailsFragment

@Module
class FragmentModule {
    @Provides
    fun provideDetailsPresenter():LessonDetailsContract.Presenter{
        return LessonDetailsPresenter()
    }
}
