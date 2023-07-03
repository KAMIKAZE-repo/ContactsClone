package com.example.streamwidetechtest.presentation.ui.contact_detail

import com.example.streamwidetechtest.domain.model.Contact

data class ContactDetailsState(
    var data: Contact? = null,
    var loading: Boolean = false
)
