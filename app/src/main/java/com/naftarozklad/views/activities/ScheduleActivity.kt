package com.naftarozklad.views.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.naftarozklad.R
import com.naftarozklad.RozkladApp
import com.naftarozklad.presenters.SchedulePresenter
import com.naftarozklad.repo.models.Lesson
import com.naftarozklad.views.interfaces.ScheduleView
import org.jetbrains.anko.contentView
import javax.inject.Inject

/**
 * Created by bohdan on 10/7/17
 */
class ScheduleActivity : AppCompatActivity(), ScheduleView {

	@Inject
	lateinit var presenter: SchedulePresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shedule)

		RozkladApp.applicationComponent.inject(this)

		presenter.attachView(this)
	}

	override fun getGroupId() = intent.getIntExtra(ScheduleView.EXTRA_GROUP_ID, 0)

	override fun getWeekId() = 0

	override fun getSubgroupId() = 0

	override fun setListItems(lessons: List<Lesson>) {}

	override fun onError(errorMessage: String) {
		contentView?.let { Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG).show() }
	}
}