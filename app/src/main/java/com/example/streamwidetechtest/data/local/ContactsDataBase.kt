package com.example.streamwidetechtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactsDataBase: RoomDatabase() {
    abstract val contactsDao: ContactsDao

    companion object {
        const val DATABASE_NAME = "contact_db"
    }
}