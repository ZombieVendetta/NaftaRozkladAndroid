package com.naftarozklad.business

import com.naftarozklad.R
import com.naftarozklad.repo.external.WebApi
import com.naftarozklad.repo.internal.GlobalCache
import com.naftarozklad.utils.isNetworkAvailable
import com.naftarozklad.utils.resolveErrorMessage
import com.naftarozklad.utils.resolveString
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 27.10.2017
 */

class SynchronizeGroupsUseCase @Inject constructor(
		private val webApi: WebApi,
		private val globalCache: GlobalCache
) {

	fun synchronizeGroups(callback: SynchronizeCallback?) = doAsync {
		if (!isNetworkAvailable()) {
			uiThread { callback?.onError(resolveString(R.string.lbl_no_network)) }
			return@doAsync
		}

		with(webApi.getGroups().execute()) {
			val groups = body()

			if (!isSuccessful) {
				uiThread { callback?.onError(resolveErrorMessage(code())) }
				return@doAsync
			}

			if (groups == null || groups.isEmpty()) {
				uiThread { callback?.onError(resolveString(R.string.lbl_no_data)) }
				return@doAsync
			}

			globalCache.insertGroups(groups.toMutableList())
			uiThread { callback?.onSuccess() }
		}
	}
}