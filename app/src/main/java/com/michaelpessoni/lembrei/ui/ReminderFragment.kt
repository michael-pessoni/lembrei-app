package com.michaelpessoni.lembrei.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.database.RemindersDatabase
import com.michaelpessoni.lembrei.databinding.ReminderFragmentBinding
import com.michaelpessoni.lembrei.viewmodels.ReminderViewModel
import com.michaelpessoni.lembrei.viewmodels.ReminderViewModelFactory

class ReminderFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<ReminderFragmentBinding>(
            inflater,
            R.layout.reminder_fragment,
            container,
            false
        )



        val application = requireNotNull(this.activity).application

        val dataSource = RemindersDatabase.getInstance(application).remindersDatabaseDAO

        val viewModelFactory = ReminderViewModelFactory(dataSource, application)

        val reminderViewModel = ViewModelProvider(this, viewModelFactory)[ReminderViewModel::class.java]

        binding.reminderViewModel = reminderViewModel

        binding.reminderList.adapter = ReminderListAdapter(ReminderListener {
            reminderViewModel.navigateToAddEdit()
        })

//        reminderViewModel.isNewReminder.observe(viewLifecycleOwner, {
//            if (it == true){
//                this.findNavController().navigate(
//                    ReminderFragmentDirections.actionReminderFragmentToAddEditFragment(-1)
//                )
//            }
//            reminderViewModel.doneNavigating()
//        })

        binding.addButton.setOnClickListener {
            this.findNavController().navigate(
                ReminderFragmentDirections.actionReminderFragmentToAddEditFragment(-1)
            )
        }

        return binding.root
    }



}