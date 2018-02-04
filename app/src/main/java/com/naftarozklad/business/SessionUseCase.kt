package com.naftarozklad.business

import com.naftarozklad.repo.preferences.SharedPreferenceManager
import javax.inject.Inject

/**
 * Created by Bohdan on 05.02.2018
 */
class SessionUseCase @Inject constructor(
		private val sharedPreferenceManager: SharedPreferenceManager
) {

	fun getCurrentGroupName() = sharedPreferenceManager.currentGroupName

	fun setCurrentGroupName(groupName: String) {
		sharedPreferenceManager.currentGroupName = groupName
	}

	fun getCurrentSubgroupId() = sharedPreferenceManager.currentSubgroupId

	fun setCurrentSubgroupId(subgroupId: Int) {
		sharedPreferenceManager.currentSubgroupId = subgroupId
	}

	fun getCurrentWeekId() = sharedPreferenceManager.currentWeekId

	fun setCurrentWeekId(weekId: Int) {
		sharedPreferenceManager.currentWeekId = weekId
	}
}