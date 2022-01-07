package com.michaelpessoni.lembrei.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.viewmodels.ReminderViewModel

class ReminderFragment : Fragment() {

    companion object {
        fun newInstance() = ReminderFragment()
    }

    private lateinit var viewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reminder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}