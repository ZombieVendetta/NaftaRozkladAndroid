package com.naftarozklad.presenters

import android.text.TextUtils
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

		if (TextUtils.isEmpty(sessionUseCase.getCurrentGroupName())) {
			scheduleView.openGroupsView()
			return
		}

		scheduleView.setGroupName(sessionUseCase.getCurrentGroupName())
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

		val currentGroup = getCurrentGroup()

		if (currentGroup == null)
			return

		if (lessonsUseCase.isLessonsExist(currentGroup.id))
			initList()
		else
			synchronizeLessons()

		scheduleView.setRefreshAction {
			synchronizeLessons()
		}
	}

	override fun detachView() {}

	private fun synchronizeLessons() {
		getCurrentGroup()?.id?.let {
			synchronizeLessonsUseCase.synchronizeLessons(it, object : SynchronizeCallback {
				override fun onSuccess() {
					initList()
				}

				override fun onError(errorMessage: String) {
					scheduleView.onError(errorMessage)
				}
			})
		}
	}

	private fun initList() = with(scheduleView) {
		getCurrentGroup()?.id?.let { setLessons(lessonsUseCase.getLessons(it, getWeekId(), getSubgroupId())) }
	}

	private fun getCurrentGroup() = groupsUseCase.getGroupByName(sessionUseCase.getCurrentGroupName())
}