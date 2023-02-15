package com.example.phonebook.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.navArgs
import com.example.phonebook.R
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.localDataSource.ContactProvider
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentDetailContactBinding
import com.example.phonebook.domain.useCase.broadcast.IsAlarmSetUseCase
import com.example.phonebook.domain.useCase.broadcast.OffReminderUseCase
import com.example.phonebook.domain.useCase.broadcast.OnReminderUseCase
import java.text.SimpleDateFormat
import java.util.*


class DetailContactFragment : BaseFragment<FragmentDetailContactBinding>(),
    CompoundButton.OnCheckedChangeListener {


    private val repository by lazy {
        ContactsRepository(
            ContactProvider(requireContext().contentResolver),
            NotificationSwitcher(requireActivity().applicationContext)
        )
    }
    private val contactDetailsViewModel by lazy {
        ContactDetailsViewModel(
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
        switchAlarm = binding.switchBirthday
        switchAlarm?.setOnCheckedChangeListener(this)
        observeViewModel()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        contactDetailsViewModel.changeNotifyStatus(isChecked, args.contact)
    }

    override fun onDestroyView() {
        switchAlarm = null
        super.onDestroyView()
    }

    private fun observeViewModel() {
        val isAlarmSet =
            contactDetailsViewModel.isAlarmSet(requireActivity().applicationContext, args.contact)
        binding.detailName.text = args.contact.name
        binding.detailNumber.text = args.contact.number
        createImage(args.contact.name, binding.detailImageContact)
        val calendar = args.contact.birthday
        Log.d("observeViewModel", calendar.toString())
        if (calendar != null) {
            val dateBirthday =
                (context?.getString(R.string.date_of_birth, calendarToStringDate(calendar)))
            binding.tvDateBirthday.text = dateBirthday
            switchAlarm?.isChecked = isAlarmSet
        } else {
            binding.tvDateBirthday.visibility = View.GONE
            switchAlarm?.visibility = View.GONE
        }
        if (args.contact.email.isNullOrBlank()) {
            binding.detailEmail.visibility = View.GONE
            binding.textEmail.visibility = View.GONE
        } else {
            binding.detailEmail.text = args.contact.email
        }
        binding.callThePhone.setOnClickListener {
           callThePhone(args.contact.number)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun calendarToStringDate(calendar: Calendar): String {
        val formatter = SimpleDateFormat(DATE_PATTERN)
        return formatter.format(calendar.time)
    }

    private fun createImage(name: String, button: Button) {
        val strArray = name.split(" ").toTypedArray()
        val builder = StringBuilder()
        if (strArray.isNotEmpty()) {
            builder.append(strArray[0], 0, 1)
        }
        if (strArray.size > 1) {
            builder.append(strArray[1], 0, 1)
        }
        button.text = builder.toString()
    }

    private fun callThePhone(number:String){
        val intent = Intent(Intent.ACTION_DIAL)
        val phone = "tel:$number"
        intent.data = Uri.parse(phone)
        startActivity(intent)
    }

    companion object {
        private const val DATE_PATTERN = "dd-MM-yyyy"
    }
}