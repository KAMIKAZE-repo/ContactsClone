package com.example.streamwidetechtest.data.local.data_source

import com.example.streamwidetechtest.data.local.ContactEntity

interface ContactsDataSource {
    suspend fun getAllContacts(): List<ContactEntity>

    suspend fun getContactById(contactId: Long): ContactEntity

    suspend fun selectContactsByName(name: String): List<ContactEntity>

    suspend fun selectContactsByPhoneNumber(phoneNumber: String): List<ContactEntity>

    suspend fun insertNewContact(contact: ContactEntity)
}