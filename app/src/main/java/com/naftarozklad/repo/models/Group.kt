package com.naftarozklad.repo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Bohdan.Shvets on 04.10.2017
 */

@Entity(tableName = "groups")
class Group {

	@PrimaryKey
	var id: Int = 0

	lateinit var name: String

	lateinit var faculty: String

	@SerializedName("has_subgroups")
	@ColumnInfo(name = "has_subgroups")
	var hasSubgroups: Boolean = false
}