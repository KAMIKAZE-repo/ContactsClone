package com.example.streamwidetechtest.domain.repository

import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.model.ContactDetails
import com.example.streamwidetechtest.util.Resource

interface ContactsRepository {
    suspend fun getAllContacts(): Resource<List<Contact>>
    suspend fun getContactDetails(id: Long): Resource<ContactDetails>
    suspend fun selectContactsByName(name: String): Resource<List<Contact>>
    suspend fun selectContactsByPhoneNumber(phoneNumber: String): Resource<List<Contact>>
    suspend fun insetNewContact(contact: Contact): Resource<Unit>
    suspend fun addNewContact(name: String, phoneNumber: String): Resource<Unit>
}