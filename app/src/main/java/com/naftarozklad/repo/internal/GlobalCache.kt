package com.naftarozklad.repo.internal

import com.naftarozklad.repo.internal.db.DBManager
import com.naftarozklad.repo.internal.db.GroupsDAO
import com.naftarozklad.repo.internal.db.LessonsDAO
import com.naftarozklad.repo.models.Group
import com.naftarozklad.repo.models.Lesson
import org.jetbrains.anko.collections.forEachWithIndex
import javax.inject.Inject

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
class GlobalCache @Inject constructor(private val dbManager: DBManager) {

	lateinit var cachedGroups: MutableList<Group>
	lateinit var cachedLessons: MutableList<Lesson>

	fun initDatabase() {
		dbManager.initDatabase()
		cachedGroups = groupsDAO().getGroups().toMutableList()
		cachedLessons = lessonsDAO().getLessons().toMutableList()
	}

	fun insertGroups(groups: List<Group>) {
		cachedGroups = groups.sortedBy { it.id }.toMutableList()
		groupsDAO().clearGroups()
		groupsDAO().insertGroups(cachedGroups)
	}

	fun insertLessons(lessons: List<Lesson>) {
		cachedLessons = lessons.toMutableList()
		lessonsDAO().clearLessons()
		lessonsDAO().insertLessons(cachedLessons).forEachWithIndex { index: Int, id: Long -> cachedLessons[index].id = id.toInt() }
	}

	private fun GlobalCache.groupsDAO(): GroupsDAO = dbManager.rozkladDatabase.groupsDAO()
	private fun GlobalCache.lessonsDAO(): LessonsDAO = dbManager.rozkladDatabase.lessonsDAO()
}