package com.example.streamwidetechtest.domain.repository

interface ContactsRepository {
    fun getAllContacts()
    fun getContactDetails(id: Int)
    fun selectContactsByName(name: String)
    fun selectContactsByPhoneNumber(phoneNumber: String)
}