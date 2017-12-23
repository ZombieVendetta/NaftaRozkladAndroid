package com.naftarozklad.business

import com.naftarozklad.R
import com.naftarozklad.repo.external.WebApi
import com.naftarozklad.repo.internal.GlobalCache
import com.naftarozklad.repo.models.Lesson
import com.naftarozklad.repo.models.Subgroup
import com.naftarozklad.repo.models.Week
import com.naftarozklad.utils.isNetworkAvailable
import com.naftarozklad.utils.resolveErrorMessage
import com.naftarozklad.utils.resolveString
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 30.10.2017
 */
class SynchronizeLessonsUseCase @Inject constructor(
		private val webApi: WebApi,
		private val globalCache: GlobalCache
) {

	fun synchronizeLessons(groupId: Int, callback: SynchronizeCallback?) = doAsync {
		if (!isNetworkAvailable()) {
			uiThread { callback?.onError(resolveString(R.string.lbl_no_network)) }
			return@doAsync
		}

		// BS: University's web api was RAPED! to show correct data on http://rozklad.nung.edu.ua/
		// BS: Probably it would be better to provide correct data from web api and adjust data on client side, wouldn't it?!?!
		// BS: Workaround - used HashSet to avoid duplicates
		// BS: SUGGESTION for web api developers: provide possibility to fetch all data with one query!
		// region Workaround
		val lessons = HashSet<Lesson>()

		var result = webApi.getSchedule(groupId = groupId, week = Week.NUMERATOR.id, subgroup = Subgroup.FIRST.id).execute()
		if (!result.isSuccessful) {
			uiThread { callback?.onError(resolveErrorMessage(result.code())) }
			return@doAsync
		}

		var body = result.body()
		lessons.addAll(body?.flatMap { day-> day.lessons.toList().onEach { lesson -> lesson.day = day.day } })

		result = webApi.getSchedule(groupId = groupId, week = Week.DENOMINATOR.id, subgroup = Subgroup.FIRST.id).execute()
		if (!result.isSuccessful) {
			uiThread { callback?.onError(resolveErrorMessage(result.code())) }
			return@doAsync
		}

		lessons.addAll(result.body()?.flatMap { it.lessons.toList().onEach { it.day = it.day } })

		result = webApi.getSchedule(groupId = groupId, week = Week.NUMERATOR.id, subgroup = Subgroup.SECOND.id).execute()
		if (!result.isSuccessful) {
			uiThread { callback?.onError(resolveErrorMessage(result.code())) }
			return@doAsync
		}

		lessons.addAll(result.body()?.flatMap { it.lessons.toList().onEach { it.day = it.day } })

		result = webApi.getSchedule(groupId = groupId, week = Week.DENOMINATOR.id, subgroup = Subgroup.SECOND.id).execute()
		if (!result.isSuccessful) {
			uiThread { callback?.onError(resolveErrorMessage(result.code())) }
			return@doAsync
		}

		lessons.addAll(result.body()?.flatMap { it.lessons.toList().onEach { it.day = it.day } })

		// endregion

		if (lessons.isEmpty()) {
			uiThread { callback?.onError(resolveString(R.string.lbl_no_data)) }
			return@doAsync
		}

		globalCache.insertLessons(lessons.onEach { it.groupId = groupId }.toList())
		uiThread { callback?.onSuccess() }
	}

	private fun <T> MutableSet<T>.addAll(elements: Collection<T>?) {
		elements?.let { this.addAll(elements) }
	}
}