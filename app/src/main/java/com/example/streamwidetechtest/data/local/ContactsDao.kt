package com.example.streamwidetechtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contact_entity")
    suspend fun getAllContactsDao(): List<ContactEntity>

    @Query("SELECT * FROM Contact_Entity WHERE contactId = :contactId")
    suspend fun getContactById(contactId: Long): ContactEntity

    @Query("SELECT * FROM Contact_Entity WHERE name = :name")
    suspend fun selectContactsByName(name: String): List<ContactEntity>
    @Query("SELECT * FROM Contact_Entity WHERE name = :phoneNumber")
    suspend fun selectContactsByPhoneNumber(phoneNumber: String): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewContact(contact: ContactEntity)
}