package com.example.phonebook.data

import java.util.*

data class Contact(val id:Int, var name:String, var number: String, var birthday: Calendar = GregorianCalendar(2000, Calendar.FEBRUARY, 3, 0, 0, 0),)