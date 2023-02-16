package com.example.phonebook.data.localDataSource

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import com.example.phonebook.data.Contact
import java.text.SimpleDateFormat
import java.util.*


class ContactProvider(private val contentResolver: ContentResolver) {
    fun getContactListData(query:String): List<Contact> {
        val listContacts = mutableListOf<Contact>()
        contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "upper(" + ContactsContract.Profile.DISPLAY_NAME + ") ASC")
            .use { cursor ->
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        val id =
                            cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Profile._ID))
                        val name =
                            cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Profile.DISPLAY_NAME))

                        val number = getContactNumbers(id.toString())
                        val email = getContactEmail(id.toString())
                        val birthdayString = getBirthday(id.toString())
                        val birthday = birthdayFormatter(birthdayString)
                        if (name.lowercase().contains(query.lowercase())){
                            listContacts.add(
                            Contact(id = id, name = name, number = number[0],email = email, birthday = birthday))
                        }
                    }
                }
            }
        return listContacts
    }
    private fun getContactNumbers(id: String): Array<String> {
        val numbers = arrayOf("-", "-")
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        contentResolver.query(
            phoneUri,
            null,
            SELECTION_NUMBER,
            arrayOf(id),
            null
        ).use { numberCursor ->
            if (numberCursor != null) {
                if (numberCursor.moveToNext()) {
                    numbers[0] = numberCursor.getString(
                        numberCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }
                if (numberCursor.moveToNext()) {
                    numbers[1] = numberCursor.getString(
                        numberCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }
            }
        }
        return numbers
    }

    private fun getContactEmail(id: String): String {
        var email = ""
        val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        contentResolver.query(
            emailUri,
            null,
            SELECTION_EMAIL,
            arrayOf(id),
            null
        ).use { numberCursor ->
            if (numberCursor != null) {
                if (numberCursor.moveToNext()) {
                    email = numberCursor.getString(
                        numberCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    )
                }
            }
        }
        return email
    }

    private fun getBirthday(id: String): String {
        var birthday = ""
        contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            SELECTION_BIRTHDAY,
            arrayOf(id),
            null
        ).use { birthdayCursor ->
            if (birthdayCursor != null) {
                while (birthdayCursor.moveToNext()) {
                    birthday = birthdayCursor.getString(
                        birthdayCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Event.START_DATE)
                    )
                }
            }
        }
        return birthday
    }

    @SuppressLint("SimpleDateFormat")
    private fun birthdayFormatter(date:String):Calendar? {
        return if (date == ""){
            null
        }else{
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)
            calendar.time = formatter.parse(date)
            calendar
        }
    }

    companion object {
        private const val SELECTION_NUMBER =
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?"
        private const val SELECTION_EMAIL =
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " =?"
        private const val SELECTION_BIRTHDAY =
            ContactsContract.Data.CONTACT_ID + " =? " + " AND " + ContactsContract.Contacts.Data.MIMETYPE +
                    " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " +
                    ContactsContract.CommonDataKinds.Event.TYPE + " = " +
                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
        private const val DATE_PATTERN = "yyyy-MM-dd"
    }
}