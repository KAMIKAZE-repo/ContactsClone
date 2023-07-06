package com.example.streamwidetechtest.data.local.data_source

import com.example.streamwidetechtest.data.local.ContactEntity
import com.example.streamwidetechtest.data.local.ContactsDao

class ContactsDataSourceImpl(private val dao: ContactsDao): ContactsDataSource {
    override suspend fun getAllContacts(): List<ContactEntity> {
        return dao.getAllContacts()
    }

    override suspend fun getContactById(contactId: Long): ContactEntity {
        return dao.getContactById(contactId)
    }

    override suspend fun selectContactsByName(name: String): List<ContactEntity> {
        return dao.selectContactsByName(name)
    }

    override suspend fun selectContactsByPhoneNumber(phoneNumber: String): List<ContactEntity> {
        return dao.selectContactsByPhoneNumber(phoneNumber)
    }

    override suspend fun insertNewContact(contact: ContactEntity) {
        dao.insertNewContact(contact)
    }
}