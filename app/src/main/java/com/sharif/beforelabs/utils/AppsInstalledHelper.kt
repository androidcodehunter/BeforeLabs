package com.sharif.beforelabs.utils

import android.content.Context
import android.content.Intent

class AppsInstalledHelpe {

    fun readInstalledApps(context: Context): List<Pair<String, String>> {
        val allApps: MutableList<Pair<String, String>> = mutableListOf()
        val packageManager = context.packageManager
        val apps = packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }, 0)

        apps.forEach { resolveInfo ->
            val packageName = resolveInfo.activityInfo.packageName
            val activityName = resolveInfo.activityInfo.name
            if (!isExcludedPackage(packageName)) {
                allApps.add(Pair(packageName, resolveInfo.loadLabel(packageManager).toString()))
            }

        }
        return allApps
    }

    companion object {
        fun isExcludedPackage(packageName: String): Boolean {
            val excludedPackageNames: List<String> = listOf()
            return excludedPackageNames.contains(packageName)
        }
    }

}