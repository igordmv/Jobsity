package com.exercise.jobsity.presentation.activity

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.exercise.jobsity.R
import com.exercise.jobsity.databinding.ActivityMainBinding
import com.exercise.jobsity.presentation.fragment.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomMenu()
    }

    private fun setupBottomMenu() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent?.action) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
            val fragment = navHostFragment.childFragmentManager.fragments.first()
            val message = intent.getStringExtra(SearchManager.QUERY)
            if (fragment is SearchFragment) {
                fragment.setSpelledQuery(message)
            }
        }
    }

    companion object {
        @BindingAdapter("android:glideImage")
        @JvmStatic
        fun setImage(image: AppCompatImageView, url: String?) {
            url?.let {
                Glide.with(image.context)
                    .load(url)
                    .placeholder(R.drawable.show_placeholder)
                    .into(image)
            }
        }

        @BindingAdapter("android:visibility")
        @JvmStatic
        fun setVisibility(view: View, value: Boolean) {
            view.visibility = if (value) View.VISIBLE else View.GONE
        }
    }
}