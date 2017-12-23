package com.naftarozklad.presenters

import com.naftarozklad.business.GroupsUseCase
import com.naftarozklad.business.InitCacheUseCase
import com.naftarozklad.business.SynchronizeCallback
import com.naftarozklad.business.SynchronizeGroupsUseCase
import com.naftarozklad.views.interfaces.GroupsView
import javax.inject.Inject

/**
 * Created by bohdan on 10/4/17
 */
class GroupsPresenter @Inject constructor(
		private val groupsUseCase: GroupsUseCase,
		private val initCacheUseCase: InitCacheUseCase,
		private val synchronizeGroupsUseCase: SynchronizeGroupsUseCase
) : Presenter<GroupsView> {

	lateinit var groupsView: GroupsView

	override fun attachView(view: GroupsView) {
		groupsView = view

		groupsView.setTextChangedAction { initList() }
		groupsView.setRefreshAction { synchronizeGroups() }

		initCacheUseCase.initInternalRepo().get()

		if (initCacheUseCase.isGroupsExist()) {
			initList()
			return
		}

		synchronizeGroups()
	}

	override fun detachView() {}

	private fun synchronizeGroups() {
		synchronizeGroupsUseCase.synchronizeGroups(object : SynchronizeCallback {
			override fun onSuccess() {
				groupsView.stopRefresh()
				initList()
			}

			override fun onError(errorMessage: String) {
				groupsView.stopRefresh()
				groupsView.onError(errorMessage)
			}
		})
	}

	private fun initList() = with(groupsView) {
		setListItems(groupsUseCase.getGroups(getFilterText()))
	}
}