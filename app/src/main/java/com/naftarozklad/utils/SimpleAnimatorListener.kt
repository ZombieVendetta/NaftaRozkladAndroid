package com.naftarozklad.utils

import android.animation.Animator

/**
 * Created by Bohdan on 23.12.2017
 */
interface SimpleAnimatorListener : Animator.AnimatorListener {

	override fun onAnimationRepeat(animation: Animator?) {

	}

	override fun onAnimationEnd(animation: Animator?) {

	}

	override fun onAnimationCancel(animation: Animator?) {

	}

	override fun onAnimationStart(animation: Animator?) {

	}
}