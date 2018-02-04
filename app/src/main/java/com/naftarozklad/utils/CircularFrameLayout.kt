package com.naftarozklad.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Bohdan on 04.02.2018
 */
class CircularFrameLayout @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

	init {
		visibility = View.INVISIBLE
	}

	var animationDuration = 300L

	private var animator: ValueAnimator? = null

	var circleCenterX = 0f
	var circleCenterY = 0f

	private val path = Path()

	override fun dispatchDraw(canvas: Canvas?) {
		if (canvas == null)
			return

		val count = canvas.save()

		canvas.clipPath(path)
		super.dispatchDraw(canvas)

		canvas.restoreToCount(count)
	}

	@JvmOverloads
	fun setContentVisibility(isVisible: Boolean, animated: Boolean = false) {
		if ((visibility == View.VISIBLE) == isVisible)
			return

		if (!animated) {
			path.reset()
			path.addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)

			visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
			return
		}

		val circleRadius = calculateCircleRadius(circleCenterX, circleCenterY)

		animator?.cancel()

		val startValue = if (visibility == View.VISIBLE) circleRadius else 0f
		val endValue = if (visibility == View.VISIBLE) 0f else circleRadius

		this.animator = ValueAnimator.ofFloat(startValue, endValue)
				.apply { duration = animationDuration }
				.apply { interpolator = AccelerateDecelerateInterpolator() }
				.apply {
					addUpdateListener {
						path.reset()
						path.addCircle(circleCenterX, circleCenterY, it.animatedValue as Float, Path.Direction.CW)
						invalidate()
					}
				}
				.apply {
					addListener(object : Animator.AnimatorListener {
						override fun onAnimationRepeat(animation: Animator?) {}

						override fun onAnimationEnd(animation: Animator?) {
							animator = null

							if (!isVisible)
								visibility = View.INVISIBLE
						}

						override fun onAnimationCancel(animation: Animator?) {}

						override fun onAnimationStart(animation: Animator?) {
							if (isVisible)
								visibility = View.VISIBLE
						}
					})
				}
				.apply { start() }
	}

	private fun calculateCircleRadius(centerX: Float, centerY: Float): Float {
		val hypotenuse = { x: Float, y: Float -> sqrt(x.pow(2) + y.pow(2)) }

		val topLeft = hypotenuse(centerX, centerY)
		val topRight = hypotenuse(abs(width - centerX), centerY)
		val bottomLeft = hypotenuse(centerX, abs(height - centerY))
		val bottomRight = hypotenuse(abs(width - centerX), abs(height - centerY))

		return arrayOf(topLeft, topRight, bottomLeft, bottomRight).max() ?: 0f
	}
}