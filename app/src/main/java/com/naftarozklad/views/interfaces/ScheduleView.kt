package com.naftarozklad.views.interfaces

import com.naftarozklad.repo.models.Lesson

/**
 * Created by bohdan on 10/21/17
 */
interface ScheduleView : View {

	fun onError(errorMessage: String)

	fun setLessons(lessons: Map<Int, List<Lesson>>)

	fun setRefreshAction(action : () -> Unit)

	fun setGroupName(name: String?)

	fun openGroupsView()
}