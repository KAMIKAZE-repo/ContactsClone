package com.example.streamwidetechtest.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.streamwidetechtest.presentation.component.RequestContactPermission
import com.example.streamwidetechtest.presentation.navigation.NavigationHost
import com.example.streamwidetechtest.ui.theme.StreamWideTechTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StreamWideTechTestTheme {
                val readContactPermission = Manifest.permission.READ_CONTACTS

                RequestContactPermission(
                    permission = readContactPermission,
                    onPermissionPermanentlyDenied = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = { openAppSettings() }) {
                                Text(text = "open settings")
                            }
                        }
                    }
                ) {
                    NavigationHost()
                }
            }
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}