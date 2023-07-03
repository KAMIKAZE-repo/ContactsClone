package com.example.streamwidetechtest.data.contact_provider

import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.util.Resource

class ContactContentProviderImpl(private val context: Context) : ContactContentProvider {
    private val contentResolver: ContentResolver = context.contentResolver

    @SuppressLint("Range")
    override suspend fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
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
                val photoUri =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))

                val phoneProjection = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.TYPE
                )

                val phoneCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    phoneProjection,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(contactId.toString()),
                    null
                )

                var phoneNumber = displayName

                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    phoneNumber =
                        phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val type = phoneCursor.getString(
                        phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TYPE
                        )
                    )
                    Log.i("TAG", "phone type: $type")
                    phoneCursor.close()
                }

                contacts.add(Contact(contactId, displayName, phoneNumber, photoUri))

            } while (cursor.moveToNext())

            cursor.close()
        }

        return contacts
    }

    @SuppressLint("Range")
    override suspend fun getContactDetailsById(contactId: Long) {
        val accountPos = 0

        var cursor: Cursor? = null
        var accountName: String? = null
        var accountType: String? = null
        val rawContactUri = ContactsContract.RawContacts.CONTENT_URI
        val syncColumns = arrayOf(
            ContactsContract.RawContacts.ACCOUNT_NAME,
            ContactsContract.RawContacts.ACCOUNT_TYPE
        )
        val whereClause = ContactsContract.RawContacts._ID + "=?"
        val whereParams = arrayOf<String>(java.lang.String.valueOf(contactId))
        try {
            cursor = contentResolver.query(
                rawContactUri,
                syncColumns,
                whereClause,
                whereParams,
                null
            )
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                if (cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME) >= 0) {
                    accountName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME))
                }
                if (cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE) >= 0) {
                    accountType =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE))
                }
                cursor.close()
                cursor = null
            }
        } catch (e: Exception) {
            Log.d("TAG", "getting account info failed")
        } finally {
            cursor?.close()
            cursor = null
        }
        Log.d("TAG", "$accountName $accountType")
    }

    override suspend fun addNewContact(displayName: String, phoneNumber: String): Resource<Unit> {
        val ops = arrayListOf<ContentProviderOperation>()

        var op: ContentProviderOperation.Builder =
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    displayName
                )
        // Builds the operation and adds it to the array of operations
        ops.add(op.build())

        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            // Sets the data row's MIME type to Phone
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            // Sets the phone number and type
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
        //  .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, phoneType)

        // Builds the operation and adds it to the array of operations
        ops.add(op.build())

        return try {
            val res = contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            Resource.Success(Unit)
        } catch (e: Exception) {
            // Log exception
            Log.e("TAG", "Exception encountered while inserting contact: $e")
            Resource.Error(e.localizedMessage ?: "error")
        }
    }
}