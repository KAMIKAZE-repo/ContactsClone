package com.example.streamwidetechtest.presentation.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.streamwidetechtest.util.hasPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestContactPermission(
    permission: String,
    onPermissionPermanentlyDenied: @Composable () -> Unit,
    onPermissionGrantedContent: @Composable () -> Unit,
) {
    val permissionState = rememberPermissionState(permission)
    val context = LocalContext.current

    LaunchedEffect(Unit){
        if (!context.hasPermission(permission)) {
            permissionState.launchPermissionRequest()
        }
    }

    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            onPermissionGrantedContent()
        }

        is PermissionStatus.Denied -> {
            if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                PermissionDialog {
                    permissionState.launchPermissionRequest()
                }
            } else {
                onPermissionPermanentlyDenied()
            }
        }
    }
}