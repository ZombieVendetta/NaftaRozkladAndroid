package com.naftarozklad.views.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.animation.AlphaAnimation
import android.widget.TextView
import com.naftarozklad.R
import com.naftarozklad.RozkladApp
import com.naftarozklad.presenters.SchedulePresenter
import com.naftarozklad.repo.models.Day
import com.naftarozklad.repo.models.Lesson
import com.naftarozklad.repo.models.Subgroup
import com.naftarozklad.repo.models.Week
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

		fabSettings.setOnClickListener {
			if (!llSettings.isAnimating())
				switchSettingsViewVisibility()
		}

		viewOverlay.setOnTouchListener(fun(_: View, _: MotionEvent): Boolean {
			if (!llSettings.isAnimating() && llSettings.visibility == View.VISIBLE)
				switchSettingsViewVisibility()

			return false
		})

		btnSelectGroup.setOnClickListener {
			startActivity<GroupsActivity>()
		}

		presenter.attachView(this)
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)

		viewOverlay.alpha = 0f
		llSettings.setContentVisibility(false, false)
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

	override fun onBackPressed() {
		if (llSettings.isAnimating())
			return

		if (llSettings.visibility == View.VISIBLE) {
			switchSettingsViewVisibility()
			return
		}

		super.onBackPressed()
	}

	override fun getSubgroupId() = if (rbFirstSubgroup.isChecked) Subgroup.FIRST.id else Subgroup.SECOND.id

	override fun getWeekId() = if (rbNumerator.isChecked) Week.NUMERATOR.id else Week.DENOMINATOR.id

	override fun onError(errorMessage: String) {
		coordinatorLayout?.let { Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG).show() }
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

	override fun setSubgroupId(subgroupId: Int) {
		when (subgroupId) {
			Subgroup.FIRST.id -> rgSegmentedGroup.check(R.id.rbFirstSubgroup)
			Subgroup.SECOND.id -> rgSegmentedGroup.check(R.id.rbSecondSubgroup)
			else -> rgSegmentedGroup.check(R.id.rbFirstSubgroup)
		}
	}

	override fun setWeekId(weekId: Int) {
		when (weekId) {
			Week.NUMERATOR.id -> rgSegmentedWeek.check(R.id.rbNumerator)
			Week.DENOMINATOR.id -> rgSegmentedWeek.check(R.id.rbDenominator)
			else -> rgSegmentedWeek.check(R.id.rbNumerator)
		}
	}

	override fun setSubgroupChangedAction(action: (subgroupId: Int) -> Unit) {
		rbFirstSubgroup.setOnClickListener {
			action(Subgroup.FIRST.id)
		}

		rbSecondSubgroup.setOnClickListener {
			action(Subgroup.SECOND.id)
		}
	}

	override fun setWeekChangedAction(action: (weekId: Int) -> Unit) {
		rbNumerator.setOnClickListener {
			action(Week.NUMERATOR.id)
		}

		rbDenominator.setOnClickListener {
			action(Week.DENOMINATOR.id)
		}
	}

	override fun openGroupsView() {
		startActivity<GroupsActivity>()
		finish()
	}

	private fun switchSettingsViewVisibility() {
		val startValue = if (llSettings.visibility == View.VISIBLE) 1f else 0f
		val endValue = if (llSettings.visibility == View.VISIBLE) 0f else 1f

		viewOverlay.animation?.cancel()
		viewOverlay.alpha = 1f
		viewOverlay.animation = AlphaAnimation(startValue, endValue)
				.apply { duration = llSettings.animationDuration }
				.apply { fillBefore = true }
				.apply { fillAfter = true }
				.apply { start() }

		llSettings.circleCenterX = fabSettings.x + fabSettings.width / 2
		llSettings.circleCenterY = fabSettings.y + fabSettings.height / 2 - llSettings.y
		llSettings.setContentVisibility(llSettings.visibility != View.VISIBLE, true)
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