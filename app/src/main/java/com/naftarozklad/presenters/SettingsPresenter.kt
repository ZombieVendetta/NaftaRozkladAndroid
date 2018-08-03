package com.naftarozklad.presenters

import com.naftarozklad.business.SessionUseCase
import com.naftarozklad.views.interfaces.SettingsView
import javax.inject.Inject

class SettingsPresenter @Inject constructor(private val sessionUseCase: SessionUseCase) : Presenter<SettingsView> {

	private lateinit var settingsView: SettingsView

	override fun attachView(view: SettingsView) {
		settingsView = view

		settingsView.setSubgroups(sessionUseCase.getSubgroups().map { it.description })
		settingsView.setSelectedSubgroup(sessionUseCase.getCurrentSubgroup().description)
		settingsView.setGroupSelectedAction { selectedDescription ->
			sessionUseCase.setCurrentSubgroup(sessionUseCase.getSubgroups().first { it.description == selectedDescription }.id)
		}
	}

	override fun detachView() {

	}
}