package com.example.streamwidetechtest.presentation.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.example.streamwidetechtest.presentation.MainActivity.Companion.permission
import com.example.streamwidetechtest.util.hasMultiPermissions

@Composable
fun RequestContactPermission(
    onPermissionDenied: () -> Unit,
) {
    val context = LocalContext.current

    //val permission = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)

    val readContactPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.forEach { (t, u) ->
                if (!u) {
                    onPermissionDenied()
                }
            }
        }
    )

    SideEffect {
        if (context.hasMultiPermissions(permission)) {
            readContactPermissionResultLauncher.launch(permission)
        }
    }
}