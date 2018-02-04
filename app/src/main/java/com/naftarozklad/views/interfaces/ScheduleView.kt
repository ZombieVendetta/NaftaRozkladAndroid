package com.naftarozklad.views.interfaces

import com.naftarozklad.repo.models.Lesson

/**
 * Created by bohdan on 10/21/17
 */
interface ScheduleView : View {
	companion object {
		val EXTRA_GROUP_ID = "EXTRA_GROUP_ID"
	}

	fun getGroupId(): Int

	fun getWeekId(): Int

	fun getSubgroupId(): Int

	fun onError(errorMessage: String)

	fun setLessons(lessons: Map<Int, List<Lesson>>)

	fun setRefreshAction(action : () -> Unit)

	fun setGroupName(name: String?)
}