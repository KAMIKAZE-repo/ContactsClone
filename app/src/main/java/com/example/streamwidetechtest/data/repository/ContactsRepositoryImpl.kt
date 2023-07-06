package com.example.streamwidetechtest.data.repository

import com.example.streamwidetechtest.data.contact_provider.ContactContentProvider
import com.example.streamwidetechtest.data.local.data_source.ContactsDataSource
import com.example.streamwidetechtest.data.mapper.toDomain
import com.example.streamwidetechtest.data.mapper.toLocal
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.model.ContactDetails
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import com.example.streamwidetechtest.util.Resource

class ContactsRepositoryImpl(
    private val dataSource: ContactsDataSource,
    private val contactContentProvider: ContactContentProvider
) : ContactsRepository {
    override suspend fun getAllContacts(): Resource<List<Contact>> {
        return try {
            val contacts = contactContentProvider.getAllContacts()
            contacts.forEach {
                dataSource.insertNewContact(it.toLocal())
            }
            Resource.Success(
                dataSource.getAllContacts().map { contactEntity -> contactEntity.toDomain() }
            )
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun getContactDetails(id: Long): Resource<ContactDetails> {
        return try {
            Resource.Success(contactContentProvider.getContactDetailsById(id))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun selectContactsByName(name: String): Resource<List<Contact>> {
        return try {
            Resource.Success(dataSource.selectContactsByName(name).map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun selectContactsByPhoneNumber(phoneNumber: String): Resource<List<Contact>> {
        return try {
            Resource.Success(dataSource.selectContactsByPhoneNumber(phoneNumber).map { it.toDomain() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }

    override suspend fun insetNewContact(contact: Contact): Resource<Unit> {
        return try {
            dataSource.insertNewContact(contact.toLocal())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?.toString() ?: "Something went wrong!")
        }
    }
}