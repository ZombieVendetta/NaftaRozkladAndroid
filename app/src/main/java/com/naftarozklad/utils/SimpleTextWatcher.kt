package com.naftarozklad.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by bohdan on 10/21/17
 */
abstract class SimpleTextWatcher : TextWatcher {
	override fun afterTextChanged(s: Editable?) {
	}

	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
	}
}