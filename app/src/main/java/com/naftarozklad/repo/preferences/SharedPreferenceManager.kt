package com.naftarozklad.repo.preferences

import android.content.SharedPreferences
import com.naftarozklad.repo.models.Subgroup

/**
 * Created by Bohdan on 05.02.2018
 */
class SharedPreferenceManager constructor(private val sharedPreferences: SharedPreferences) {

	companion object {
		private val PREF_CURRENT_GROUP_NAME = "PREF_CURRENT_GROUP_NAME"
		private val PREF_CURRENT_SUBGROUP_ID = "PREF_CURRENT_SUBGROUP_ID"
	}

	var currentGroupName: String
		get() = sharedPreferences.getString(PREF_CURRENT_GROUP_NAME, "")
		set(value) {
			sharedPreferences.edit().putString(PREF_CURRENT_GROUP_NAME, value).apply()
		}

	var currentSubgroupId
		get() = sharedPreferences.getInt(PREF_CURRENT_SUBGROUP_ID, Subgroup.FIRST.id)
		set(value) {
			sharedPreferences.edit().putInt(PREF_CURRENT_SUBGROUP_ID, value).apply()
		}
}