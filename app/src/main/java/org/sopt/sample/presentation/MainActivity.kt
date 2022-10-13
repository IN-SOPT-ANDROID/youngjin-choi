package org.sopt.sample.presentation

import android.os.Bundle
import org.sopt.sample.R
import org.sopt.sample.base.BindingActivity
import org.sopt.sample.databinding.ActivityMainBinding
import replace
import timber.log.Timber

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addListeners()
        changeFragment(R.id.home)
    }

    private fun addListeners() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener {
                changeFragment(it.itemId)
                true
            }

            setOnItemReselectedListener {
                if (it.itemId != R.id.home) return@setOnItemReselectedListener
                (supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName) as? HomeFragment)?.scrollToTop()
            }
        }
    }

    private fun changeFragment(menuItemId: Int) = when (menuItemId) {
        R.id.home -> replace<HomeFragment>(R.id.home_container, HomeFragment::class.java.simpleName)
        R.id.gallery -> replace<GalleryFragment>(R.id.home_container,
            GalleryFragment::class.java.simpleName)
        R.id.search -> replace<SearchFragment>(R.id.home_container,
            SearchFragment::class.java.simpleName)
        else -> Timber.e(IllegalArgumentException("Not found menu item id"))
    }
}