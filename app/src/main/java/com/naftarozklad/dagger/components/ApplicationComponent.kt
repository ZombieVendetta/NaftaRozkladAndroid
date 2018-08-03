package com.naftarozklad.dagger.components

import android.content.Context
import com.naftarozklad.dagger.modules.ApplicationModule
import com.naftarozklad.dagger.modules.CacheModule
import com.naftarozklad.dagger.modules.RetrofitModule
import com.naftarozklad.dagger.modules.SharedPreferenceManagerModule
import com.naftarozklad.views.activities.GroupsActivity
import com.naftarozklad.views.activities.ScheduleActivity
import com.naftarozklad.views.activities.SettingsActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
@Singleton
@Component(modules = arrayOf(CacheModule::class, RetrofitModule::class, ApplicationModule::class, SharedPreferenceManagerModule::class))
interface ApplicationComponent {
	fun context(): Context

	fun inject(activity: GroupsActivity)

	fun inject(activity: ScheduleActivity)

	fun inject(activity: SettingsActivity)
}