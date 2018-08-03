package com.naftarozklad.business

import com.naftarozklad.repo.models.Subgroup
import com.naftarozklad.repo.preferences.SharedPreferenceManager
import javax.inject.Inject

/**
 * Created by Bohdan on 05.02.2018
 */
class SessionUseCase @Inject constructor(private val sharedPreferenceManager: SharedPreferenceManager) {

	fun getCurrentGroupName() = sharedPreferenceManager.currentGroupName

	fun setCurrentGroupName(groupName: String) {
		sharedPreferenceManager.currentGroupName = groupName
	}

	fun getSubgroups() = arrayListOf(Subgroup.FIRST, Subgroup.SECOND)

	fun getCurrentSubgroup() = Subgroup.values().first { it.id == sharedPreferenceManager.currentSubgroupId }

	fun setCurrentSubgroup(subgroupId: Int) {
		sharedPreferenceManager.currentSubgroupId = subgroupId
	}
}