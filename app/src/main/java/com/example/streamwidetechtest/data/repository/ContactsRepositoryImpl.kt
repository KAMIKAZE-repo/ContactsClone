package com.example.streamwidetechtest.data.repository

import android.content.Context
import com.example.streamwidetechtest.data.contact_provider.ContactContentProviderImpl
import com.example.streamwidetechtest.data.local.ContactsDao
import com.example.streamwidetechtest.data.mapper.toDomain
import com.example.streamwidetechtest.data.mapper.toLocal
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.repository.ContactsRepository

class ContactsRepositoryImpl (
    private val dao: ContactsDao,
) : ContactsRepository {
    override suspend fun getAllContacts(context: Context): List<Contact> {
        val contacts = ContactContentProviderImpl(context).getAllContacts()
        contacts.forEach {
            dao.insertNewContact(it.toLocal())
        }
        return dao.getAllContactsDao().map {contactEntity -> contactEntity.toDomain() }
    }

    override suspend fun getContactDetails(id: Long): Contact {
        return dao.getContactById(id).toDomain()
    }

    override suspend fun selectContactsByName(name: String): List<Contact> {
        return dao.selectContactsByName(name).map { it.toDomain() }
    }

    override suspend fun selectContactsByPhoneNumber(phoneNumber: String): List<Contact> {
        return dao.selectContactsByName(phoneNumber).map { it.toDomain() }
    }

    override suspend fun insetNewContact(contact: Contact) {
        dao.insertNewContact(contact.toLocal())
    }
}