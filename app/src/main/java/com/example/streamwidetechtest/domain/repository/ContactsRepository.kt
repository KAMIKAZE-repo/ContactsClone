package com.example.streamwidetechtest.domain.repository

import com.example.streamwidetechtest.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getAllContacts(): Flow<List<Contact>>
    suspend fun getContactDetails(id: String): Contact
    suspend fun selectContactsByName(name: String): List<Contact>
    suspend fun selectContactsByPhoneNumber(phoneNumber: String): List<Contact>
    suspend fun insetNewContact(contact: Contact)
}