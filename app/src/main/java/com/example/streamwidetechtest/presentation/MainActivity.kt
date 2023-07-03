package com.example.streamwidetechtest.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.streamwidetechtest.presentation.component.RequestContactPermission
import com.example.streamwidetechtest.presentation.ui.contact_list.ContactList
import com.example.streamwidetechtest.ui.theme.StreamWideTechTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreamWideTechTestTheme {
                RequestContactPermission {
//                    openAppSettings()
                }
                ContactList()
            }
        }
    }

    companion object {
        val permission = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}