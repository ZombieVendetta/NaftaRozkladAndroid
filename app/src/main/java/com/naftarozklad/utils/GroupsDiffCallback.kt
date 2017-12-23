package com.naftarozklad.utils

import android.support.v7.util.DiffUtil
import com.naftarozklad.repo.models.Group

/**
 * Created by bohdan on 10/21/17
 */
class GroupsDiffCallback(
		private val oldGroupsList: List<Group>,
		private val newGroupsList: List<Group>
) : DiffUtil.Callback() {

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
			oldGroupsList[oldItemPosition].id == newGroupsList[newItemPosition].id

	override fun getOldListSize(): Int = oldGroupsList.size

	override fun getNewListSize(): Int = newGroupsList.size

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
}