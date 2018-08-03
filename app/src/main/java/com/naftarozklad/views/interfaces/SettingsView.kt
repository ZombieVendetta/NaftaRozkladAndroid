package com.naftarozklad.views.interfaces

interface SettingsView: View {

	fun setWeeksSwapped(areSwapped: Boolean)

	fun setSubgroups(subgroups: List<String>)

	fun setSelectedSubgroup(subgroup: String)

	fun setGroupSelectedAction(groupSelectAction: (String) -> Unit)
}