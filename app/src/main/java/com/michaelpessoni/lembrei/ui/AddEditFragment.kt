package com.michaelpessoni.lembrei.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.database.RemindersDatabase
import com.michaelpessoni.lembrei.databinding.AddEditFragmentBinding
import com.michaelpessoni.lembrei.viewmodels.AddEditViewModel
import com.michaelpessoni.lembrei.viewmodels.AddEditViewModelFactory

class AddEditFragment : Fragment() {

    private var mTitle: String = ""
    private var mDescription: String = ""
    private var mDate: String = ""
    private var mPriority: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: AddEditFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_edit_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = AddEditFragmentArgs.fromBundle(requireArguments())

        val dataSource = RemindersDatabase.getInstance(application).remindersDatabaseDAO
        val viewModelFactory = AddEditViewModelFactory(arguments.reminderKey, dataSource)

        val addEditViewModel = ViewModelProvider(this, viewModelFactory)[AddEditViewModel::class.java]

        mTitle = binding.titleTextInput.toString()
        mDescription = binding.descriptionTextInput.toString()
        mPriority = binding.priorityRadioButton.isChecked

        binding.addEditViewModel = addEditViewModel


        addEditViewModel.navigateToReminderFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                addEditViewModel.saveReminder(
                    mTitle,
                    mDescription,
                    mDate,
                    mPriority
                )

                this.findNavController().navigate(
                    AddEditFragmentDirections.actionAddEditFragmentToReminderFragment()
                )
                addEditViewModel.doneNavigating()
            }
        })

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }
}