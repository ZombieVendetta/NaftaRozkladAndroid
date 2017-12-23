package com.naftarozklad.repo.models

import com.naftarozklad.R
import com.naftarozklad.utils.resolveString

/**
 * Created by Bohdan.Shvets on 31.10.2017
 */
enum class Day(val id: Int, val description: String) {
	MONDAY(1, resolveString(R.string.lbl_monday)),
	TUESDAY(2, resolveString(R.string.lbl_tuesday)),
	WEDNESDAY(3, resolveString(R.string.lbl_wednesday)),
	THURSDAY(4, resolveString(R.string.lbl_thursday)),
	FRIDAY(5, resolveString(R.string.lbl_friday))
}