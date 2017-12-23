package com.naftarozklad.repo.internal.db

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import javax.inject.Inject

/**
 * Created by bohdan on 10/7/17
 */
class DBManager @Inject constructor(var context: Context) {

	lateinit var rozkladDatabase: RozkladDatabase

	fun initDatabase() {
		Log.d(DBManager::class.java.simpleName, "Init Database start")

		rozkladDatabase = Room.databaseBuilder(context, RozkladDatabase::class.java, RozkladDatabase.DATABASE_NAME).build()

		Log.d(DBManager::class.java.simpleName, "Init Database end")
	}
}