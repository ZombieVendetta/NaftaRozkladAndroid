package com.naftarozklad.repo.internal.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.naftarozklad.repo.models.Lesson

/**
 * Created by bohdan on 10/7/17
 */
@Dao
interface LessonsDAO {

	@Query("SELECT * FROM lessons")
	fun getLessons(): List<Lesson>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertLessons(lessons: List<Lesson>): List<Long>

	@Query("DELETE FROM lessons")
	fun clearLessons()
}