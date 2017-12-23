package com.naftarozklad

import android.app.Application
import com.naftarozklad.dagger.components.ApplicationComponent
import com.naftarozklad.dagger.components.DaggerApplicationComponent
import com.naftarozklad.dagger.modules.ApplicationModule
import com.naftarozklad.dagger.modules.CacheModule
import com.naftarozklad.dagger.modules.RetrofitModule

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
class RozkladApp : Application() {

	companion object {
		lateinit var applicationComponent: ApplicationComponent
	}

	override fun onCreate() {
		super.onCreate()

		applicationComponent = DaggerApplicationComponent.builder()
				.cacheModule(CacheModule())
				.retrofitModule(RetrofitModule())
				.applicationModule(ApplicationModule(this))
				.build()
	}
}