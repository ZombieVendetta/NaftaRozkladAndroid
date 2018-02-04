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

		if (!initCacheUseCase.isInitialized()) {
			initCacheUseCase.initInternalRepo {
				attachView(groupsView)
			}

			return
		}

		groupsView.setTextChangedAction { setListItems() }
		groupsView.setRefreshAction { synchronizeGroups() }

		if (initCacheUseCase.isGroupsExist()) {
			setListItems()
			return
		}

		synchronizeGroups()
	}

	override fun detachView() {}

	private fun synchronizeGroups() {
		synchronizeGroupsUseCase.synchronizeGroups(object : SynchronizeCallback {
			override fun onSuccess() {
				groupsView.stopRefresh()
				setListItems()
			}

			override fun onError(errorMessage: String) {
				groupsView.stopRefresh()
				groupsView.onError(errorMessage)
			}
		})
	}

	private fun setListItems() = with(groupsView) {
		setListItems(groupsUseCase.getGroups(getFilterText()))
	}
}