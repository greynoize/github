package com.strangelove.github.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.strangelove.github.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var tabsAdapter: MainFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabsAdapter = MainFragmentPagerAdapter(supportFragmentManager, this)

        main_viewPager.adapter = tabsAdapter
    }
}
