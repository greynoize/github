package com.strangelove.github.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.strangelove.github.R
import org.koin.android.architecture.ext.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: com.strangelove.github.databinding.ProfileLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.profile_layout, container, false)
        binding.viewModel = profileViewModel

        profileViewModel.loadProfile()
        return binding.root
    }
}