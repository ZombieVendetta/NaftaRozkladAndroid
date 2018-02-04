package com.naftarozklad.presenters

import com.naftarozklad.business.*
import com.naftarozklad.views.interfaces.ScheduleView
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 31.10.2017
 */
class SchedulePresenter @Inject constructor(
		private val lessonsUseCase: LessonsUseCase,
		private val groupsUseCase: GroupsUseCase,
		private val synchronizeLessonsUseCase: SynchronizeLessonsUseCase,
		private val initCacheUseCase: InitCacheUseCase,
		private val sessionUseCase: SessionUseCase
) : Presenter<ScheduleView> {

	lateinit var scheduleView: ScheduleView

	override fun attachView(view: ScheduleView) {
		scheduleView = view

		if (!initCacheUseCase.isInitialized()) {
			initCacheUseCase.initInternalRepo {
				attachView(scheduleView)
			}

			return
		}

		scheduleView.setGroupName(groupsUseCase.getGroupById(scheduleView.getGroupId())?.name)
		scheduleView.setSubgroupId(sessionUseCase.getCurrentSubgroupId())
		scheduleView.setWeekId(sessionUseCase.getCurrentWeekId())

		scheduleView.setSubgroupChangedAction { subgroupId ->
			sessionUseCase.setCurrentSubgroupId(subgroupId)
			initList()
		}
		scheduleView.setWeekChangedAction { weekId ->
			sessionUseCase.setCurrentWeekId(weekId)
			initList()
		}

		if (lessonsUseCase.isLessonsExist(scheduleView.getGroupId()))
			initList()
		else
			synchronizeLessons()

		scheduleView.setRefreshAction {
			synchronizeLessons()
		}
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
		setLessons(lessonsUseCase.getLessons(getGroupId(), getWeekId(), getSubgroupId()))
	}
}