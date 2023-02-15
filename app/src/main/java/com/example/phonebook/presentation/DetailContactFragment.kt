package com.example.phonebook.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.navArgs
import com.example.phonebook.R
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.listOfContacts
import com.example.phonebook.data.localDataSource.ContactProvider
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentDetailContactBinding
import com.example.phonebook.domain.useCase.broadcast.IsAlarmSetUseCase
import com.example.phonebook.domain.useCase.broadcast.OffReminderUseCase
import com.example.phonebook.domain.useCase.broadcast.OnReminderUseCase
import com.example.phonebook.domain.useCase.contactDetail.DetailsContactUseCase
import com.example.phonebook.utils.MonthFormatter
import java.util.*

class DetailContactFragment : BaseFragment<FragmentDetailContactBinding>(),
    CompoundButton.OnCheckedChangeListener {

    private var dateTv: TextView? = null

    private val repository by lazy {
        ContactsRepository( ContactProvider(requireContext().contentResolver),NotificationSwitcher(requireActivity().applicationContext))
    }
    private val contactDetailsViewModel by lazy {
        ContactDetailsViewModel(
            DetailsContactUseCase(repository),
            OnReminderUseCase(repository),
            OffReminderUseCase(repository),
            IsAlarmSetUseCase(repository)
        )
    }

    private val args: DetailContactFragmentArgs by navArgs()

    private var switchAlarm: SwitchCompat? = null


    override fun getViewBinding(): FragmentDetailContactBinding =
        FragmentDetailContactBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateTv = binding.tvDateBirthday
        switchAlarm = binding.switchBirthday
        switchAlarm?.setOnCheckedChangeListener(this)
        val selectDate = binding.tvSelectDate
        selectDate.setOnClickListener {
            showDatePickerDialog()
        }
        observeViewModel()
//        //FIXME(Ниже логика просто для красоты, при добавлении поставщика контактов все будет переделано))
//        val cal = listOfContacts[args.contact.id].birthday
//        val stringMonth =
//            MonthFormatter().convertNumberMountToInt(cal?.get(Calendar.MONTH) ?: 0, requireContext())
//        dateTv?.text = (context?.getString(
//            R.string.date_of_birth,
//            "${cal?.get(Calendar.DATE)} $stringMonth ${cal?.get(Calendar.YEAR)}"
//        ))
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val stringMonth = MonthFormatter().convertNumberMountToInt(month, requireContext())
        //FIXME(Ниже логика просто для красоты, при добавлении поставщика контактов все будет переделано))
        val dateBirthday = (context?.getString(R.string.date_of_birth, "$day $stringMonth $year"))
        dateTv?.text = dateBirthday
        listOfContacts[args.contact.id].birthday = GregorianCalendar(year, month, day, 0, 0, 0)
        Log.d(
            "onDateSelected",
            listOfContacts[args.contact.id].birthday.toString() + "date birthday"
        )
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            contactDetailsViewModel.changeNotifyStatus(isChecked,args.contact)
    }

    override fun onDestroyView() {
        switchAlarm = null
        super.onDestroyView()
    }

    private fun observeViewModel() {
        val isAlarmSet =
            contactDetailsViewModel.isAlarmSet(requireActivity().applicationContext, args.contact)
            binding.detailName.text = args.contact.name
            binding.detailNumber.text = args.contact.name
            switchAlarm?.isChecked = isAlarmSet
        }
    }