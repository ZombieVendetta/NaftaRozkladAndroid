package com.naftarozklad.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.naftarozklad.R
import com.naftarozklad.RozkladApp
import com.naftarozklad.presenters.SettingsPresenter
import com.naftarozklad.views.interfaces.SettingsView
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SettingsActivity : AppCompatActivity(), SettingsView {

	@Inject
	lateinit var presenter: SettingsPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)

		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		RozkladApp.applicationComponent.inject(this)

		presenter.attachView(this)

		btnSelectGroup.setOnClickListener {
			startActivity<GroupsActivity>()
		}
	}

	override fun setWeeksSwapped(areSwapped: Boolean) {
		switchSwapWeeks.isChecked = areSwapped
	}

	override fun setSubgroups(subgroups: List<String>) {
		spinnerSubgroup.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subgroups)
	}

	override fun setSelectedSubgroup(subgroup: String) {
		spinnerSubgroup.apply {
			(0 until count).forEach { position -> takeIf { subgroup == getItemAtPosition(position) }?.setSelection(position) }
		}
	}

	override fun setGroupSelectedAction(groupSelectAction: (String) -> Unit) {
		spinnerSubgroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(parent: AdapterView<*>?) {}

			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				groupSelectAction(spinnerSubgroup.selectedItem as String)
			}
		}
	}
}