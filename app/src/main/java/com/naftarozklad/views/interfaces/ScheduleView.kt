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

	fun setListItems(lessons: List<Lesson>)

	fun onError(errorMessage: String)
}