package com.naftarozklad.dagger.modules

import android.content.Context
import com.naftarozklad.RozkladApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Bohdan.Shvets on 05.10.2017
 */
@Module
class ApplicationModule(private val application: RozkladApp) {

	@Provides
	@Singleton
	fun provideApplicationContext(): Context {
		return application
	}

	@Provides
	@Singleton
	fun provideApplication(): RozkladApp {
		return application
	}
}