package com.example.phonebook.utils

import android.content.Context
import com.example.phonebook.R

class MonthFormatter {

    fun convertNumberMountToInt(month:Int,context: Context):String{
      return  when(month){
            0 -> context.getString(R.string.january)
            1 ->context.getString(R.string.february)
            2 ->context.getString(R.string.march)
            3 ->context.getString(R.string.april)
            4 ->context.getString(R.string.may)
            5 ->context.getString(R.string.june)
            6 ->context.getString(R.string.july)
            7 ->context.getString(R.string.august)
            8 ->context.getString(R.string.september)
            9 ->context.getString(R.string.october)
            10 ->context.getString(R.string.november)
            11 ->context.getString(R.string.december)
          else -> throw RuntimeException("an error in choosing a month that does not exist")
      }
    }
}