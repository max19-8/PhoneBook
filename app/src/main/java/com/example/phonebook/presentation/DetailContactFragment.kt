package com.example.phonebook.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.phonebook.ProvideService
import com.example.phonebook.data.Contact
import com.example.phonebook.data.ContactService
import com.example.phonebook.databinding.FragmentDetailContactBinding
import com.example.phonebook.notification.AlarmBirthdayReceiver
import java.lang.ref.WeakReference
import java.util.*



class DetailContactFragment : BaseFragment<FragmentDetailContactBinding>(),GetContactById {

    private var contactService:ProvideService? = null
     var alarmManager:AlarmManager? = null
    private lateinit var pendingIntent: PendingIntent


    override fun getViewBinding(): FragmentDetailContactBinding = FragmentDetailContactBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         (context as? ProvideService)?.getService()?.getDetailContact(0, WeakReference(this))

        Log.d("CONTACT","DetailContactFragment onViewCreated DetailContactFragment ")
        Log.d("CONTACT",contactService.toString() + " DetailContactFragment")

    }

    override fun getContact(contact: Contact) {
        requireView().post {
            with(binding){
                detailName.text = contact.name
                detailNumber.text = contact.number
            }
        }
      //  setAlarm(contact)
    }

    private fun setAlarm(contact: Contact){
       alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(),AlarmBirthdayReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(view?.context,0,intent, PendingIntent.FLAG_MUTABLE)
        val datetimeToAlarm = Calendar.getInstance()
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(Calendar.DAY_OF_MONTH, contact.day)
        datetimeToAlarm.set(Calendar.MONTH, contact.mount)
        datetimeToAlarm.set(Calendar.HOUR_OF_DAY, 12)
        datetimeToAlarm.set(Calendar.MINUTE, 23)

        alarmManager!!.setExactAndAllowWhileIdle( AlarmManager.RTC_WAKEUP,
            datetimeToAlarm.timeInMillis,  pendingIntent)
        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), pendingIntent)
    }
}