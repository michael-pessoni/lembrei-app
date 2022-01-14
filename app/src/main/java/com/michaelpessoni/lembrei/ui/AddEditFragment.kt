package com.michaelpessoni.lembrei.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.michaelpessoni.lembrei.viewmodels.AddEditViewModel
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.databinding.AddEditFragmentBinding

class AddEditFragment : Fragment() {

    private lateinit var viewModel: AddEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddEditFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.add_edit_fragment, container, false)
        return binding.root
    }


}