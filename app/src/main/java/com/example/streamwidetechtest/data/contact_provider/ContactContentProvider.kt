package com.example.streamwidetechtest.data.contact_provider

import com.example.streamwidetechtest.domain.model.Contact

interface ContactContentProvider {
    suspend fun getAllContacts(): List<Contact>
}