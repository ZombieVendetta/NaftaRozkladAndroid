package com.naftarozklad.repo.external

import com.naftarozklad.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
class WebApiBuilder {

	val BASE_URL = "http://rozklad.nung.edu.ua/application/"

	fun createWebApi(): WebApi {

		val clientBuilder = OkHttpClient.Builder()

		if (BuildConfig.DEBUG)
			clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

		val builder = Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(clientBuilder.build())
				.addConverterFactory(GsonConverterFactory.create())
				.build()

		return builder.create(WebApi::class.java)
	}
}