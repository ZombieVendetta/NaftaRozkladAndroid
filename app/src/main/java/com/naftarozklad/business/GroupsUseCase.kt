package com.naftarozklad.business

import android.text.TextUtils
import com.naftarozklad.repo.internal.GlobalCache
import com.naftarozklad.repo.models.Group
import javax.inject.Inject

/**
 * Created by bohdan on 10/4/17
 */
class GroupsUseCase @Inject constructor(private val globalCache: GlobalCache) {

	fun getGroups(filterString: String): List<Group> = globalCache.cachedGroups.filter { it.name.contains(filterString, true) }

	fun getGroupById(groupId: Int) = globalCache.cachedGroups.firstOrNull { group -> group.id == groupId }

	fun getGroupByName(groupName: String) = globalCache.cachedGroups.firstOrNull { group -> TextUtils.equals(group.name, groupName) }
}