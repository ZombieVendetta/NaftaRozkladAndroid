package com.naftarozklad.repo.internal.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.naftarozklad.repo.models.Group

/**
 * Created by bohdan on 10/7/17
 */
@Dao
interface GroupsDAO {

	@Query("SELECT * FROM groups")
	fun getGroups(): List<Group>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertGroups(groups: List<Group>)

	@Query("DELETE FROM groups")
	fun clearGroups()
}