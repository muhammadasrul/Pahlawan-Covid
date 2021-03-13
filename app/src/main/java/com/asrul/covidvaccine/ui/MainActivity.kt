package com.asrul.covidvaccine.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.databinding.ActivityMainBinding
import com.asrul.covidvaccine.ui.home.HomeFragment
import com.asrul.covidvaccine.ui.news.NewsFragment
import com.asrul.covidvaccine.ui.profile.ProfileFragment
import com.asrul.covidvaccine.ui.vaccine.VaccineFragment

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.hide()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        activityMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home_nav -> {
                    loadFragment(HomeFragment())
                }
                R.id.news_nav -> {
                    loadFragment(NewsFragment())
                }
                R.id.vaccine_nav -> {
                    loadFragment(VaccineFragment())
                }
                R.id.profile_nav -> {
                    loadFragment(ProfileFragment())
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit()
            return true
        }
        return false
    }
}