package com.naftarozklad.repo.internal.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.naftarozklad.repo.models.Group
import com.naftarozklad.repo.models.Lesson

/**
 * Created by bohdan on 10/7/17
 */

@Database(entities = arrayOf(Group::class, Lesson::class), version = 1, exportSchema = false)
abstract class RozkladDatabase : RoomDatabase() {

	companion object {
		const val DATABASE_NAME = "rozklad_db"
	}

	abstract fun groupsDAO(): GroupsDAO

	abstract fun lessonsDAO(): LessonsDAO
}