package com.example.streamwidetechtest.data.contact_provider

import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.streamwidetechtest.domain.model.Contact
import com.example.streamwidetechtest.domain.model.ContactDetails
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
                val contactId = cursor.getLong(cursor.getColumnIndex(projection[0]))
                val displayName =
                    cursor.getString(cursor.getColumnIndex(projection[1]))
                val photoUri =
                    cursor.getString(cursor.getColumnIndex(projection[2]))

                val phoneProjection = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
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
                        phoneCursor.getString(phoneCursor.getColumnIndex(phoneProjection[0]))

                    phoneCursor.close()
                }

                contacts.add(Contact(contactId, displayName, phoneNumber, photoUri))

            } while (cursor.moveToNext())

            cursor.close()
        }

        return contacts
    }

    @SuppressLint("Range")
    override suspend fun getContactDetailsById(contactId: Long): ContactDetails {
        val contactDetails = ContactDetails(contactId, "", "")

        val detailsProjection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        )

        val detailsCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            detailsProjection,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contactId.toString()),
            null
        )

        if (detailsCursor != null && detailsCursor.moveToFirst()) {

            contactDetails.name =  detailsCursor.getString(detailsCursor.getColumnIndex(detailsProjection[0]))
            contactDetails.phoneNumber =  detailsCursor.getString(detailsCursor.getColumnIndex(detailsProjection[1]))
            contactDetails.photoUri =  detailsCursor.getString(detailsCursor.getColumnIndex(detailsProjection[2]))
            contactDetails.email = getAddressEmail(contactId)

            detailsCursor.close()
        }
        return contactDetails
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

    @SuppressLint("Range")
    private fun getAddressEmail(contactId: Long): String? {
        var email: String? = null

        val emailProjection = arrayOf(
            ContactsContract.CommonDataKinds.Email.ADDRESS
        )

        val emailCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            emailProjection,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(contactId.toString()),
            null
        )

        if (emailCursor != null && emailCursor.moveToFirst()) {
            email =
                emailCursor.getString(emailCursor.getColumnIndex(emailProjection[0]))
            emailCursor.close()
        }
        return email
    }
}