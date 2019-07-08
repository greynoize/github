package com.strangelove.github.ui.main

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.strangelove.github.R
import com.strangelove.github.ui.profile.ProfileFragment
import com.strangelove.github.ui.repositories.repositories_list.RepositoriesFragment

class MainFragmentPagerAdapter(fragmentManager: FragmentManager, private val context: Context): FragmentPagerAdapter(fragmentManager) {
    private var listOfFragments = mutableListOf<Fragment>()

    init {
        listOfFragments.add(RepositoriesFragment())
        listOfFragments.add(ProfileFragment())
    }

    override fun getItem(position: Int) = listOfFragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == REPOSITORY_FRAGMENT_POSITION) {
            return context.getString(R.string.main_tabs_repositories)
        } else if (position == PROFILE_FRAGMENT_POSITION) {
            return context.getString(R.string.main_tabs_profile)
        }

        return null
    }

    override fun getCount() = listOfFragments.size

    private companion object {
        private const val REPOSITORY_FRAGMENT_POSITION = 0
        private const val PROFILE_FRAGMENT_POSITION = 1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        listOfFragments[position] = super.instantiateItem(container, position) as Fragment
        return listOfFragments[position]
    }
}
