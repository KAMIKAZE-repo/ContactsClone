package com.example.streamwidetechtest.data.repository

import com.example.streamwidetechtest.data.local.ContactsDao
import com.example.streamwidetechtest.data.mapper.toDomain
import com.example.streamwidetechtest.data.mapper.toLocal
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactsRepositoryImpl(private val dao: ContactsDao) : ContactsRepository {
    override fun getAllContacts(): Flow<List<Contact>> {
        return dao.getAllContactsDao().map { it.map { contactEntity -> contactEntity.toDomain() } }
    }

    override suspend fun getContactDetails(id: String): Contact {
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