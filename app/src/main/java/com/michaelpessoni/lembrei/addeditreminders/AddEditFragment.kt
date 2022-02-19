package com.michaelpessoni.lembrei.addeditreminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.michaelpessoni.lembrei.EventObserver
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.data.local.RemindersDatabase
import com.michaelpessoni.lembrei.databinding.AddEditFragmentBinding
import com.michaelpessoni.lembrei.util.setupSnackbar

class AddEditFragment : Fragment() {

    private lateinit var viewModel: AddEditViewModel

    private lateinit var binding: AddEditFragmentBinding

    private val args: AddEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
            inflater, R.layout.add_edit_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = RemindersDatabase.getInstance(application).remindersDatabaseDAO
        val viewModelFactory = AddEditViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory)[AddEditViewModel::class.java]

        binding.viewModel = viewModel

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSnackbar()
        setupNavigation()
        viewModel.start(args.reminderKey)
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation(){
        viewModel.reminderUpdatedEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                AddEditFragmentDirections
                    .actionAddEditFragmentToReminderFragment())
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }
}