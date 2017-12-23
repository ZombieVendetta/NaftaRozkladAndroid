package com.naftarozklad.views.interfaces

import com.naftarozklad.repo.models.Group

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
interface GroupsView : View {

	fun setTextChangedAction(action: (String) -> Unit)

	fun setListItems(groups: List<Group>)

	fun getFilterText(): String

	fun setFilterText(filterText: String)

	fun setRefreshAction(action: () -> Unit)

	fun stopRefresh()

	fun onError(errorMessage: String)
}