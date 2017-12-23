package com.naftarozklad.repo.external

import com.naftarozklad.repo.models.DayDTO
import com.naftarozklad.repo.models.Group
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */
interface WebApi {

	@GET("api/groups.php")
	fun getGroups(): Call<List<Group>>

	@GET("api/schedules.php")
	fun getSchedule(@Query("group_id") groupId: Int, @Query("week") week: Int = 0, @Query("subgroup") subgroup: Int = 0): Call<List<DayDTO>>
}