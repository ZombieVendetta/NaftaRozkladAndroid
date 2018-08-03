package com.naftarozklad.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.naftarozklad.R
import com.naftarozklad.views.interfaces.SettingsView
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsView {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_settings)

		setSupportActionBar(toolbar)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
}