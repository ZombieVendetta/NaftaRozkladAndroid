package com.naftarozklad.utils

import android.content.Context
import android.net.ConnectivityManager
import com.naftarozklad.R
import com.naftarozklad.RozkladApp

/**
 * Created by Bohdan.Shvets on 05.10.2017
 */

fun isNetworkAvailable(): Boolean {
	val connectivityManager = RozkladApp.applicationComponent.context().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val activeNetworkInfo = connectivityManager.activeNetworkInfo
	return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

		fun resolveErrorMessage(code: Int) = resolveString(
		when (code) {
			503 -> R.string.lbl_service_unavailable
			500 -> R.string.lbl_server_error
			else -> R.string.lbl_unknown_error
		}
)