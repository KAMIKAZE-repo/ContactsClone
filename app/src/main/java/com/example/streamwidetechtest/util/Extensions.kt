package com.example.streamwidetechtest.util

import android.content.Context
import android.content.pm.PackageManager

fun Context.hasPermission(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun String.createSqlPattern(): String = "%$this%"

fun String.isAllNumbers(): Boolean = matches(Regex("(\\+)?\\d+"))

