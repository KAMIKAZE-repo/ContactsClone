package com.example.streamwidetechtest.presentation.ui.contact_list

import com.example.streamwidetechtest.domain.model.Contact

data class ContactsListState(
    val contactList: List<Contact> = emptyList(),
    val isLoading: Boolean = false
)