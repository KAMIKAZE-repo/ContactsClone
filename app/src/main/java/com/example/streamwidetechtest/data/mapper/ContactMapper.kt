package com.example.streamwidetechtest.data.mapper

import com.example.streamwidetechtest.data.local.ContactEntity
import com.example.streamwidetechtest.domain.model.Contact

fun ContactEntity.toDomain(): Contact = Contact(contactId, name, phoneNumber)

fun Contact.toLocal(): ContactEntity =
    ContactEntity(contactId = id, name = name, phoneNumber = phoneNumber)