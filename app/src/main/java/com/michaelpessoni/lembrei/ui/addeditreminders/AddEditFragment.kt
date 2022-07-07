package com.michaelpessoni.lembrei.ui.addeditreminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.michaelpessoni.lembrei.EventObserver
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.databinding.AddEditFragmentBinding
import com.michaelpessoni.lembrei.util.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddEditFragment : Fragment() {

    private val viewModel: AddEditViewModel by viewModels()

    private lateinit var binding: AddEditFragmentBinding

    private val args: AddEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= AddEditFragmentBinding.inflate(inflater, container, false)

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

}