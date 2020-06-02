package com.sharif.beforelabs.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sharif.beforelabs.utils.AppsInstalledHelpe
import com.sharif.beforelabs.R
import kotlinx.android.synthetic.main.activity_main.*

const val UNINSTALL_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {
    private val packageListAdapter = PackageListAdapter()
    private val appsInstalledHelpe =
        AppsInstalledHelpe()

    private lateinit var selectedAppName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.apply {
            adapter = packageListAdapter
        }
        packageListAdapter.submitList(appsInstalledHelpe.readInstalledApps(this))

        packageListAdapter.onItemClickListener = { packageName, appName ->
            Log.d("TAG", "package $packageName")
            //package:com.example.myapplication
            uninstallPackageWithResult(packageName)
            selectedAppName = appName
        }

    }

    /**
     * Uninstall provided package name through activity result. Grab the success message in ActivityResult
     * Starting with API 25, calling ACTION_INSTALL_PACKAGE will require the signature level REQUEST_INSTALL_PACKAGES permission.
     * Likewise, starting with API 28 (Android P), calling ACTION_UNINSTALL_PACKAGE will require the non-dangerous REQUEST_DELETE_PACKAGES permission.
     * At least according to the docs.
     *
     * @param packageName The package to be uninstalled.
     * @param appName The name of the app.
     */
    private fun uninstallPackageWithResult(packageName: String){
        val intent = Intent(Intent.ACTION_DELETE).apply {
            data = Uri.parse("package:$packageName")
            putExtra(Intent.EXTRA_RETURN_RESULT, true)
        }
        startActivityForResult(intent, UNINSTALL_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UNINSTALL_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    packageListAdapter.submitList(appsInstalledHelpe.readInstalledApps(this))
                    Toast.makeText(this, "$selectedAppName uninstalled", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onActivityResult: user accepted the (un)install")
                }
                Activity.RESULT_CANCELED -> {
                    Log.d("TAG", "onActivityResult: user canceled the (un)install")
                }
                Activity.RESULT_FIRST_USER -> {
                    Log.d("TAG", "onActivityResult: failed to (un)install")
                }
            }
        }
    }

}



