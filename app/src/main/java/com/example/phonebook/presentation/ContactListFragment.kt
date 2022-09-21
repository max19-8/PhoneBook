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
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.notification.AlarmBirthdayReceiver
import java.lang.ref.WeakReference
import java.util.*

class ContactListFragment : BaseFragment<FragmentContactListBinding>(),GetContacts{
    override fun getViewBinding(): FragmentContactListBinding  = FragmentContactListBinding.inflate(layoutInflater)

    private var contactService: ContactService? = null

    var alarmManager: AlarmManager? = null
    private lateinit var pendingIntent: PendingIntent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ContactListFragment","фрагмент он атач")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       (context as? ProvideService)?.getService()?.getContacts(WeakReference(this))

        binding.contactItem.item.setOnClickListener {
          //  NotificationHelper.createSampleDataNotification(view.context,"ТЕСТ","ТЕСТОВОЕ СООБЩЕНИЕ","БОЛЬШОЙ ТЕКСТ",true)
            navigate(ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment())

        }
        Log.d("ContactListFragment","фрагмент onViewCreated ")
        Log.d("ContactListFragment",contactService.toString())
    }

    override fun getContacts(list: List<Contact>) {
        requireView().post {
             with(binding.contactItem){
                 listTextViewContact.text = list[0].name
                 listTextViewNumber.text = list[0].number
             }
        //    setAlarm(list[0])
        }

    }

    private fun setAlarm(contact: Contact, getContactFromNotification: GetContactFromnotificetion){
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmBirthdayReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(view?.context,0,intent, PendingIntent.FLAG_IMMUTABLE)

        val datetimeToAlarm = Calendar.getInstance()
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(Calendar.DAY_OF_MONTH, contact.day)
        datetimeToAlarm.set(Calendar.MONTH, contact.mount)
        datetimeToAlarm.set(Calendar.HOUR, 12)
        datetimeToAlarm.set(Calendar.MINUTE, 34)
        getContactFromNotification.getCon(contact.id)

        Log.d("setAlarm",alarmManager.toString())

        alarmManager!!.setExactAndAllowWhileIdle( AlarmManager.RTC_WAKEUP,
            datetimeToAlarm.timeInMillis,  pendingIntent)
//        alarmManager!!.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), pendingIntent)
    }

}