package com.naftarozklad.repo.models

import com.naftarozklad.R
import com.naftarozklad.utils.resolveString

/**
 * Created by bohdan on 10/7/17
 */
enum class Week(val id: Int, val description: String) {
	COMMON(0, ""),
	NUMERATOR(1, resolveString(R.string.lbl_numerator)),
	DENOMINATOR(2, resolveString(R.string.lbl_denominator))
}