package com.naftarozklad.utils

import com.naftarozklad.repo.models.Week
import java.util.*

fun getCurrentWeek(): Week {
	val currentCalendar = GregorianCalendar()
	val calculationCalendar = GregorianCalendar()

	calculationCalendar.set(Calendar.MONTH, if (calculationCalendar.get(Calendar.MONTH) in 2..8) 2 else 9)

	return if (currentCalendar.get(Calendar.WEEK_OF_YEAR) - calculationCalendar.get(Calendar.WEEK_OF_YEAR) % 2 == 0) Week.NUMERATOR else Week.DENOMINATOR
}

//fun getCurrentDayOfWeek(): Day {
//	GregorianCalendar().get(Calendar.DAY_OF_WEEK)
//}