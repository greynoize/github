package com.strangelove.github.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.strangelove.github.R
import com.strangelove.github.ui.profile.ProfileFragment
import com.strangelove.github.ui.repositories.repositories_list.RepositoriesFragment

class MainFragmentPagerAdapter(fragmentManager: FragmentManager, private val context: Context): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return if (position == REPOSITORY_FRAGMENT_POSITION) {
            RepositoriesFragment()
        } else {
            ProfileFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == REPOSITORY_FRAGMENT_POSITION) {
            return context.getString(R.string.main_tabs_repositories)
        } else if (position == PROFILE_FRAGMENT_POSITION) {
            return context.getString(R.string.main_tabs_profile)
        }

        return null
    }

    override fun getCount() = COUNT_OF_FRAGMENTS

    private companion object {
        private const val REPOSITORY_FRAGMENT_POSITION = 0
        private const val PROFILE_FRAGMENT_POSITION = 1
        private const val COUNT_OF_FRAGMENTS = 2
    }
}
