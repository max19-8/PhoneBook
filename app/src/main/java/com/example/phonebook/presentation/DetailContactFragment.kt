package com.example.phonebook.presentation

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.navArgs
import com.example.phonebook.R
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentDetailContactBinding
import com.example.phonebook.domain.useCase.broadcast.IsAlarmSetUseCase
import com.example.phonebook.domain.useCase.broadcast.OffReminderUseCase
import com.example.phonebook.domain.useCase.broadcast.OnReminderUseCase
import com.example.phonebook.domain.useCase.contactDetail.DetailsContactUseCase
import com.example.phonebook.utils.MonthFormatter


class DetailContactFragment : BaseFragment<FragmentDetailContactBinding>(), CompoundButton.OnCheckedChangeListener {

    private var dateTv: TextView? = null

    private val repository  by lazy {
        ContactsRepository(NotificationSwitcher(requireContext()))
    }
  private val  contactDetailsViewModel by lazy {
      ContactDetailsViewModel(DetailsContactUseCase(repository), OnReminderUseCase(repository), OffReminderUseCase(repository),
          IsAlarmSetUseCase(repository)
      )
  }

    private val args: DetailContactFragmentArgs by navArgs()

    private var switchAlarm: SwitchCompat? = null


    override fun getViewBinding(): FragmentDetailContactBinding = FragmentDetailContactBinding.inflate(layoutInflater)

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
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment{ day,month,year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager,"datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int){
        val stringMonth = MonthFormatter().convertNumberMountToInt(month,requireContext())
        val dateBirthday =  (context?.getString(R.string.date_of_birth,"$day $stringMonth $year" ))
        dateTv?.text = dateBirthday
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked){
            args.contactId.let {
                contactDetailsViewModel.contact.value?.let {
                    contactDetailsViewModel.onReminder(args.contactId,it)
                }
            }
        } else {
            args.contactId.let {
                contactDetailsViewModel.offReminder(it)
            }
        }
    }
    override fun onDestroyView() {
        switchAlarm = null
        super.onDestroyView()
    }

    private fun observeViewModel() {
        val isAlarmSet = contactDetailsViewModel.isAlarmSet(requireContext(),args.contactId)
        contactDetailsViewModel.contact.observe(viewLifecycleOwner) { contact ->
            val tvName = binding.detailName
            val tvPhoneNumber = binding.detailNumber
            activity?.runOnUiThread {
                tvName.text = contact.name
                tvPhoneNumber.text = contact.number
                switchAlarm?.isChecked = isAlarmSet
            }
        }
    }
}