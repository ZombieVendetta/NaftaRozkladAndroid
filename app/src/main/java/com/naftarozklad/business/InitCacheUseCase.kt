package com.naftarozklad.business

import com.naftarozklad.repo.internal.GlobalCache
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by bohdan on 10/4/17
 */

class InitCacheUseCase @Inject constructor(private val globalCache: GlobalCache) {

	fun isGroupsExist() = !globalCache.cachedGroups.isEmpty()

	fun isInitialized() = globalCache.isInitialized()

	fun initInternalRepo(callback: () -> Unit) = doAsync {
		globalCache.initDatabase()
		uiThread { callback() }
	}
}