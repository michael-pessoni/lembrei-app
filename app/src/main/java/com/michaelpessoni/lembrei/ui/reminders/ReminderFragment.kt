package com.michaelpessoni.lembrei.ui.reminders

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.michaelpessoni.lembrei.EventObserver
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.databinding.ReminderFragmentBinding
import com.michaelpessoni.lembrei.util.setupSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderFragment : Fragment() {

    private val viewModel: ReminderViewModel by viewModels()

    private lateinit var listAdapter: ReminderListAdapter
    private lateinit var  binding: ReminderFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReminderFragmentBinding.inflate(
            inflater,
            container,
            false
        )


        binding.viewModel = viewModel

        setHasOptionsMenu(true)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSnackbar()
        setupListAdapter()
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId) {
            R.id.menu_clear_completed -> {
                viewModel.clearCompletedReminders()
                true
            }
            R.id.menu_clear_all -> {
                viewModel.clearAllReminders()
                true
            }
            else -> false
        }


    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupListAdapter() {
        listAdapter = ReminderListAdapter(viewModel)
        binding.reminderList.adapter = listAdapter
        viewModel.reminderList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }

    }

    private fun setupNavigation() {
        viewModel.newReminderEvent.observe(viewLifecycleOwner, EventObserver{
            navigateToAddEdit(it)
        })
        viewModel.openReminderEvent.observe(viewLifecycleOwner, EventObserver{
            navigateToAddEdit(it)
        })
    }
    private fun navigateToAddEdit(reminderId: Long) {
        findNavController().navigate(
            ReminderFragmentDirections
                .actionReminderFragmentToAddEditFragment(reminderId)
        )

    }

}