package com.naftarozklad.dagger.modules

import com.naftarozklad.repo.external.WebApi
import com.naftarozklad.repo.external.WebApiBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
@Module
class RetrofitModule {

	@Provides
	@Singleton
	fun provideWebApi(): WebApi = WebApiBuilder().createWebApi()
}