package com.naftarozklad.views.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.naftarozklad.R
import com.naftarozklad.RozkladApp
import com.naftarozklad.presenters.GroupsPresenter
import com.naftarozklad.repo.models.Group
import com.naftarozklad.utils.SimpleTextWatcher
import com.naftarozklad.utils.hideKeyboard
import com.naftarozklad.views.interfaces.GroupsView
import com.naftarozklad.views.lists.adapters.GroupsAdapter
import kotlinx.android.synthetic.main.activity_groups.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.contentView
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class GroupsActivity : AppCompatActivity(), GroupsView {

	@Inject
	lateinit var presenter: GroupsPresenter

	private val adapter = GroupsAdapter()

	private lateinit var textChangedAction: (String) -> Unit
	private lateinit var groupSelectedAction: (Int) -> Unit

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_groups)
		RozkladApp.applicationComponent.inject(this)

		setSupportActionBar(toolbar)

		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = adapter
		recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

		etSearch.addTextChangedListener(object : SimpleTextWatcher() {
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				s?.toString()?.let { text -> textChangedAction(text) }
			}
		})

		adapter.setSelectionCallback { groupId ->
			groupSelectedAction(groupId)
			openScheduleActivity()
		}

		ivCloseSearch.setOnClickListener {
			switchSearchContainerVisibility()
			etSearch.setText("")
			etSearch.hideKeyboard()
		}

		recyclerView.setOnTouchListener { _, _ -> etSearch.hideKeyboard().run { false } }

		presenter.attachView(this)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_groups, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId != R.id.search)
			return false

		switchSearchContainerVisibility()
		etSearch.requestFocus()
		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		finish()
		return true
	}

	override fun onBackPressed() {
		if (flSearchContainer.visibility != View.VISIBLE)
			return super.onBackPressed()

		switchSearchContainerVisibility()
		etSearch.setText("")
	}

	override fun setTextChangedAction(action: (String) -> Unit) {
		textChangedAction = action
	}

	override fun setGroupSelectedAction(action: (Int) -> Unit) {
		groupSelectedAction = action
	}

	override fun setRefreshAction(action: () -> Unit) {
		swipeRefreshLayout.setOnRefreshListener(action)
	}

	override fun setNavigationBackEnabled(enabled: Boolean) {
		supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
	}

	override fun setListItems(groups: List<Group>) {
		adapter.setGroups(groups)
	}

	override fun stopRefresh() {
		swipeRefreshLayout.isRefreshing = false
	}

	override fun getFilterText() = etSearch.text.toString()

	override fun setFilterText(filterText: String) = etSearch.setText(filterText)

	override fun onError(errorMessage: String) {
		contentView?.let { Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG).show() }
	}

	private fun openScheduleActivity() {
		startActivity(intentFor<ScheduleActivity>().clearTop())
		finish()
	}

	private fun switchSearchContainerVisibility() {
		flSearchContainer.circleCenterX = flSearchContainer.width.toFloat()
		flSearchContainer.circleCenterY = flSearchContainer.height / 2 + flSearchContainer.y
		flSearchContainer.setContentVisibility(flSearchContainer.visibility != View.VISIBLE, true)
	}
}
