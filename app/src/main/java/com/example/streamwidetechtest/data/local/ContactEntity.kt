package com.example.streamwidetechtest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact_Entity")
data class ContactEntity(
    @PrimaryKey
    val contactId: Long,
    val name: String,
    val phoneNumber: String,
)