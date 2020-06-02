package com.sharif.beforelabs

import android.content.Context
import android.content.Intent
import java.io.File

class AppsInstalledHelpe {

    fun readInstalledApps(context: Context): List<String> {
        val allApps: MutableList<String> = mutableListOf()
        val packageManager = context.packageManager
        val apps = packageManager.queryIntentActivities(Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }, 0)

        apps.forEach { resolveInfo ->
            val packageName = resolveInfo.activityInfo.packageName
            /*val activityName = resolveInfo.activityInfo.name
            if (!isExcludedPackage(packageName)) {
                allApps.add(resolveInfo.loadLabel(packageManager).toString())
            }*/
            allApps.add(packageName)
        }
        return allApps
    }

    companion object {
        fun isExcludedPackage(packageName: String): Boolean {
            val excludedPackageNames: List<String> = listOf()
            return excludedPackageNames.contains(packageName)
        }
    }

    private fun getAppSize(context:Context, packageName:String):Long
    {
        val pm = context.packageManager
        val applicationInfo = pm.getApplicationInfo(packageName, 0)
        val file = File(applicationInfo.publicSourceDir)
        val size = file.length()/1048576
        return size
    }

}