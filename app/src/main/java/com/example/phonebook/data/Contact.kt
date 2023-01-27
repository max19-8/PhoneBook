package com.example.phonebook.data

import java.util.*

data class Contact(val id:Int, var name:String, var number: String, val birthday: Calendar = GregorianCalendar(2000, Calendar.JANUARY, 28, 0, 0, 0),)