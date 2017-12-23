package com.naftarozklad.business

/**
 * Created by Bohdan.Shvets on 27.10.2017
 */
interface SynchronizeCallback {

	fun onSuccess()

	fun onError(errorMessage: String)
}