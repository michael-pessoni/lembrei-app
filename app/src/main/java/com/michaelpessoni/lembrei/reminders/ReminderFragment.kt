package com.michaelpessoni.lembrei.reminders

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.michaelpessoni.lembrei.EventObserver
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.data.local.RemindersDatabase
import com.michaelpessoni.lembrei.databinding.ReminderFragmentBinding
import com.michaelpessoni.lembrei.util.setupSnackbar

class ReminderFragment : Fragment() {

    private lateinit var viewModel: ReminderViewModel

    private lateinit var listAdapter: ReminderListAdapter
    private lateinit var  binding: ReminderFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.reminder_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = RemindersDatabase.getInstance(application).remindersDatabaseDAO

        val viewModelFactory = ReminderViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[ReminderViewModel::class.java]

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
        viewModel.reminderList.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })

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