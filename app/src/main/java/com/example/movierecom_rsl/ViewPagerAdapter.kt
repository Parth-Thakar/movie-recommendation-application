package com.example.movierecom_rsl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movierecom_rsl.fragments.LogInFragment
import com.example.movierecom_rsl.fragments.RegisterFragment


class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
// View Page Adapter for the tab layout
    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            RegisterFragment()
        } else LogInFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}