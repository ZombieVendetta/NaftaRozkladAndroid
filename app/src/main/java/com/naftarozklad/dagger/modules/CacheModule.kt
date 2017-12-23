package com.naftarozklad.dagger.modules

import com.naftarozklad.repo.internal.GlobalCache
import com.naftarozklad.repo.internal.db.DBManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
@Module
class CacheModule {

	@Provides
	@Singleton
	fun provideGlobalCache(dbManager: DBManager): GlobalCache = GlobalCache(dbManager)
}