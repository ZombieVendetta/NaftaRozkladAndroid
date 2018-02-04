package com.naftarozklad.repo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose

/**
 * Created by Bohdan.Shvets on 05.10.2017
 */
@Entity(tableName = "lessons")
class Lesson {

	@Expose
	@PrimaryKey(autoGenerate = true)
	var id: Int = 0

	@Expose
	@ColumnInfo(name = "group_id")
	var groupId: Int = 0

	var period: Int = 0

	@Expose
	var day: Int = 0

	var week: Int = 0

	var subgroup: Int = 0

	var type: String? = null

	var name: String? = null

	var teacher: String? = null

	var room: String? = null

	//region Generated
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Lesson

		if (id != other.id) return false
		if (groupId != other.groupId) return false
		if (period != other.period) return false
		if (day != other.day) return false
		if (week != other.week) return false
		if (subgroup != other.subgroup) return false
		if (type != other.type) return false
		if (name != other.name) return false
		if (teacher != other.teacher) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id
		result = 31 * result + groupId
		result = 31 * result + period
		result = 31 * result + day
		result = 31 * result + week
		result = 31 * result + subgroup
		result = 31 * result + (type?.hashCode() ?: 0)
		result = 31 * result + (name?.hashCode() ?: 0)
		result = 31 * result + (teacher?.hashCode() ?: 0)
		return result
	}

	//endregion
}