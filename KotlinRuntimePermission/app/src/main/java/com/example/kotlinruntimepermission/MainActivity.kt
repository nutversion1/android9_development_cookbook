package com.example.kotlinruntimepermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_SEND_SMS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun checkPermission(permission: String) : Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(
            this, permission)

        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(permissionName: String, permissionRequestCode: Int) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permissionName),
            permissionRequestCode)
    }

    private fun showExplanation(
        title: String,
        message: String,
        permission: String,
        permissionRequestCode: Int
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, id ->
                requestPermissions(
                    permission,
                    permissionRequestCode
                )
            }
        builder.create().show()
    }

    fun doSomething(view: View) {
        if (!checkPermission(Manifest.permission.SEND_SMS)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
                showExplanation(
                    "Permission Needed",
                    "Rationale",
                    Manifest.permission.SEND_SMS,
                    REQUEST_PERMISSION_SEND_SMS
                )
            } else {
                requestPermissions(
                    Manifest.permission.SEND_SMS,
                    REQUEST_PERMISSION_SEND_SMS
                )
            }
        } else {
            Toast.makeText(
                this@MainActivity,
                "Permission (already) Granted!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_SEND_SMS ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this@MainActivity,
                        "Granted!",
                        Toast.LENGTH_SHORT
                    ).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Denied!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}