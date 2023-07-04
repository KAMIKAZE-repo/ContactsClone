package com.example.streamwidetechtest.domain.model

data class ContactDetails(
    val id: Long,
    var name: String,
    var phoneNumber: String,
    var photoUri: String? = null,
    var email: String? = null
)
