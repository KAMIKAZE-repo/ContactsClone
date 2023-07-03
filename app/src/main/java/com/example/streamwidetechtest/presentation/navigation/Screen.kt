package com.example.streamwidetechtest.presentation.navigation

sealed class Screen(val route: String){
    object ContactsScreen : Screen("contacts_screen")
    object ContactDetailScreen : Screen("contact_detail_screen")
}