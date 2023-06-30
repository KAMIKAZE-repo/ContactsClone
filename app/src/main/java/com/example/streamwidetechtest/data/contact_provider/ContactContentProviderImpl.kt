package com.example.streamwidetechtest.data.contact_provider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.example.streamwidetechtest.domain.model.Contact

class ContactContentProviderImpl(context: Context): ContactContentProvider {
    private val contentResolver: ContentResolver = context.contentResolver

    @SuppressLint("Range")
    override suspend fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contacts.add(Contact(contactId, displayName, phoneNumber))
            } while (cursor.moveToNext())

            cursor.close()
        }

        return contacts
    }
}