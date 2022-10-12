package org.sopt.sample.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
        changeFragment(R.id.home)
    }

    private fun addListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            changeFragment(it.itemId)
            true
        }
    }

    private fun changeFragment(menuItemId: Int) {
        val targetFragment = getFragment(menuItemId)

        supportFragmentManager.beginTransaction()
            .replace(R.id.home_container, targetFragment)
            .commitAllowingStateLoss()
    }

    private fun getFragment(menuItemId: Int): Fragment =
        when (menuItemId) {
            R.id.home -> HomeFragment()
            R.id.gallery -> GalleryFragment()
            R.id.search -> SearchFragment()
            else -> throw IllegalArgumentException("not found menu item id")
        }
}