package com.naftarozklad.presenters

import com.naftarozklad.views.interfaces.View

/**
 * Created by bohdan on 10/4/17
 */
interface Presenter<in T : View> {

	fun attachView(view: T)

	fun detachView()
}