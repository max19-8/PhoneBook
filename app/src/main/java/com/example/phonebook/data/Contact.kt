package com.example.phonebook.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
data class Contact(
    val id: Int,
    var name: String,
    var number: String,
    var email: String?,
    var birthday: Calendar? = null
): Parcelable