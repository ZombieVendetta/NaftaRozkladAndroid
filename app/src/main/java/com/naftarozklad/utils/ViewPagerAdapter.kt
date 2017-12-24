package com.naftarozklad.utils

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference

/**
 * Created by Bohdan on 24.12.2017
 */
class ViewPagerAdapter(views: List<View>) : PagerAdapter() {

	private val views: List<WeakReference<View>> = views.map { WeakReference(it) }

	override fun instantiateItem(container: ViewGroup, position: Int): Any = views[position].get().also { container.addView(it) } ?: Any()

	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
		container.removeView(`object` as? View)
	}

	override fun isViewFromObject(view: View, `object`: Any) = view == `object`

	override fun getCount() = views.size
}