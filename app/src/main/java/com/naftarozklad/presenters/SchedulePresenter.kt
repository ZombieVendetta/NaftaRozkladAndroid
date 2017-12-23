package com.naftarozklad.presenters

import com.naftarozklad.business.LessonsUseCase
import com.naftarozklad.business.SynchronizeCallback
import com.naftarozklad.business.SynchronizeLessonsUseCase
import com.naftarozklad.views.interfaces.ScheduleView
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 31.10.2017
 */
class SchedulePresenter @Inject constructor(
		private val lessonsUseCase: LessonsUseCase,
		private val synchronizeLessonsUseCase: SynchronizeLessonsUseCase
) : Presenter<ScheduleView> {

	lateinit var scheduleView: ScheduleView

	override fun attachView(view: ScheduleView) {
		scheduleView = view

		if (lessonsUseCase.isLessonsExist(scheduleView.getGroupId())) {
			initList()
			return
		}

		synchronizeLessons()
	}

	override fun detachView() {}

	private fun synchronizeLessons() {
		synchronizeLessonsUseCase.synchronizeLessons(scheduleView.getGroupId(), object : SynchronizeCallback {
			override fun onSuccess() {
				initList()
			}

			override fun onError(errorMessage: String) {
				scheduleView.onError(errorMessage)
			}
		})
	}

	private fun initList() = with(scheduleView) {
		setListItems(lessonsUseCase.getLessons(getGroupId(), getWeekId(), getSubgroupId()))
	}
}