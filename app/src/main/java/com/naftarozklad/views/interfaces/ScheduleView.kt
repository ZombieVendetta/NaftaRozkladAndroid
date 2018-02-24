package com.naftarozklad.views.interfaces

import com.naftarozklad.repo.models.Lesson

/**
 * Created by bohdan on 10/21/17
 */
interface ScheduleView : View {

	fun getSubgroupId(): Int

	fun getWeekId(): Int

	fun onError(errorMessage: String)

	fun setLessons(lessons: Map<Int, List<Lesson>>)

	fun setRefreshAction(action : () -> Unit)

	fun setGroupName(name: String?)

	fun setSubgroupId(subgroupId: Int)

	fun setWeekId(weekId: Int)

	fun setSubgroupChangedAction(action: (subgroupId: Int) -> Unit)

	fun setWeekChangedAction(action: (weekId: Int) -> Unit)

	fun openGroupsView()
}