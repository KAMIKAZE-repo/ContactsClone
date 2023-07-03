package com.example.streamwidetechtest.data.contact_provider

import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.util.Resource

interface ContactContentProvider {
    suspend fun getAllContacts(): List<Contact>
    suspend fun getContactDetailsById(contactId: Long)
    suspend fun addNewContact(displayName: String, phoneNumber: String): Resource<Unit>
}