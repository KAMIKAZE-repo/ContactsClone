package com.example.streamwidetechtest.util

sealed class UIEvent{
    data class ShowSnackbar(val message: String): UIEvent()
}
