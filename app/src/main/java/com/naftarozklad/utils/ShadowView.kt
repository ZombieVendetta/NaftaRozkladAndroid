package com.naftarozklad.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.View

/**
 * Created by Bohdan on 03.02.2018
 */
class ShadowView @JvmOverloads constructor(
		context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

	private val paint = Paint()
	private lateinit var gradient: LinearGradient

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
		val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

		val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
		val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

		val width = if (widthMode == View.MeasureSpec.EXACTLY) widthSize else 0
		val height = if (heightMode == View.MeasureSpec.EXACTLY) heightSize else TypedValue.applyDimension(COMPLEX_UNIT_DIP, 4f, resources.displayMetrics).toInt()

		setMeasuredDimension(width, height)
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		gradient = LinearGradient(0f, 0f, 0f, h.toFloat(), Color.parseColor("#40000000"), Color.TRANSPARENT, Shader.TileMode.MIRROR)
	}

	override fun onDraw(canvas: Canvas?) {
		paint.shader = gradient
		canvas?.drawPaint(paint)
	}
}