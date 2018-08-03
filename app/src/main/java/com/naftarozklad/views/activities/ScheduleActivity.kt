package com.naftarozklad.views.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.naftarozklad.R
import com.naftarozklad.RozkladApp
import com.naftarozklad.presenters.SchedulePresenter
import com.naftarozklad.repo.models.Day
import com.naftarozklad.repo.models.Lesson
import com.naftarozklad.utils.ViewPagerAdapter
import com.naftarozklad.utils.resolveString
import com.naftarozklad.views.interfaces.ScheduleView
import kotlinx.android.synthetic.main.activity_shedule.*
import kotlinx.android.synthetic.main.list_item_lesson.view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by bohdan on 10/7/17
 */
class ScheduleActivity : AppCompatActivity(), ScheduleView {

	@Inject
	lateinit var presenter: SchedulePresenter

	private lateinit var recyclerViews: List<RecyclerView>

	private lateinit var refreshAction: () -> Unit

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shedule)

		RozkladApp.applicationComponent.inject(this)

		setSupportActionBar(toolbar)
	}

	override fun onStart() {
		super.onStart()
		presenter.attachView(this)
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)

		presenter.attachView(this)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_schedule, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		item?.run {
			when (itemId) {
				R.id.navSync -> refreshAction()
				R.id.navSettings -> startActivity<SettingsActivity>()
				else -> return false
			}
		}.let { return true }
	}

	override fun onError(errorMessage: String) {
		viewPager?.let { Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG).show() }
	}

	override fun setLessons(lessons: Map<Int, List<Lesson>>) {
		recyclerViews = List(lessons.size) { index ->
			RecyclerView(this)
					.apply { layoutManager = LinearLayoutManager(this@ScheduleActivity) }
					.apply { adapter = RecyclerViewAdapter(lessons.values.elementAt(index)) }
		}
		viewPager.adapter = ViewPagerAdapter(recyclerViews)
		tabLayout.setupWithViewPager(viewPager)

		var counter = 0
		lessons.forEach { (dayId, _) -> tabLayout.getTabAt(counter++)?.text = Day.values().firstOrNull { it.id == dayId }?.description }
	}

	override fun setRefreshAction(action: () -> Unit) {
		refreshAction = action
	}

	override fun setGroupName(name: String?) {
		supportActionBar?.title = name ?: resolveString(R.string.lbl_unknown_group)
	}

	override fun openGroupsView() {
		startActivity<GroupsActivity>()
		finish()
	}

	private class RecyclerViewAdapter(private val lessons: List<Lesson>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
		override fun getItemCount() = lessons.size

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
			val lesson = lessons[position]
			holder.lessonNumber.text = lesson.period.toString()
			holder.lessonType.text = lesson.type
			holder.lessonName.text = lesson.name
			holder.lecturer.text = lesson.teacher
			holder.room.text = lesson.room
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_lesson, parent, false))

		private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
			val lessonNumber: TextView = itemView.tvLessonNumber
			val lessonType: TextView = itemView.tvLessonType
			val lessonName: TextView = itemView.tvLessonName
			val lecturer: TextView = itemView.tvLecturer
			val room: TextView = itemView.tvRoom
		}
	}
}