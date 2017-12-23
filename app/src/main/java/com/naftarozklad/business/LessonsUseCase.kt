package com.naftarozklad.business

import com.naftarozklad.repo.internal.GlobalCache
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 31.10.2017
 */
class LessonsUseCase @Inject constructor(private val globalCache: GlobalCache) {

	fun isLessonsExist(groupId: Int) = globalCache.cachedLessons.any { it.groupId == groupId }

	fun getLessons(groupId: Int, weekId: Int, subgroupId: Int) = globalCache.cachedLessons.filter { it.groupId == groupId }.filter { it.week == weekId }.filter { it.subgroup == subgroupId }
}