package com.example.streamwidetechtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.streamwidetechtest.presentation.ui.contact_detail.ContactDetails
import com.example.streamwidetechtest.presentation.ui.contact_list.ContactList

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ContactsScreen.route) {
        composable(Screen.ContactsScreen.route) {
            ContactList(navController = navController)
        }
        composable(Screen.ContactDetailScreen.route + "/{contactId}") {
            ContactDetails(
                navController, contactId = it.arguments!!.getString("contactId")!!
            )
        }
    }
}