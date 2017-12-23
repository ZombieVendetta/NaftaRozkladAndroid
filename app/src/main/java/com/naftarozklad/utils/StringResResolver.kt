package com.naftarozklad.utils

import android.support.annotation.StringRes
import com.naftarozklad.RozkladApp

/**
 * Created by bohdan on 10/7/17
 */
fun resolveString(@StringRes stringRes: Int): String = RozkladApp.applicationComponent.context().getString(stringRes)