package com.example.streamwidetechtest.domain.repository

import android.content.Context
import com.example.streamwidetechtest.domain.model.Contact

interface ContactsRepository {
    suspend fun getAllContacts(context: Context): List<Contact>
    suspend fun getContactDetails(id: Long): Contact
    suspend fun selectContactsByName(name: String): List<Contact>
    suspend fun selectContactsByPhoneNumber(phoneNumber: String): List<Contact>
    suspend fun insetNewContact(contact: Contact)
}