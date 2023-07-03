package com.example.streamwidetechtest.data.repository

import com.example.streamwidetechtest.data.contact_provider.ContactContentProvider
import com.example.streamwidetechtest.data.local.ContactsDao
import com.example.streamwidetechtest.data.mapper.toDomain
import com.example.streamwidetechtest.data.mapper.toLocal
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import com.example.streamwidetechtest.util.Resource

class ContactsRepositoryImpl(
    private val dao: ContactsDao,
    private val contactContentProvider: ContactContentProvider
) : ContactsRepository {
    override suspend fun getAllContacts(): Resource<List<Contact>> {
        return try {
            val contacts = contactContentProvider.getAllContacts()
            contacts.forEach {
                dao.insertNewContact(it.toLocal())
            }
            Resource.Success(
                dao.getAllContactsDao().map { contactEntity -> contactEntity.toDomain() }
            )
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun getContactDetails(id: Long): Resource<Contact> {
        return try {
            Resource.Success(dao.getContactById(id).toDomain())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun selectContactsByName(name: String): Resource<List<Contact>> {
        return try {
            Resource.Success(dao.selectContactsByName(name).map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun selectContactsByPhoneNumber(phoneNumber: String): Resource<List<Contact>> {
        return try {
            Resource.Success(dao.selectContactsByPhoneNumber(phoneNumber).map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun insetNewContact(contact: Contact): Resource<Unit> {
        return try {
            dao.insertNewContact(contact.toLocal())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun addNewContact(name: String, phoneNumber: String): Resource<Unit> {
        return contactContentProvider.addNewContact(name, phoneNumber)
    }
}