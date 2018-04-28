package com.naftarozklad.dagger.modules

import android.content.Context
import com.naftarozklad.repo.preferences.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Singleton

/**
 * Created by Bohdan on 05.02.2018
 */
@Module
class SharedPreferenceManagerModule {

	@Provides
	@Singleton
	fun providePreferenceManager(context: Context) = SharedPreferenceManager(context.defaultSharedPreferences)
}