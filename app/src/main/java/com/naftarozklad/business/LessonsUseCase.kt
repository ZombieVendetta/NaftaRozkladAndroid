package com.naftarozklad.business

import com.naftarozklad.repo.internal.GlobalCache
import com.naftarozklad.repo.models.Lesson
import java.util.*
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 31.10.2017
 */
class LessonsUseCase @Inject constructor(private val globalCache: GlobalCache) {

	fun isLessonsExist(groupId: Int) = globalCache.cachedLessons.any { it.groupId == groupId }

	fun getLessons(groupId: Int, weekId: Int, subgroupId: Int) = TreeMap<Int, List<Lesson>>(
			globalCache.cachedLessons
					.filter { it.groupId == groupId }
					.filter { it.week == weekId || it.week == 0 }
					.filter { it.subgroup == subgroupId || it.subgroup == 0 }
					.groupBy { it.day }
	)
}