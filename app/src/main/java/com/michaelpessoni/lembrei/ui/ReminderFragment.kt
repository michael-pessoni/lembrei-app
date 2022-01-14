package com.michaelpessoni.lembrei.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.databinding.ReminderFragmentBinding
import com.michaelpessoni.lembrei.viewmodels.ReminderViewModel

class ReminderFragment : Fragment() {


    private lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ReminderFragmentBinding>(
            inflater,
            R.layout.reminder_fragment,
            container,
            false
        )

        binding.addButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_reminderFragment_to_addEditFragment)
        }


        return binding.root
    }


}