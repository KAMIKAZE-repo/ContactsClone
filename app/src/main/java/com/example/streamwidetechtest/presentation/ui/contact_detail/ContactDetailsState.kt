package com.example.streamwidetechtest.presentation.ui.contact_detail

import com.example.streamwidetechtest.domain.model.ContactDetails

data class ContactDetailsState(
    var data: ContactDetails? = null,
    var loading: Boolean = false
)
